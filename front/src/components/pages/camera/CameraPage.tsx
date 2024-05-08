import { useEffect, useRef, useState } from 'react';
import styles from "../camera/CameraPage.module.scss";
import { useGetCharacterList } from "../../../hooks/character/useCharacterList";
import Draggable from 'react-draggable';
import { ResizableBox } from 'react-resizable';
import 'react-resizable/css/styles.css';
import LNB from "../../molecules/common/LNB";

interface Character {
    characterId: number;
    imageUrl: string;
    mainCharacter: boolean;
    nickname: string;
    width: number;
    height: number;
    x: number;
    y: number;
    uniqueKey: string;  // 고유 키를 위한 새로운 필드 추가
}

const Cameras: React.FC = () => {
    const {data: characterList, isLoading, error} = useGetCharacterList();
    const videoRef = useRef<HTMLVideoElement>(null);
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const [selectedCharacters, setSelectedCharacters] = useState<Character[]>([]);
    const [showCharacters, setShowCharacters] = useState<boolean>(false);
    const [previewImage, setPreviewImage] = useState<string | null>(null);

    useEffect(() => {
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            navigator.mediaDevices.getUserMedia({video: {aspectRatio: 16 / 9}})
                .then((stream: MediaStream) => {
                    const video = videoRef.current;
                    if (video) {
                        video.srcObject = stream;
                        video.style.objectFit = 'contain';
                        video.style.width = '100%';
                        video.style.height = 'auto'; // This ensures it scales with the width
                    }
                })
                .catch((error: any) => {
                    console.error("Error accessing the camera: ", error);
                });
        }
    }, []);


    useEffect(() => {
        function updateCanvasSize() {
            if (videoRef.current && canvasRef.current) {
                const {clientWidth, clientHeight} = videoRef.current;
                canvasRef.current.width = clientWidth;
                canvasRef.current.height = clientHeight;

                canvasRef.current.style.width = `${clientWidth}px`;
                canvasRef.current.style.height = `${clientHeight}px`;
                canvasRef.current.style.position = 'absolute';
                canvasRef.current.style.left = '0';
                canvasRef.current.style.top = '0';
                canvasRef.current.style.display = selectedCharacters.length > 0 ? 'block' : 'none';
            }
        }

        updateCanvasSize();
        window.addEventListener('resize', updateCanvasSize);

        return () => {
            window.removeEventListener('resize', updateCanvasSize);
        };
    }, [selectedCharacters, showCharacters, previewImage, videoRef.current]);


    const handleTakePhoto = () => {
        const video = videoRef.current;
        const canvas = canvasRef.current;
        if (video && canvas) {
            const context = canvas.getContext('2d');
            if (context) {
                context.clearRect(0, 0, canvas.width, canvas.height);
                context.drawImage(video, 0, 0, canvas.width, canvas.height);

                // 모든 캐릭터 이미지 로드를 위한 프로미스 배열 생성
                const loadPromises = selectedCharacters.map(character => {
                    return new Promise((resolve, reject) => {
                        const img = new Image();
                        img.crossOrigin = 'anonymous'; // Needed for external URLs
                        img.src = character.imageUrl;
                        img.onload = () => {
                            const scaleX = canvas.width / video.videoWidth;
                            const scaleY = canvas.height / video.videoHeight;
                            const x = character.x * scaleX;
                            const y = character.y * scaleY;
                            const width = character.width * scaleX;
                            const height = character.height * scaleY;

                            context.drawImage(img, x, y, width, height);
                            resolve();
                        };
                        img.onerror = (e) => {
                            console.error("Image load error: ", e);
                            reject(e);
                        };
                    });
                });

                // 모든 이미지가 로드되고 그려진 후에 프리뷰 업데이트
                Promise.all(loadPromises).then(() => {
                    updatePreview();
                }).catch(error => {
                    console.error('Failed to load one or more character images:', error);
                });
            }
        }
    };



    const updatePreview = () => {
        try {
            const imageData = canvasRef.current.toDataURL('image/png');
            setPreviewImage(imageData);
        } catch (e) {
            console.error('Error capturing the image:', e);
        }
    };

    useEffect(() => {
        function handleResize() {
            if (selectedCharacters.length > 0 && videoRef.current) {
                setSelectedCharacters(prevCharacters => prevCharacters.map(character => ({
                    ...character,
                    x: (character.x / videoRef.current.clientWidth) * window.innerWidth,
                    y: (character.y / videoRef.current.clientHeight) * window.innerHeight,
                    width: (character.width / videoRef.current.clientWidth) * window.innerWidth,
                    height: (character.height / videoRef.current.clientHeight) * window.innerHeight
                })));
            }
        }

        window.addEventListener('resize', handleResize);
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, [selectedCharacters]);



    const toggleCharacterList = () => {
        setShowCharacters(!showCharacters);
    };

    const selectCharacter = (character: Character) => {
        const newCharacter = {
            ...character,
            width: 200,
            height: 200,
            x: 20,
            y: 20,
            uniqueKey: `${character.characterId}-${selectedCharacters.length}`  // 고유 키를 생성하여 할당
        };
        setSelectedCharacters(prev => [...prev, newCharacter]);
    };


    const removeSelectedCharacter = (uniqueKey: string) => {
        setSelectedCharacters(prev => prev.filter(c => c.uniqueKey !== uniqueKey));
    };

    const onControlDrag = (e, data, index) => {
        setSelectedCharacters(prev => prev.map((c, i) => i === index ? {...c, x: data.x, y: data.y} : c));
    };

    const onControlResize = (event, {size}, index) => {
        setSelectedCharacters(prev => prev.map((c, i) => i === index ? {...c, width: size.width, height: size.height} : c));
    };

    const closeCharacterList = () => {
        setShowCharacters(false);
    };


    return (
        <div className={styles.container}>
            <video ref={videoRef} autoPlay playsInline className={styles.camera}></video>
            <canvas ref={canvasRef} style={{ position: 'absolute', top: 0, left: 0 }}></canvas>

            {showCharacters && (
                <div className={styles.characterList}>
                    <button onClick={() => setShowCharacters(false)} style={{ /* Styles for close button */ }}>
                        X
                    </button>
                    <ul>
                        {isLoading ? "Loading characters..." : error ? "Error loading characters." : characterList.map(character => (
                            <li key={character.characterId} onClick={() => selectCharacter(character)}>
                                <img src={character.imageUrl} alt={character.nickname} />
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            <div className={styles.controls}>
                <button className={`${styles.button} ${styles.main}`} onClick={handleTakePhoto}>
                    <span style={{visibility: 'hidden'}}>사진 찍기</span>
                </button>
                <button onClick={toggleCharacterList}>캐릭터</button>
            </div>

            {selectedCharacters.map((character, index) => (
                <Draggable key={character.uniqueKey} bounds="parent" onDrag={(e, data) => onControlDrag(e, data, index)}>
                    <ResizableBox
                        width={character.width}
                        height={character.height}
                        onResizeStop={(e, data) => onControlResize(e, data, index)}
                        style={{ position: 'absolute', top: character.y, left: character.x }}
                    >
                        <div style={{ width: '100%', height: '100%' }}>
                            <button onClick={() => removeSelectedCharacter(character.uniqueKey)} style={{
                                position: 'absolute',
                                right: '10px',
                                top: '10px',
                                border: 'none',
                                background: 'red',
                                color: 'white',
                                cursor: 'pointer',
                                width: '25px',
                                height: '25px'
                            }}>
                                X
                            </button>
                            <img src={character.imageUrl} alt={character.nickname} style={{ width: '100%', height: '100%' }} />
                        </div>
                    </ResizableBox>
                </Draggable>
            ))}

            {previewImage && (
                <div className={styles.preview}>
                    <img src={previewImage} alt="Preview" />
                    <button onClick={() => setPreviewImage(null)}>Close Preview</button>
                </div>
            )}
        </div>
    );
};

export default Cameras;