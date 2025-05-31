import { atom } from "recoil";
import { temp_news } from "../assets/data/temp/temp_news";
import { recoilPersist } from "recoil-persist";
import { News } from "../models/news";

const { persistAtom } = recoilPersist({
  key: "sessionStorage", //원하는 key 값 입력
  storage: sessionStorage,
});

// 학부소식 상세보기
export const SelectedNewsAtom = atom<News>({
  key: "SelectedNewsAtom",
  default: temp_news[0],
  effects_UNSTABLE: [persistAtom],
});
