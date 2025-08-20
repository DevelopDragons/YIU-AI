import { AxiosError } from "axios";
import { ApiType } from "./type";
import { handleSignInError, handleSignUpError } from "./main/error";

export const handleError = (type: ApiType, error: unknown) => {
  switch (type) {
    case ApiType.SIGN_UP:
      return handleSignUpError(error);
    case ApiType.SIGN_IN:
      return handleSignInError(error);
    default:
      alert("알 수 없는 오류가 발생했습니다.");
  }
};
