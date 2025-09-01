import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";
import Snackbar from "@mui/material/Snackbar";

// 기본 API
export const defaultAPI: AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_URL,
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
  },
});

// AccessToken이 필요한 API => post, put // not delete
export const authAPI: AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_URL,
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
    Authorization: `Bearer ${sessionStorage.getItem("accessToken")}`,
  },
});

// DELETE 요청 함수
export const authDeleteAPI = async (url: string, data?: any) => {
  try {
    const response = await authFileAPI.delete(url, { data });
    return response.data;
  } catch (error) {
    console.error("Error deleting data:", error);
    throw error;
  }
};

// AccessToken이 필요 + 파일 업로드 필요 API
export const authFileAPI: AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_URL,
  headers: {
    "Content-Type": "multipart/form-data",
    Authorization: `Bearer ${sessionStorage.getItem("accessToken")}`,
  },
});

// 토큰 응답 타입 정의
interface TokenResponse {
  accessToken: string;
  refreshToken: string;
}

// AccessToken 갱신 함수
export const refreshAccessToken = async (): Promise<boolean> => {
  try {
    const response: AxiosResponse<TokenResponse> = await axios({
      method: "POST",
      url: `${process.env.REACT_APP_URL}/token/refresh`,
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      data: {
        accessToken: sessionStorage.getItem("accessToken"),
        refreshToken: sessionStorage.getItem("refreshToken"),
      },
    });

    const { accessToken, refreshToken } = response.data;
    sessionStorage.setItem("accessToken", accessToken);
    sessionStorage.setItem("refreshToken", refreshToken);
    return true;
  } catch (error: any) {
    sessionStorage.clear();
    // message.error("로그인 세션이 만료되어 로그아웃 됩니다.");
    return false;
  }
};

// Axios 인터셉터 설정 함수
// Axios 인터셉터 설정 함수
const setupAxiosInterceptors = (apiInstance: AxiosInstance): void => {
  apiInstance.interceptors.response.use(
    (response: AxiosResponse): AxiosResponse => response,
    async (error: any) => {
      const originalRequest: AxiosRequestConfig = error.config;

      if (error.response && error.response.status === 401) {
        const refreshSuccess: boolean = await refreshAccessToken();
        if (refreshSuccess) {
          const newToken = sessionStorage.getItem("accessToken");

          // 🔹 인스턴스 기본 헤더 업데이트
          apiInstance.defaults.headers.Authorization = `Bearer ${newToken}`;

          // 🔹 원래 요청에도 최신 토큰 적용
          originalRequest.headers = {
            ...originalRequest.headers,
            Authorization: `Bearer ${newToken}`,
          };

          return apiInstance(originalRequest);
        }
      }

      return Promise.reject(error);
    }
  );
};

// 인터셉터 적용
setupAxiosInterceptors(authAPI);
setupAxiosInterceptors(authFileAPI);
