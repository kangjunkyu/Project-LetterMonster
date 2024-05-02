import os
import shutil
import uuid
import logging

from fastapi import APIRouter, File, Form, UploadFile
from fastapi.responses import JSONResponse, FileResponse
from pathlib import Path

import mediapipe as mp
# from mediapipe import solutions
# from mediapipe.tasks import python
# from mediapipe.framework.formats import landmark_pb2

import cv2
import numpy as np
import pandas as pd

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# model_path ='rigging/pose_landmarker.task'
# base_option = python.baseOptions(model_asset_path='pose_landmarker.task')

router = APIRouter()

joint_names = [
    'mouth_right', 'mouth_left',
    'right_wrist', 'left_wrist', 'left_shoulder', 'right_elbow',
    'left_elbow', 'right_shoulder',
    'left_ankle', 'left_knee', 'left_hip', 'right_ankle',
    'right_knee', 'right_hip', 'nose'
]

joint_rename = {
    'right_wrist': 'RightHand',
    'left_wrist': 'LeftHand',
    'left_shoulder': 'LeftShoulder',
    'right_shoulder': 'RightShoulder',
    'right_elbow': 'RightArm',
    'left_elbow': 'LeftArm',
    'left_ankle': 'LeftFoot',
    'right_ankle': 'RightFoot',
    'left_knee': 'LeftLeg',
    'right_knee': 'RightLeg',
    'left_hip': 'LeftUpLeg',
    'right_hip': 'RightUpLeg',
    'nose': 'Head'
}


# 영상, 캐릭터 img url을 받음
# 1. 영상 -> 리깅 -> bvh
# 2. 캐릭터 -> 리깅 -> 영상으로 만든 bvh적용하여 img로 만들기

@router.post("/create")
def rigging(
        video: UploadFile = File(...),
        character_id: str = Form(...),
        motion_name: str = Form(...),
        img_url: str = Form(...)
):
    global cap, pose

    print(video)
    print(character_id)
    print(motion_name)
    print(img_url)

    try:
        # 비디오 저장 경로
        VIDEO_DIR = 'temp_video'
        Path(VIDEO_DIR).mkdir(exist_ok=True)

        UUID = str(uuid.uuid4())
        video_path = os.path.join(VIDEO_DIR, UUID)
        Path(video_path).mkdir(exist_ok=True)

        print(f'{video_path}/{video.filename}')

        # 비디오 저장
        with open(f'{video_path}/{video.filename}', "wb") as buffer:
            shutil.copyfileobj(video.file, buffer)

        # 리깅 결과 저장 경로
        RIG_DIR = 'temp_rig'
        Path(RIG_DIR).mkdir(exist_ok=True)
        rig_path = os.path.join(RIG_DIR, UUID)
        Path(rig_path).mkdir(exist_ok=True)

        # 리깅 결과 파일
        fourcc = cv2.VideoWriter_fourcc(*'XVID')
        out = cv2.VideoWriter(f'{rig_path}/output.avi', fourcc, 20.0, (480, 640))

        # MediaPipe Pose 모듈
        mp_pose = mp.solutions.pose
        mp_drawing = mp.solutions.drawing_utils
        pose = mp_pose.Pose(static_image_mode=False,
                            model_complexity=2,
                            enable_segmentation=True,
                            min_detection_confidence=0.5,
                            min_tracking_confidence=0.5)

        # 비디오 열기
        cap = cv2.VideoCapture(f'{video_path}/{video.filename}')
        frame_landmarks = []

        while cap.isOpened():
            success, image = cap.read()
            if not success:
                break

            resized_image = cv2.resize(image, (480, 640))

            rgb_image = cv2.cvtColor(resized_image, cv2.COLOR_BGR2RGB)
            results = pose.process(rgb_image)

            if results.pose_landmarks:
                frame_joint = {}

                for joint_name, joint_rename_value in joint_rename.items():
                    idx = getattr(mp_pose.PoseLandmark, joint_name.upper()).value
                    landmark = results.pose_landmarks.landmark[idx]
                    frame_joint[joint_rename_value] = (round(landmark.x * 480), round(landmark.y * 640), 0)

                cv2.putText(resized_image,
                            f'{joint_rename_value}:{frame_joint[joint_rename_value]}',
                            (10, 20 + 20 * list(joint_rename.values()).index(joint_rename_value)),
                            cv2.FONT_HERSHEY_PLAIN,
                            0.6,
                            (255, 255, 255),
                            1
                            )

                frame_joint['Neck'] = tuple(
                    np.round(np.mean([frame_joint['LeftShoulder'], frame_joint['RightShoulder']], axis=0), 4))
                frame_joint['Hips'] = tuple(
                    np.round(np.mean([frame_joint['LeftUpLeg'], frame_joint['RightUpLeg']], axis=0), 4))
                frame_joint['Spine'] = frame_joint['Hips']
                frame_joint['Spine2'] = tuple(np.round(np.mean([frame_joint['Neck'], frame_joint['Hips']], axis=0), 4))

                frame_landmarks.append(frame_joint)

                mp_drawing.draw_landmarks(
                    resized_image,
                    results.pose_landmarks,
                    mp_pose.POSE_CONNECTIONS,
                    mp_drawing.DrawingSpec(color=(255, 255, 255), thickness=2, circle_radius=2),
                    mp_drawing.DrawingSpec(color=(245, 66, 230), thickness=2, circle_radius=2)
                )

                out.write(resized_image)

            # cv2.imshow('MediaPipe Pose', resized_image)  # 이걸 영상이나 gif로 저장할 수 없을까?

            if cv2.waitKey(5) & 0xFF == 27:
                break

        return FileResponse(path=f'{rig_path}/output.avi', media_type='application/octet-stream', filename="output.avi")

    except Exception as e:
        logger.error("rigging => 에러 발생", exc_info=True)
        return JSONResponse(content={"error": f"Fast API 에러 : rigging => {e}"}, status_code=500)

    finally:
        if 'cap' in globals() and cap is not None:
            cap.release()
        if 'pose' in globals() and pose is not None:
            pose.close()
        cv2.destroyAllWindows()
