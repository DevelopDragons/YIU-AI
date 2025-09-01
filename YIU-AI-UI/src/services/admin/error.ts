import { AxiosError } from "axios";

export const handleNewsError = (error: unknown) => {
  if ((error as AxiosError).isAxiosError) {
    const status = (error as AxiosError).response?.status;
    switch (status) {
      case 400:
        alert("필수 데이터가 입력되지 않았습니다.");
        break;
      case 401:
        alert("인증되지 않은 사용자입니다.");
        break;
      case 403:
        alert("권한이 없습니다");
        break;
      case 404:
        alert("존재하지 않는 뉴스입니다.");
        break;
      default:
        alert("알 수 없는 오류가 발생했습니다.");
    }
  } else {
    alert("알 수 없는 오류가 발생했습니다.");
  }
};
