import os
import uuid

from fastapi import FastAPI, File, UploadFile, Form
from fastapi.responses import FileResponse
from fastapi.middleware.cors import CORSMiddleware
from pathlib import Path

import boto3
from botocore.exceptions import ClientError
import logging

from pkg_resources import resource_filename
from pydantic import BaseModel, Field

from animated_drawings import render
from AnimatedDrawings.examples.image_to_animation import image_to_animation

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

origins = [
    "*"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# S3 설정
# client_s3 = boto3.client(
#     's3',
#     aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
#     aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY")
# )

@app.get("/")
async def home():
    return {"home":"home"}

@app.post("/ai/character/create")
async def create_joint_gif(
        userId: str = Form(..., description="사용자 ID"),
        motionName: str = Form(..., description="애니메이션 모션 이름"),
        file: UploadFile = File(...)):
    try:
        # 이미지 저장 경로 설정
        IMG_DIR = "images_temp"
        Path(IMG_DIR).mkdir(exist_ok=True)

        # 이미지 저장
        imagename = f"{userId}_"+file.filename
        content = await file.read()
        image_path = os.path.join(IMG_DIR, imagename)
        with open(image_path, "wb") as img:
            img.write(content)

        # GIF 저장
        GIF_DIR = "gif_temp"
        gif_dir_name = f"{str(uuid.uuid4())}"
        Path(GIF_DIR).mkdir(exist_ok=True)

        gif_path = os.path.join(GIF_DIR, gif_dir_name)

        motion = motionName

        # config 절대 경로 불러오기
        motion_cfg_fn = resource_filename(__name__, f'AnimatedDrawings/examples/config/motion/{motion}.yaml')
        # retarget_cfg_fn = resource_filename(__name__, f'AnimatedDrawings/examples/config/retarget/fair1_ppf.yaml')
        retarget_cfg_fn = resource_filename(__name__, f'AnimatedDrawings/examples/config/retarget/mixamo_fff.yaml')

        # joint, gif 생성
        image_to_animation(image_path,gif_path,motion_cfg_fn,retarget_cfg_fn, userId, motion)

        # S3에 gif 업로드
        # gif_url = await save_gif_s3(gif_path, motion)

        # 로컬 img 삭제
        os.remove(image_path)

        # S3 gif 경로 돌려주기
        return FileResponse(path=gif_path+f"/{userId}_{motion}.gif", media_type="image/gif")

    except Exception as e:
        logger.error("에러 발생: %s", e)
        return {"error":str(e)}

# async def get_img_s3(img_url)

# S3에 gif 저장하기
# async def save_gif_s3(gif_path, motion):
#     try:
#         # S3에 GIF 파일 업로드
#         client_s3.upload_file(
#             "./gif_temp/" + gif_path,
#             os.getenv("S3_BUCKET"),
#             "gifs/" + gif_path,
#             ExtraArgs={'ContentType': 'image/gif'}
#         )
#
#         # S3에 저장한 GIF 파일의 URL
#         gif_url = os.getenv("S3_URL") + "/" + gif_path
#
#         # 로컬 gif 삭제
#         os.remove(gif_path+f"{motion}.gif")
#
#         return gif_url
#     except ClientError as e:
#         print(f'Credential error => {e}')
#     except Exception as e:
#         print(f"Another error => {e}")
#         return None