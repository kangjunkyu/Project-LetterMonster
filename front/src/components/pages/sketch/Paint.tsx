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

interface PaintProps {}

const downloadURI = (uri: string | undefined, name: string) => {
  const link = document.createElement("a");
  link.download = name;
  link.href = uri || "";
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

const SIZE = 500;

export const Paint: React.FC<PaintProps> = React.memo(function Paint({}) {
  const [color, setColor] = useState("#000");
  const [drawAction, setDrawAction] = useState<DrawAction>(DrawAction.Select);
  const [scribbles, setScribbles] = useState<Scribble[]>([]);
  const [image, setImage] = useState<HTMLImageElement>();
  const [showPopover, setShowPopover] = useState(false);

  const onImportImageSelect = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      if (e.target.files?.[0]) {
        const imageUrl = URL.createObjectURL(e.target.files?.[0]);
        const image = new Image(SIZE / 2, SIZE / 2);
        image.src = imageUrl;
        setImage(image);
      }
      e.target.files = null;
    },
    []
  );

  const fileRef = useRef<HTMLInputElement>(null);
  const onImportImageClick = useCallback(() => {
    fileRef?.current && fileRef?.current?.click();
  }, []);

  const stageRef = useRef<any>(null);

  const onExportClick = useCallback(() => {
    const dataUri = stageRef?.current?.toDataURL({
      pixelRatio: 3,
      mimeType: "image/png", //PNG 확장자라고 명시
      quality: 1, // 그림 최대 품질
    });
    downloadURI(dataUri, "image.png");
  }, []);

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
      <div>
        <div>
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
              className="color-box"
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
              <div style={{ width: "300px" }}>
                <button
                  className="popover-close"
                  onClick={() => setShowPopover(false)}
                >
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
        <div>
          <input
            type="file"
            ref={fileRef}
            onChange={onImportImageSelect}
            style={{ display: "none" }}
            accept="image/*"
          />
          <button onClick={onImportImageClick}>그림 올리기</button>
          <button onClick={onExportClick}>만나러 가기</button>
        </div>
      </div>

      <div>
        <Stage
          height={SIZE}
          width={SIZE}
          ref={stageRef}
          onMouseUp={onStageMouseUp}
          onMouseDown={onStageMouseDown}
          onMouseMove={onStageMouseMove}
        >
          <Layer>
            <Rect
              width={SIZE}
              height={SIZE}
              fill="yellow" // 배경색을 빨간색으로 설정
            />
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
      </div>
    </div>
  );
});
