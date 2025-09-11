// src/utils/session.ts

import { useLocation } from "react-router-dom";
import { UserRole } from "../models/enum";
import { browser } from "./browser";

// 저장된 user 객체 가져오기
export const getUser = () => {
  const user = sessionStorage.getItem("user");
  return user ? JSON.parse(user) : null;
};

// 특정 필드만 바로 꺼내오기
export const getUserId = (): string | null => getUser()?.id ?? null;
export const getUserName = (): string | null => getUser()?.name ?? null;
export const getUserRole = (): string | null => getUser()?.role ?? null;
export const getAccessToken = (): string | null =>
  getUser()?.token?.accessToken ?? null;
export const getRefreshToken = (): string | null =>
  getUser()?.token?.refreshToken ?? null;

// 유저 저장
export const setUser = (data: any) => {
  sessionStorage.setItem("user", JSON.stringify(data));
  sessionStorage.setItem("accessToken", data.token.accessToken);
  sessionStorage.setItem("refreshToken", data.token.refreshToken);
};

// 유저 삭제 (로그아웃 시)
export const clearUser = () => {
  sessionStorage.removeItem("user");
  sessionStorage.removeItem("accessToken");
  sessionStorage.removeItem("refreshToken");
  browser.refresh();
};

// 로그인 여부 확인
export const isLoggedIn = (): boolean => {
  const token = getAccessToken();
  return token !== null && token !== "";
};

// ADMIN 권한 여부 확인
export const isAdmin = (): boolean => {
  const role = getUserRole();
  return role === UserRole.ADMIN || role === UserRole.SUPER;
};

// ADMIN Console 확인
export const isAdminMode = (path: string): boolean => {
  return path.startsWith("/admin");
};
