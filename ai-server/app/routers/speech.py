import os
import requests
import shutil
import json

from fastapi import APIRouter, File, Form, UploadFile
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from dotenv import load_dotenv

router = APIRouter()

env = os.getenv('ENV', 'dev')
load_dotenv(f'.env.{env}')


class TTSRequest(BaseModel):
    speaker: str
    text: str
    volume: int
    speed: int
    pitch: int
    emotion: int
    format: str


@router.post("/tts")
async def text_to_speech(request: TTSRequest):

    speaker = request.speaker
    text = request.text
    volume = request.volume
    speed = request.speed
    pitch = request.pitch
    emotion = request.emotion
    format = request.format

    headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "X-NCP-APIGW-API-KEY-ID": os.getenv("CLOVA_CLIENT_ID"),
        "X-NCP-APIGW-API-KEY": os.getenv("CLOVA_CLIENT_SECRET")
    }

    params = f'speaker={speaker}&text={text}&volume={volume}&speed={speed}&pitch={pitch}&emotion={emotion}&format={format}'

    response = requests.post(
        url="https://naveropenapi.apigw-pub.fin-ntruss.com/tts-premium/v1/tts",
        params=params,
        headers=headers
    )

    return JSONResponse(content={}, status_code=200)


@router.post("/stt")
async def speech_to_text(audio: UploadFile = File(...),
                         lang: str = Form(...)):
    try:
        with open(audio.filename, "wb") as buffer:
            shutil.copyfileobj(audio.file, buffer)

        with open(audio.filename, "rb") as f:
            audio_data = f.read()

        headers = {
            "Content-Type": "application/octet-stream",
            "X-NCP-APIGW-API-KEY-ID": os.getenv("CLOVA_CLIENT_ID"),
            "X-NCP-APIGW-API-KEY": os.getenv("CLOVA_CLIENT_SECRET")
        }

        params = {
            "lang": lang
        }

        response = requests.post(
            url="https://naveropenapi.apigw.ntruss.com/recog/v1/stt",
            headers=headers,
            params=params,
            data=audio_data
        )

        json_result = response.text
        string_data = json.loads(json_result)
        text = string_data["text"]

        print(text) # 확인

        return JSONResponse(content={"text": text}, status_code=200)

    except Exception as e:
        return JSONResponse(content={}, status_code=500)

    finally:
        os.remove(audio.filename)