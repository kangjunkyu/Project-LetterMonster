import API, { ImgAPI } from "./Config";

export const baseAPI = () => API.get("/");

// 카카오 소셜 로그인
export const getKakaoLogin = (code: string | null) => {
  return API.post(`/kakao`, {}, { params: { code: code } });
};

// 라인 소셜 로그인
export const postLineLogin = (code: string) => {
  return API.post(`/line`, {}, { params: { code: code } });
};

// 캐릭터 그림 전송(생성)
export const postCharacter = async (nickname: string, file: File) => {
  const formData = new FormData();

  const data = { nickname: nickname };
  const uploadData = JSON.stringify(data);
  const blobData = new Blob([uploadData], { type: "application/json" });

  formData.append("form", blobData);
  formData.append("file", file);

  // ImgAPI를 사용하여 요청 보내기
  try {
    return ImgAPI.post(`/characters/create`, formData);
  } catch (error) {
    console.log(error);
  }
};

// 캐릭터 생성 취소
export const cancelCharacter = (characterId: number) =>
  API.delete(`/characters/cancel/${characterId}`);

// 캐릭터 리스트 조회
export const getCharacterList = (userId: number) =>
  API.get(`/characters/list/${userId}`).then((res) => res.data);

// 캐릭터 모션 리스트 조회
export const getCharacterMotionList = () =>
  API.get(`/characters/list/motion`).then((res) => res.data);

// 캐릭터 모션 리스트 조회
export const getCharacterMotionSelect = () =>
  API.get(`/characters/select/motion`).then((res) => res.data);

// 캐릭터 삭제
export const deleteCharacter = (characterId: number) =>
  API.delete(`/characters/delete/${characterId}`);
