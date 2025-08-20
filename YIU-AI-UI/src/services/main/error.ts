import { AxiosError } from "axios";

export const handleSignUpError = (error: unknown) => {
  if ((error as AxiosError).isAxiosError) {
    const status = (error as AxiosError).response?.status;
    switch (status) {
      case 400:
        alert("필수 데이터가 입력되지 않았습니다.");
        break;
      case 409:
        alert("이미 가입한 학번입니다.");
        break;
      case 500:
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        break;
      default:
        alert("회원가입 중 알 수 없는 오류가 발생했습니다.");
    }
  } else {
    alert("회원가입 중 알 수 없는 오류가 발생했습니다.");
  }
};

export const handleSignInError = (error: unknown) => {
  if ((error as AxiosError).isAxiosError) {
    const status = (error as AxiosError).response?.status;
    switch (status) {
      case 400:
        alert("학번과 비밀번호를 모두 입력해주세요.");
        break;
      case 401:
        alert("회원 정보가 일치하지 않습니다.");
        break;
      case 404:
        alert("회원 정보가 존재하지 않습니다.");
        break;
      case 500:
        alert("서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        break;
      default:
        alert("로그인 중 알 수 없는 오류가 발생했습니다.");
    }
  } else {
    alert("로그인 중 알 수 없는 오류가 발생했습니다.");
  }
};
