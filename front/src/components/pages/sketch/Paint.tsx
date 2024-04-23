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
  const postSketchCharacterMutation = usePostSketchCharacter();

  const navigate = useNavigate();

  const [color, setColor] = useState("#000");
  const [drawAction, setDrawAction] = useState<DrawAction>(DrawAction.Select);
  const [scribbles, setScribbles] = useState<Scribble[]>([]);
  const [showPopover, setShowPopover] = useState(false);
  const [characterNickname, setCharacterNickname] = useState("");

  const { image, setImage, onImportImageSelect } = useImportImageSelect();

  const handleCharacterNicknameChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setCharacterNickname(event.target.value);
    },
    []
  );

  const fileRef = useRef<HTMLInputElement>(null);
  const onImportImageClick = useCallback(() => {
    fileRef?.current && fileRef?.current?.click();
  }, []);

  const stageRef = useRef<any>(null);

  // 그림 추출
  // const onExportClick = useCallback(() => {
  //   const uri = stageRef.current.toDataURL({
  //     pixelRatio: 3,
  //     mimeType: "image/png",
  //     quality: 1,
  //   });
  //   postSketchCharacterMutation.mutate({
  //     file: uri,
  //     nickname: characterNickname,
  //   });
  //   console.log("사진 들어감api로");

  //   navigate("/motion", { state: { image: uri } });
  // }, [navigate]);
  const onExportClick = useCallback(() => {
    const uri = stageRef.current.toDataURL({
      pixelRatio: 3,
      mimeType: "image/png",
      quality: 1,
    });
  
    // Data URI를 Blob으로 변환
    fetch(uri)
      .then(res => res.blob())
      .then(blob => {
        const file = new File([blob], 'character.png', { type: 'image/png' });
        const nickname = characterNickname;
  
        // usePostSketchCharacter 훅을 통해 데이터 전송
        postSketchCharacterMutation.mutate({ nickname, file });
      });
  
    console.log("이미지와 별명이 API로 전송됨");
    navigate("/motion", { state: { image: uri, nickname: characterNickname } });
  }, [navigate, characterNickname, postSketchCharacterMutation]);

  // 그림판 초기화
  const onClear = useCallback(() => {
    setScribbles([]);
    setImage(undefined);
  }, []);

  const isPaintRef = useRef(false);

  const onStageMouseUp = useCallback(() => {
    isPaintRef.current = false;
  }, []);

  const currentShapeRef = useRef<string>();

  const onStageMouseDown = useCallback(
    (e: KonvaEventObject<MouseEvent>) => {
      if (drawAction === DrawAction.Select) {
        e.cancelBubble = true;
        return;
      }
      isPaintRef.current = true;
      const stage = stageRef?.current;
      const pos = stage?.getPointerPosition();
      const x = pos?.x || 0;
      const y = pos?.y || 0;
      const id = uuidv4();
      currentShapeRef.current = id;

      switch (drawAction) {
        case DrawAction.Scribble: {
          setScribbles((prevScribbles) => [
            ...prevScribbles,
            {
              id,
              points: [x, y],
              color,
            },
          ]);
          break;
        }
      }
    },
    [drawAction, color]
  );

  const onStageMouseMove = useCallback(() => {
    if (drawAction === DrawAction.Select || !isPaintRef.current) return;

    const stage = stageRef?.current;
    const id = currentShapeRef.current;
    const pos = stage?.getPointerPosition();
    const x = pos?.x || 0;
    const y = pos?.y || 0;

    switch (drawAction) {
      case DrawAction.Scribble: {
        setScribbles((prevScribbles) =>
          prevScribbles?.map((prevScribble) =>
            prevScribble.id === id
              ? {
                  ...prevScribble,
                  points: [...prevScribble.points, x, y],
                }
              : prevScribble
          )
        );
        break;
      }
    }
  }, [drawAction]);

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

  return (
    <div>
      <div className={`${styles.paintUpper}`}>
        <div className={styles.paintTool}>
          {PAINT_OPTIONS.map(({ id, label, icon }) => (
            <div
              key={id}
              aria-label={label}
              onClick={() => setDrawAction(id)}
              style={{
                colorScheme: id === drawAction ? "whatsapp" : undefined,
              }}
            >
              {icon}
              {label}
            </div>
          ))}

          <div>
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

          <button aria-label={"Clear"} onClick={onClear}>
            초기화
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
          <button onClick={onImportImageClick}>그림 올리기</button>
          <button onClick={onExportClick}>만나러 가기</button>
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
                strokeWidth={4}
                points={scribble.points}
                onClick={onShapeClick}
                draggable={isDraggable}
              />
            ))}
            <Transformer ref={transformerRef} />
          </Layer>
        </Stage>
        <div>
          <input
            type="text"
            value={characterNickname}
            onChange={handleCharacterNicknameChange}
            placeholder="캐릭터 별명을 입력해주세요"
          />
          <div>별명: {characterNickname}</div>
        </div>
      </div>
    </div>
  );
});
