import os
import shutil
import uuid

from fastapi import APIRouter, File, Form, UploadFile
from fastapi.responses import JSONResponse
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
def rigging(
        video: UploadFile = File(...),
        character_id: str = Form(...),
        motion_name: str = Form(...),
        img_url: str = Form(...)
):
    # 비디오 저장 경로
    VIDEO_DIR = 'temp_video'
    Path(VIDEO_DIR).mkdir(exist_ok=True)
    video_dir_name = f"{str(uuid.uuid4())}"
    video_path = os.path.join(VIDEO_DIR, video_dir_name)
    
    # 비디오 저장
    with open(video_path, "wb") as buffer:
        shutil.copyfileobj(video.file, buffer)

    # 리깅 결과 저장 경로
    RIG_DIR = 'temp_rig'
    Path(RIG_DIR).mkdir(exist_ok=True)

    # MediaPipe Pose 모듈
    mp_pose = mp.solutions.pose
    mp_drawing = mp.solutions.drawing_utils
    pose = mp_pose.Pose(static_image_mode=False, model_complexity=2, enable_segmentation=True, min_detection_confidence=0.5)

    # 비디오 열기
    cap = cv2.VideoCapture(video_path)
    frame_landmarks = []

    while cap.isOpened():
        success, image = cap.read()
        if not success:
            break

        rgb_image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

        results = pose.process(rgb_image)

        if results.pose_landmarks:
            frame_joing = {}

            mp_drawing.draw_landmarks(rgb_image, results.pose_landmarks, mp_pose.POSE_CONNECTIONS)
            frame_landmarks.append(results.pose_landmarks)

    cap.release()
