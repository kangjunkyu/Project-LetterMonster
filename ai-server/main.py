import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.routers import animation
from app.routers import rigging

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
app.include_router(router=rigging.router, prefix="/ai/rigging", tags=["rigging"])

# if __name__ == "__main__":
#     uvicorn.run("main:app", host="0.0.0.0", port=7777, reload=True, log_level="debug")