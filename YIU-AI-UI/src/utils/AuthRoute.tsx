import { Navigate } from "react-router-dom";
import { getUserRole, isLoggedIn } from "./session";
import { UserRole } from "../models/enum";

interface PrivateRouteProps {
  element: React.ReactElement;
  allowedRoles?: UserRole[];
}

const AuthRoute = ({ element, allowedRoles }: PrivateRouteProps) => {
  // 로그인 확인
  if (!isLoggedIn()) {
    return <Navigate to="/sign-in" replace />;
  }

  const role = getUserRole() as UserRole; // string -> UserRole로 타입 변환

  // allowedRoles가 정의되어 있고, 현재 role이 포함되지 않으면 리다이렉트
  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return element;
};

export default AuthRoute;
