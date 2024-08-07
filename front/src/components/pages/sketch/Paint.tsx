import { KonvaEventObject } from "konva/lib/Node";
import React, { useCallback, useEffect, useRef, useState } from "react";
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
import styles from "./Paint.module.scss";
import { useLocation, useNavigate } from "react-router-dom";
import { usePostSketchCharacter } from "../../../hooks/sketch/usePostSketchCharacter";
import { useAlert } from "../../../hooks/notice/useAlert";
import LNB from "../../molecules/common/LNB";
import DefaultButton from "../../atoms/button/DefaultButton";
import { useTranslation } from "react-i18next";
import { Page_Url } from "../../../router/Page_Url";
import useImportImageSelect from "../../../hooks/sketch/useImportImageSelect";

import GuidePage from "./SketchGuidePage";

interface PaintProps {}

export const Paint: React.FC<PaintProps> = React.memo(function Paint({}) {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { showAlert } = useAlert();
  const location = useLocation();

  const [color, setColor] = useState("#000");
  const [drawAction, setDrawAction] = useState<DrawAction>(DrawAction.Scribble);
  const [scribbles, setScribbles] = useState<Scribble[]>([]);
  const [history, setHistory] = useState<Scribble[][]>([]);
  const [redoHistory, setRedoHistory] = useState<Scribble[][]>([]);
  const [showPopover, setShowPopover] = useState(false);
  const [size, setSize] = useState(500);
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  // const [image, setImage] = useState<HTMLImageElement | undefined>();
  const { image, setImage, onImportImageSelect } = useImportImageSelect(size);
  const [strokeWidth, setStrokeWidth] = useState(15); //펜굵기
  const [showGuide, setShowGuide] = useState(true);

  const { sketchbookId, sketchbookName, fromUuid } = location.state || {};

  useEffect(() => {
    setHistory([[]]);
  }, []);

  //펜굵기
  const handleStrokeWidthChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setStrokeWidth(parseInt(event.target.value));
    },
    []
  );

  // 캐릭터 생성 뮤테이션
  const postSketchCharacterMutation = usePostSketchCharacter(
    (response, uri, nickname) => {
      if (response) {
        navigate(Page_Url.SketchResult, {
          state: {
            characterId: response.data,
            image: uri,
            nickname,
            sketchbookId: sketchbookId,
            sketchbookName: sketchbookName,
            fromUuid,
          },
        });
      }
    }
  );

  useEffect(() => {
    const updateSize = () => {
      const screenWidth = window.innerWidth;
      const maxSize = 500;
      const newSize = Math.min(maxSize, screenWidth * 0.9);
      setSize(newSize);
      setWindowWidth(screenWidth);
    };

    window.addEventListener("resize", updateSize);
    updateSize();

    return () => window.removeEventListener("resize", updateSize);
  }, []);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent | TouchEvent) {
      if (
        showPopover &&
        popoverRef.current &&
        event.target instanceof Node &&
        !popoverRef.current.contains(event.target)
      ) {
        setShowPopover(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [showPopover]);

  const popoverRef = useRef<HTMLDivElement>(null);

  // 닉네임 validation hook
  const [characterNickname, setCharacterNickname] = useState("");
  const [nicknameError, setNicknameError] = useState("");
  const handleCharacterNicknameChange = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const newNickname = event.target.value;
      if (newNickname.startsWith(" ")) {
        setNicknameError(t("paint.pleaseDont"));
      } else if (newNickname.includes("　")) {
        setNicknameError(t("paint.pleaseRename"));
      } else if (newNickname.length > 10) {
        setNicknameError(t("paint.pleaseTen"));
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
    if (characterNickname.trim() === "") {
      showAlert(t("paint.nickname"));
      return;
    } else if (scribbles.length === 0 && !image) {
      showAlert(t("paint.pleaseDraw"));
      return;
    }

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
        return postSketchCharacterMutation.mutate({ nickname, file, uri });
      });
  }, [
    navigate,
    characterNickname,
    postSketchCharacterMutation,
    scribbles,
    image,
  ]);

  // 그림판 초기화
  const onClear = useCallback(() => {
    setScribbles([]);
    setImage(undefined);
    setHistory([[]]);
    setRedoHistory([]);
  }, []);

  // 마우스 움직임 파트
  const isDrawing = useRef(false);
  const onStageMouseUp = useCallback(() => {
    if (!isDrawing.current) return;
    isDrawing.current = false;
    setHistory((prevHistory) => [...prevHistory, [...scribbles]]);
    setRedoHistory([]);
  }, [scribbles]);

  const currentShapeRef = useRef<string>();

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
          setScribbles((prevScribbles) => [
            ...prevScribbles,
            {
              id,
              points: [x, y],
              color,
              strokeWidth,
            },
          ]);
          break;
        case DrawAction.Erase:
          setScribbles((prevScribbles) => [
            ...prevScribbles,
            {
              id,
              points: [x, y],
              color: "white",
              strokeWidth,
            },
          ]);
          break;
      }
    },
    [drawAction, color, strokeWidth]
  );

  const onStageMouseMove = useCallback(
    (event: KonvaEventObject<MouseEvent | TouchEvent>) => {
      if (drawAction === DrawAction.Select || !isDrawing.current) return;

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

  const undo = useCallback(() => {
    if (history.length <= 1) return;

    const previous = history[history.length - 2];
    const current = history[history.length - 1];

    setRedoHistory((prevRedoHistory) => [...prevRedoHistory, current]);
    setHistory(history.slice(0, -1));
    setScribbles(previous || []);
  }, [history]);

  const redo = useCallback(() => {
    if (redoHistory.length === 0) return;

    const next = redoHistory[redoHistory.length - 1];

    setHistory((prevHistory) => [...prevHistory, next]);
    setRedoHistory(redoHistory.slice(0, -1));
    setScribbles(next);
  }, [redoHistory]);

  return (
    <>
      {showGuide && <GuidePage onClose={() => setShowGuide(false)} />}
      <div className={styles.paintContainer}>
        {windowWidth <= 480 && (
          <LNB>
            <h1>{t("paint.title")}</h1>
            <DefaultButton onClick={() => onExportClick()} custom={true}>
              {t("paint.create")}
            </DefaultButton>
          </LNB>
        )}
        <div className={styles.characterNicknameContainer}>
          <div>
            <input
              type="text"
              value={characterNickname}
              onChange={handleCharacterNicknameChange}
              placeholder={t("paint.nickname")}
              className={styles.inputCharacterNickname}
            />
            <div>
              {nicknameError && (
                <div className={styles.nicknameError} style={{ color: "red" }}>
                  {nicknameError}
                </div>
              )}
            </div>
          </div>
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
            {t("paint.upload")}
          </button>
          <button className={styles.etcButton} onClick={onExportClick}>
            {t("paint.create")}
          </button>
        </div>

        <div className={styles.paintCanvas}>
          <Stage
            height={500}
            width={size}
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
              <Rect width={size} height={500} fill="white" />
              {image && (
                <KonvaImage
                  image={image}
                  x={0}
                  y={0}
                  height={size / 2}
                  width={size / 2}
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
                  strokeWidth={scribble.strokeWidth}
                  points={scribble.points}
                  onClick={onShapeClick}
                  draggable={isDraggable}
                />
              ))}
              <Transformer ref={transformerRef} />
            </Layer>
          </Stage>
        </div>

        {/* <div className={`${styles.paintBottom}`}> */}
        <div className={styles.paintTool}>
          <div className={styles.paintButtonUpper}>
            <div id={styles.markerContainer}>
              <input
                id={styles.marker}
                type="range"
                onChange={handleStrokeWidthChange}
                value={strokeWidth}
                max="30"
                min="5"
              />
              <div id={styles.shape}></div>
            </div>
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
              {showPopover && (
                <div ref={popoverRef} className={styles.popoverContent}>
                  <div className={styles.popoverHeader}>
                    <button
                      className={styles.popoverClose}
                      onClick={() => setShowPopover(false)}
                    >
                      <span className="material-icons">clear</span>
                      <span>{t("close")}</span>
                    </button>
                    <SketchPicker
                      color={color}
                      onChangeComplete={(selectedColor) =>
                        setColor(selectedColor.hex)
                      }
                    />
                  </div>
                </div>
              )}
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
            </div>
          </div>

          <div className={styles.paintButtonUnder}>
            <button
              className={styles.etcButton}
              aria-label={"Clear"}
              onClick={onClear}
            >
              <span className="material-icons">delete_forever</span>
            </button>
            <button
              className={styles.etcButton}
              aria-label={"Undo"}
              onClick={undo}
              disabled={history.length <= 1}
            >
              <span className="material-icons">undo</span>
            </button>
            <button
              className={styles.etcButton}
              aria-label={"Redo"}
              onClick={redo}
              disabled={redoHistory.length === 0}
            >
              <span className="material-icons">redo</span>
            </button>
          </div>
        </div>
      </div>
    </>
  );
});
