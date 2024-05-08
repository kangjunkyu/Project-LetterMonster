import os
import shutil
import uuid
import logging

from fastapi import APIRouter, File, Form, UploadFile
from fastapi.responses import JSONResponse
from pathlib import Path

import mediapipe as mp
import cv2
import numpy as np
import pandas as pd

from app.routers.animation import create_gif

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

router = APIRouter()

joint_name = [
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

joints = [
    "Hips", "Spine", "Spine1", "Spine2",
    "LeftShoulder", "LeftArm", "LeftForeArm", "LeftHand",
    "LeftHandIndex1", "LeftHandIndex2", "LeftHandIndex3", "LeftHandMiddle1",
    "LeftHandMiddle2", "LeftHandMiddle3", "LeftHandPinky1", "LeftHandPinky2",
    "LeftHandPinky3", "LeftHandRing1", "LeftHandRing2",
    "LeftHandRing3", "LeftHandThumb1", "LeftHandThumb2",
    "LeftHandThumb3", "Neck", "Head", "RightShoulder", "RightArm",
    "RightForeArm", "RightHand", "RightHandIndex1",
    "RightHandIndex2", "RightHandIndex3", "RightHandMiddle1",
    "RightHandMiddle2", "RightHandMiddle3", "RightHandPinky1",
    "RightHandPinky2", "RightHandPinky3", "RightHandRing1",
    "RightHandRing2", "RightHandRing3", "RightHandThumb1",
    "RightHandThumb2", "RightHandThumb3", "LeftUpLeg", "LeftLeg",
    "LeftFoot", "LeftToeBase", "RightUpLeg", "RightLeg", "RightFoot",
    "RightToeBase"
]


def calculate_angle(a, b, c):
    a = np.array(a)
    b = np.array(b)
    c = np.array(c)

    ba = a - b
    bc = c - b

    # cos_angle_x = np.dot(ba[[1, 2]], bc[[1, 2]]) / (np.linalg.norm(ba[[1, 2]]) * np.linalg.norm(bc[[1, 2]]))
    # cos_angle_y = np.dot(ba[[0, 2]], bc[[0, 2]]) / (np.linalg.norm(ba[[0, 2]]) * np.linalg.norm(bc[[0, 2]]))
    # cos_angle_z = np.dot(ba[[0, 1]], bc[[0, 1]]) / (np.linalg.norm(ba[[0, 1]]) * np.linalg.norm(bc[[0, 1]]))

    # angle_x = np.arccos(cos_angle_x) * 180.0 / np.pi
    # angle_y = np.arccos(cos_angle_y) * 180.0 / np.pi
    # angle_z = np.arccos(cos_angle_z) * 180.0 / np.pi

    cos_angle_x = np.dot(ba[[1, 2]], bc[[1, 2]]) / (np.linalg.norm(ba[[1, 2]]) * np.linalg.norm(bc[[1, 2]]))
    cos_angle_y = np.dot(ba[[0, 2]], bc[[0, 2]]) / (np.linalg.norm(ba[[0, 2]]) * np.linalg.norm(bc[[0, 2]]))
    cos_angle_z = np.dot(ba[[0, 1]], bc[[0, 1]]) / (np.linalg.norm(ba[[0, 1]]) * np.linalg.norm(bc[[0, 1]]))

    cos_angle_x = np.clip(cos_angle_x, -1, 1)
    cos_angle_y = np.clip(cos_angle_y, -1, 1)
    cos_angle_z = np.clip(cos_angle_z, -1, 1)

    angle_x = np.arccos(cos_angle_x) * 180.0 / np.pi
    angle_y = np.arccos(cos_angle_y) * 180.0 / np.pi
    angle_z = np.arccos(cos_angle_z) * 180.0 / np.pi

    print("--------------")
    print("abc : {} {} {}".format(a, b, c))
    print("ba : {} / bc : {}".format(ba, bc))
    print("cos : {} {} {}".format(cos_angle_x, cos_angle_y, cos_angle_z))
    print("angle : {} {} {}".format(angle_x, angle_y, angle_z))

    # # 각 축에 대한 각도 계산
    # angle_x = np.arctan2(np.linalg.norm(np.cross(ba[[1,2]], bc[[1,2]])), np.dot(ba[[1,2]], bc[[1,2]]))
    # angle_y = np.arctan2(np.linalg.norm(np.cross(ba[[0,2]], bc[[0,2]])), np.dot(ba[[0,2]], bc[[0,2]]))
    # angle_z = np.arctan2(np.linalg.norm(np.cross(ba[[0,1]], bc[[0,1]])), np.dot(ba[[0,1]], bc[[0,1]]))
    #
    # # 라디안을 도(degree)로 변환
    # angle_x = angle_x*180.0/np.pi
    # angle_y = angle_y*180.0/np.pi
    # angle_z = angle_z*180.0/np.pi

    return (round(angle_x, 4), round(angle_y, 4), round(angle_z, 4))


# 영상, 캐릭터 img url을 받음
# 1. 영상 -> 리깅 -> bvh
# 2. 캐릭터 -> 리깅 -> 영상으로 만든 bvh적용하여 img로 만들기

@router.post("/create")
async def create_custom_motion(
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
        # 경로 생성
        UUID = str(uuid.uuid4())
        VID_DIR = "temp_video"
        RIG_DIR = "temp_rig"
        Path(VID_DIR).mkdir(exist_ok=True)
        Path(RIG_DIR).mkdir(exist_ok=True)

        # 비디오 저장 경로
        video_path = f'{VID_DIR}/{UUID}'
        Path(video_path).mkdir(exist_ok=True)

        # 비디오 저장
        with open(f'{video_path}/{video.filename}', "wb") as buffer:
            shutil.copyfileobj(video.file, buffer)

        # 리깅 저장 경로
        rig_path = f'{RIG_DIR}/{UUID}'
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
            new_size = (400,400)
            resized_image = cv2.resize(image, new_size)

            rgb_image = cv2.cvtColor(resized_image, cv2.COLOR_BGR2RGB)
            results = pose.process(rgb_image)

            if results.pose_landmarks:
                frame_joint = {}

                for joint_name, joint_rename_value in joint_rename.items():
                    idx = eval(f"mp_pose.PoseLandmark.{joint_name.upper()}.value")
                    landmark = results.pose_landmarks.landmark[idx]
                    frame_joint[joint_rename_value] = (round(landmark.x * new_size[0]), round(landmark.y * new_size[1]), 0)

                cv2.putText(resized_image,
                            f"{joint_rename_value}:{frame_joint[joint_rename_value]}",
                            (10, 20 + 20 * list(joint_rename.values()).index(joint_rename_value)),
                            cv2.FONT_HERSHEY_PLAIN,
                            0.6,
                            (255, 255, 255),
                            1
                            )

                frame_joint['Neck'] = tuple(
                    np.round(np.mean([frame_joint['LeftShoulder'], frame_joint['RightShoulder']], axis=0),
                             4))
                frame_joint['Hips'] = tuple(
                    np.round(np.mean([frame_joint['LeftUpLeg'], frame_joint['RightUpLeg']], axis=0), 4))
                frame_joint['Spine'] = frame_joint['Hips']
                frame_joint['Spine2'] = tuple(
                    np.round(np.mean([frame_joint['Neck'], frame_joint['Hips']], axis=0), 4))

                frame_landmarks.append(frame_joint)

                mp_drawing.draw_landmarks(
                    resized_image,
                    results.pose_landmarks,
                    mp_pose.POSE_CONNECTIONS,
                    mp_drawing.DrawingSpec(color=(255, 255, 255), thickness=2, circle_radius=2),
                    mp_drawing.DrawingSpec(color=(245, 66, 230), thickness=2, circle_radius=2)
                )

                out.write(resized_image)

            cv2.imshow('MediaPipe Pose', resized_image)  # 이걸 영상이나 gif로 저장할 수 없을까?

            if cv2.waitKey(5) & 0xFF == 27:
                break

        # 커스텀 bvh 파일 제작

        df = pd.DataFrame(frame_landmarks)

        angle_data = []

        for frame in frame_landmarks:
            angle = {
                'Spine': calculate_angle(frame['Spine2'], frame['Spine'], frame['Hips']),
                'LeftUpLeg': calculate_angle(frame['LeftLeg'], frame['LeftUpLeg'], frame['Hips']),
                'RightUpLeg': calculate_angle(frame['RightLeg'], frame['RightUpLeg'], frame['Hips']),
                'Spine2': calculate_angle(frame['Neck'], frame['Spine2'], frame['Spine']),
                'Neck': calculate_angle(frame['Head'], frame['Neck'], frame['Spine2']),
                'RightArm': calculate_angle(frame['RightHand'], frame['RightArm'], frame['RightShoulder']),
                'RightShoulder': calculate_angle(frame['RightArm'], frame['RightShoulder'], frame['Spine2']),
                'LeftArm': calculate_angle(frame['LeftHand'], frame['LeftArm'], frame['LeftShoulder']),
                'LeftShoulder': calculate_angle(frame['LeftArm'], frame['LeftShoulder'], frame['Spine2']),
                'RightLeg': calculate_angle(frame['RightFoot'], frame['RightLeg'], frame['RightUpLeg']),
                'LeftLeg': calculate_angle(frame['LeftFoot'], frame['LeftLeg'], frame['LeftUpLeg'])
            }
            angle_data.append(angle)

        df_angles = pd.DataFrame(angle_data)

        df.to_csv(f'{rig_path}/df.csv', index=False)
        df_angles.to_csv(f'{rig_path}/df_angles.csv', index=False)

        # bvh 템플릿 복사
        template_bvh_path = 'custom_motion/template_g.bvh'
        custom_bvh_path = f'AnimatedDrawings/examples/bvh/rokoko/{motion_name}.bvh'
        shutil.copyfile(template_bvh_path, custom_bvh_path)

        with open(custom_bvh_path, 'a', encoding='utf-8') as bvh:
            bvh.write('\nFrames: {}\nFrameTime: 0.033333\n'.format(len(df)))

            for row in range(len(df)):
                frameAni = ""

                for joint in joints:
                    if joint == 'Hips':
                        print("--- Hips ---")

                        hips = df['Hips'].iloc[row]
                        temp = (hips[0], hips[1], hips[2])
                        rounded_hips = tuple(round(t, 4) for t in temp)
                        frameAni += "{} {} {} ".format(*rounded_hips)

                        print("hips : {} {} {}".format(*rounded_hips))

                    if joint in df_angles.columns:
                        print(f'--- joint ---')
                        joint_angles = df_angles[joint].iloc[row]
                        joint_angles = list(joint_angles)

                        if row != 0:
                            pre_angle_x = df_angles[joint].iloc[row - 1]
                            pre_angle_y = df_angles[joint].iloc[row - 1]
                            pre_angle_z = df_angles[joint].iloc[row - 1]

                            # pre_angles = df_angles[joint].iloc[row - 1]
                            # pre_angle_x = joint_angles[0] if joint_angles[0] != 0 and not np.isnan(joint_angles[0]) else pre_angles[0]
                            # pre_angle_y = joint_angles[1] if joint_angles[1] != 0 and not np.isnan(joint_angles[1]) else pre_angles[1]
                            # pre_angle_z = joint_angles[2] if joint_angles[2] != 0 and not np.isnan(joint_angles[2]) else pre_angles[2]
                        else:
                            pre_angle_x = 0
                            pre_angle_y = 0
                            pre_angle_z = 0

                        joint_angles[0] = pre_angle_x if joint_angles[0] == 0 or np.isnan(joint_angles[0]) else joint_angles[0]
                        joint_angles[1] = pre_angle_y if joint_angles[1] == 0 or np.isnan(joint_angles[1]) else joint_angles[1]
                        joint_angles[2] = pre_angle_z if joint_angles[2] == 0 or np.isnan(joint_angles[2]) else joint_angles[2]

                        df_angles[joint].iloc[row] = joint_angles[0]
                        df_angles[joint].iloc[row] = joint_angles[1]
                        df_angles[joint].iloc[row] = joint_angles[2]

                        joint_angles = (joint_angles[1], joint_angles[0], joint_angles[2])
                        frameAni += "{} {} {} ".format(*joint_angles)
                        print("joint_angles : {} {} {}".format(*joint_angles))

                        pre_angle_x = joint_angles[0]
                        pre_angle_y = joint_angles[1]
                        pre_angle_z = joint_angles[2]

                    else:
                        frameAni += "0.0000 0.0000 0.0000 "

                bvh.write(frameAni.strip() + '\n')

        # AnimatedDrawings/examples/config/motion/{모션이름}.yaml 생성
        # -------------
        # 모션 이름도 characterid_motion으로 바꾸던가, 저장 경로를 바꾸던가 해야할 듯
        # ------------

        template_motion_config_path = 'custom_motion/template.yaml'
        custom_motion_config_path = f'AnimatedDrawings/examples/config/motion/{character_id}_{motion_name}.yaml'


        with open(template_motion_config_path, 'r', encoding='utf-8') as file:
            template_content = file.read()

        new_file = template_content.replace('motion_name', motion_name)

        with open(custom_motion_config_path, 'w', encoding='utf-8') as file:
            file.write(new_file)

        # 커스텀 gif 만들기

        gif_path = await create_gif(
            character_id=character_id,
            motion=f'{character_id}_{motion_name}',
            s3_img_url=img_url
        )

        if gif_path:
            return JSONResponse(content={"gif_path": gif_path}, status_code=200)

    except Exception as e:
        logger.error("custom_motion => 에러 발생", exc_info=True)
        return JSONResponse(content={"error": f"Fast API 에러 : custom_motion => {e}"}, status_code=500)

    finally:
        if 'cap' in globals() and cap is not None:
            cap.release()
        if 'pose' in globals() and pose is not None:
            pose.close()
        cv2.destroyAllWindows()