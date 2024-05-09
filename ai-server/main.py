import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.routers import animation
from app.routers import custom_motion
from app.routers import speech

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

app.include_router(router=animation.router, prefix="/ai/character", tags=["character"])  # characters
app.include_router(router=custom_motion.router, prefix="/ai/motion", tags=["motion"])
app.include_router(router=speech.router, prefix="/ai/letter", tags=["letter"])


@app.get("/")
def home():
    return {"test": "헤에"}


if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True, log_level="debug")
