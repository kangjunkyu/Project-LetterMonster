import os
import uuid

from fastapi import FastAPI, File, UploadFile
from fastapi.responses import FileResponse
from fastapi.middleware.cors import CORSMiddleware
from pathlib import Path

import boto3
from botocore.exceptions import ClientError
import logging

from pkg_resources import resource_filename
from pydantic import BaseModel

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

# client_s3 = boto3.client(
#     's3',
#     aws_access_key_id=os.getenv("CREDENTIALS_ACCESS_KEY"),
#     aws_secret_access_key=os.getenv("CREDENTIALS_SECRET_KEY")
# )

@app.get("/")
async def home():
    return {"home":"home"}

# S3 이미지 경로
# 동작 이름
# S3 GIF 경로 컨벤션 -> 필요한 값들
@app.post("/character/gif/create")
async def createGif(file: UploadFile = File(...)):
    try:
        # 이미지 저장 경로 설정
        IMG_DIR = "images_temp"
        Path(IMG_DIR).mkdir(exist_ok=True)

        # 이미지 저장
        imagename = file.filename
        content = await file.read()
        image_path = os.path.join(IMG_DIR, imagename)
        with open(image_path, "wb") as img:
            img.write(content)

        # GIF 저장
        GIF_DIR = "gif_temp"
        gif_dir_name = f"{str(uuid.uuid4())}"
        Path(GIF_DIR).mkdir(exist_ok=True)

        gif_path = os.path.join(GIF_DIR, gif_dir_name)

        motion = "jesse_dance" # 여기에 모션 이름

        motion_cfg_fn = resource_filename(__name__, f'AnimatedDrawings/examples/config/motion/{motion}.yaml')
        # retarget_cfg_fn = resource_filename(__name__, f'AnimatedDrawings/examples/config/retarget/fair1_ppf.yaml')
        retarget_cfg_fn = resource_filename(__name__, f'AnimatedDrawings/examples/config/retarget/mixamo_fff.yaml')

        # motion_cfg_fn = f'config/motion/dab.yaml'
        # retarget_cfg_fn = f'config/retarget/fair1_ppf.yaml'

        image_to_animation(image_path,gif_path,motion_cfg_fn,retarget_cfg_fn)

        # gif_url = await saveGifS3("image.gif")
        # return {"gif_url": gif_path}
        return FileResponse(path=gif_path+"/gif파일명.gif", media_type="image/gif")

    except Exception as e:
        logger.error("에러 발생: %s", e)
        return {"error":str(e)}

# async def saveGifS3(gif_name):
#     try:
#         # S3에 GIF 파일 업로드
#         client_s3.upload_file(
#             "./gif_temp/" + gif_name,
#             os.getenv("S3_BUCKET"),
#             "gifs/" + gif_name,
#             ExtraArgs={'ContentType': 'image/gif'}
#         )
#
#         # S3에 저장한 GIF 파일의 URL
#         gif_url = os.getenv("S3_URL") + "/" + gif_name
#
#         # 로컬에 저장된 GIF 파일 삭제
#         os.remove("./temp/" + gif_name)
#
#         return gif_url
#     except ClientError as e:
#         print(f'Credential error => {e}')
#     except Exception as e:
#         print(f"Another error => {e}")
#         return None