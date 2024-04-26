import { KonvaEventObject } from "konva/lib/Node";
import React, { useCallback, useRef, useState } from "react";
import {
  Rect,
  Stage,
  Layer,
  Image as KonvaImage,
  Line as KonvaLine,
  Transformer,
} from "react-konva";
import { v4 as uuidv4 } from "uuid";
import { Scribble } from "./PaintTypes";
import { DrawAction, PAINT_OPTIONS } from "./PaintConstants";
import { SketchPicker } from "react-color";
import useImportImageSelect from "../../../hooks/sketch/useImportImageSelect";
import styles from "./Paint.module.scss";
import { useNavigate } from "react-router-dom";
import { usePostSketchCharacter } from "../../../hooks/sketch/usePostSketchCharacter";

interface PaintProps {}

export const SIZE = 500;

export const Paint: React.FC<PaintProps> = React.memo(function Paint({}) {
  // 캐릭터 생성 뮤테이션
  const postSketchCharacterMutation = usePostSketchCharacter(
    (response, uri, nickname) => {
      if (response) {
        navigate("/motion", {
          state: { characterId: response.data, image: uri, nickname },
        });
      }
    }
  );

  const navigate = useNavigate();

  const [color, setColor] = useState("#000");
  const [drawAction, setDrawAction] = useState<DrawAction>(DrawAction.Scribble);
  const [scribbles, setScribbles] = useState<Scribble[]>([]);
  const [showPopover, setShowPopover] = useState(false);

  const { image, setImage, onImportImageSelect } = useImportImageSelect();

  // 닉네임 validation hook
  const [characterNickname, setCharacterNickname] = useState("");
  const [nicknameError, setNicknameError] = useState("");
  const handleCharacterNicknameChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const newNickname = event.target.value;
      if (newNickname.startsWith(" ")) {
        setNicknameError("첫 글자로 띄어쓰기를 사용할 수 없습니다.");
      } else if (
        /[^a-zA-Z0-9ㄱ-힣\s]/.test(newNickname) ||
        newNickname.includes("　")
      ) {
        setNicknameError("닉네임은 영문, 숫자, 한글만 가능합니다.");
      } else if (newNickname.length > 20) {
        setNicknameError("닉네임은 20글자 이하만 가능합니다.");
      } else {
        setCharacterNickname(newNickname);
        setNicknameError("");
      }
    },
    []
  );

  const fileRef = useRef<HTMLInputElement>(null);
  const onImportImageClick = useCallback(() => {
    fileRef?.current && fileRef?.current?.click();
  }, []);

  // 그림 추출
  const stageRef = useRef<any>(null);
  const onExportClick = useCallback(() => {
    const uri = stageRef.current.toDataURL({
      pixelRatio: 3,
      mimeType: "image/png",
      quality: 1,
    });

    // Data URI를 Blob으로 변환
    fetch(uri)
      .then((res) => res.blob())
      .then((blob) => {
        const file = new File([blob], "character.png", { type: "image/png" });
        const nickname = characterNickname;

        // usePostSketchCharacter 훅을 통해 데이터 전송
        postSketchCharacterMutation.mutate({ nickname, file, uri });
      });

    navigate("/motion", { state: { image: uri, nickname: characterNickname } });
  }, [navigate, characterNickname, postSketchCharacterMutation]);

  // 그림판 초기화
  const onClear = useCallback(() => {
    setScribbles([]);
    setImage(undefined);
  }, []);

  // 마우스 움직임 파트
  const isDrawing = useRef(false);
  const onStageMouseUp = useCallback(() => {
    isDrawing.current = false;
  }, []);

  const currentShapeRef = useRef<string>();

  // const getPointerPosition = (
  //   event: KonvaEventObject<MouseEvent | TouchEvent>
  // ) => {
  //   const stage = stageRef.current;
  //   // TouchEvent인 경우
  //   if (event.evt instanceof TouchEvent && event.evt.touches.length > 0) {
  //     // 첫 번째 터치 포인트를 사용합니다.
  //     const touch = event.evt.touches[0];
  //     return { x: touch.clientX, y: touch.clientY };
  //   } else {
  //     // MouseEvent를 처리합니다.
  //     return stage.getPointerPosition();
  //   }
  //   // TouchEvent와 MouseEvent를 모두 처리
  //   // if (event.evt.touches && event.evt.touches.length > 0) {
  //   //   return stage.getPointerPosition(); // 터치 위치 얻기
  //   // } else {
  //   //   return stage.getPointerPosition(); // 마우스 위치 얻기
  //   // }
  // };
  const getPointerPosition = (event: any) => {
    const stage = stageRef.current;
    if (event.evt.touches && event.evt.touches.length > 0) {
      const touch = event.evt.touches[0];
      return stage.getRelativePointerPosition(touch);
    } else {
      return stage.getPointerPosition();
    }
  };
  const onStageMouseDown = useCallback(
    (event: any) => {
      if (drawAction === DrawAction.Select) {
        event.cancelBubble = true;
        return;
      }
      isDrawing.current = true;
      const pos = getPointerPosition(event);
      const x = pos?.x || 0;
      const y = pos?.y || 0;
      const id = uuidv4();
      currentShapeRef.current = id;

      switch (drawAction) {
        case DrawAction.Scribble:
        case DrawAction.Erase:
          setScribbles((prevScribbles) => [
            ...prevScribbles,
            {
              id,
              points: [x, y],
              color: drawAction === DrawAction.Erase ? "white" : color,
            },
          ]);
          break;
      }
    },
    [drawAction, color]
  );

  // const onStageMouseDown = useCallback(
  //   (event: KonvaEventObject<MouseEvent | TouchEvent>) => {
  //     if (drawAction === DrawAction.Select) {
  //       event.cancelBubble = true;
  //       return;
  //     }
  //     isDrawing.current = true;
  //     // const stage = stageRef?.current;
  //     const pos = getPointerPosition(event);
  //     const x = pos?.x || 0;
  //     const y = pos?.y || 0;
  //     const id = uuidv4();
  //     currentShapeRef.current = id;

  //     switch (drawAction) {
  //       case DrawAction.Scribble: {
  //         setScribbles((prevScribbles) => [
  //           ...prevScribbles,
  //           {
  //             id,
  //             points: [x, y],
  //             color,
  //           },
  //         ]);
  //         break;
  //       }
  //       case DrawAction.Erase: {
  //         setScribbles((prevScribbles) => [
  //           ...prevScribbles,
  //           {
  //             id,
  //             points: [x, y],
  //             color: "white",
  //           },
  //         ]);
  //         break;
  //       }
  //     }
  //   },
  //   [drawAction, color]
  // );

  const onStageMouseMove = useCallback(
    (event: KonvaEventObject<MouseEvent | TouchEvent>) => {
      if (drawAction === DrawAction.Select || !isDrawing.current) return;

      // const stage = stageRef?.current;
      const id = currentShapeRef.current;
      const pos = getPointerPosition(event);
      const x = pos?.x || 0;
      const y = pos?.y || 0;

      if (
        drawAction === DrawAction.Scribble ||
        drawAction === DrawAction.Erase
      ) {
        setScribbles((prevScribbles) =>
          prevScribbles.map((prevScribble) =>
            prevScribble.id === id
              ? {
                  ...prevScribble,
                  points: [...prevScribble.points, x, y],
                }
              : prevScribble
          )
        );
      }
    },
    [drawAction]
  );

  const transformerRef = useRef<any>(null);

  const onShapeClick = useCallback(
    (e: KonvaEventObject<MouseEvent>) => {
      if (drawAction !== DrawAction.Select) return;
      const currentTarget = e.currentTarget;
      transformerRef?.current?.node(currentTarget);
    },
    [drawAction]
  );

  const isDraggable = drawAction === DrawAction.Select;
  const onTouchMove = useCallback(
    (event: any) => {
      onStageMouseMove(event);
    },
    [onStageMouseMove]
  );

  const onTouchEnd = useCallback(() => {
    onStageMouseUp();
  }, [onStageMouseUp]);
  return (
    <div>
      <div className={`${styles.paintUpper}`}>
        <div className={styles.paintTool}>
          {PAINT_OPTIONS.map(({ id, label, icon }) => (
            <div
              key={id}
              aria-label={label}
              onClick={() => setDrawAction(id)}
              className={`${styles.paintEachTool} ${
                id === drawAction ? styles.toolSelected : ""
              }`}
            >
              {icon}
            </div>
          ))}

          <div className={styles.colorButton}>
            <div
              onClick={() => setShowPopover(true)}
              style={{
                backgroundColor: color,
                height: "32px",
                width: "32px",
                borderRadius: "4px",
                cursor: "pointer",
              }}
            ></div>
            {showPopover && (
              <div className={styles.popoverContent} style={{ width: "300px" }}>
                <button onClick={() => setShowPopover(false)}>
                  <span className="material-icons">clear</span>
                </button>
                <SketchPicker
                  color={color}
                  onChangeComplete={(selectedColor) =>
                    setColor(selectedColor.hex)
                  }
                />
              </div>
            )}
          </div>

          <button
            className={styles.etcButton}
            aria-label={"Clear"}
            onClick={onClear}
          >
            <span className="material-icons">delete_forever</span>
          </button>
        </div>
        <div className={styles.paintImportExport}>
          <input
            type="file"
            ref={fileRef}
            onChange={
              onImportImageSelect as React.ChangeEventHandler<HTMLInputElement>
            }
            style={{ display: "none" }}
            accept="image/*"
          />
          <button className={styles.etcButton} onClick={onImportImageClick}>
            그림 올리기
          </button>
          <button className={styles.etcButton} onClick={onExportClick}>
            만나러 가기
          </button>
        </div>
      </div>

      <div className={styles.paintCanvas}>
        <Stage
          height={SIZE}
          width={SIZE}
          ref={stageRef}
          onMouseUp={onStageMouseUp}
          onMouseDown={onStageMouseDown}
          onMouseMove={onStageMouseMove}
          onTouchStart={onStageMouseDown}
          onTouchMove={onTouchMove}
          onTouchEnd={onTouchEnd}
          style={{ border: "3px solid black" }}
        >
          <Layer>
            <Rect width={SIZE} height={SIZE} fill="white" />
            {image && (
              <KonvaImage
                image={image}
                x={0}
                y={0}
                height={SIZE / 2}
                width={SIZE / 2}
                draggable={isDraggable}
              />
            )}
            {scribbles.map((scribble) => (
              <KonvaLine
                key={scribble.id}
                id={scribble.id}
                lineCap="round"
                lineJoin="round"
                stroke={scribble?.color}
                strokeWidth={14}
                points={scribble.points}
                onClick={onShapeClick}
                draggable={isDraggable}
              />
            ))}
            <Transformer ref={transformerRef} />
          </Layer>
        </Stage>
      </div>
      <div className={styles.characterNicknameContainer}>
        <input
          type="text"
          value={characterNickname}
          onChange={handleCharacterNicknameChange}
          placeholder="캐릭터 별명을 입력해주세요"
          className={styles.inputCharacterNickname}
        />
        <div className={styles.outputCharacterNickname}>
          {nicknameError && (
            <div className={styles.nicknameError} style={{ color: "red" }}>
              {nicknameError}
            </div>
          )}
        </div>
      </div>
    </div>
  );
});
