from fastapi import APIRouter
from App.api import animation

api_router = APIRouter()
api_router.include_router(animation.router)