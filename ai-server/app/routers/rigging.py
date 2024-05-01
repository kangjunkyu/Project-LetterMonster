from fastapi import APIRouter
from pathlib import Path

import mediapipe as mp
# from mediapipe import solutions
# from mediapipe.tasks import python
# from mediapipe.framework.formats import landmark_pb2

import cv2
import numpy as np
import pandas as pd

# model_path ='rigging/pose_landmarker.task'
# base_option = python.baseOptions(model_asset_path='pose_landmarker.task')

router = APIRouter()


# 영상, 캐릭터 img url을 받음
# 1. 영상 -> 리깅 -> bvh
# 2. 캐릭터 -> 리깅 -> 영상으로 만든 bvh적용하여 img로 만들기

@router.post("/create")
def rigging():
    mp_pose = mp.solutions.pose
    mp_drawing = mp.solutions.drawing_utils
    pose = mp_pose.Pose(static_image_mode=False, model_complexity=2, enable_segmentation=True)

    cap = cv2.VideoCapture('video path')
    frame_landmarks = []

    RIG_DIR = 'rig_temp'
    Path(RIG_DIR).mkdir(exist_ok=True)

    while cap.isOpened():
        success, image = cap.read()
        if not success:
            break

        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

        results = pose.process(image)

        if results.pose_landmarks:
            mp_drawing.draw_landmarks(image, results.pose_landmarks, mp_pose.POSE_CONNECTIONS)
            frame_landmarks.append(results.pose_landmarks)

    cap.release()
