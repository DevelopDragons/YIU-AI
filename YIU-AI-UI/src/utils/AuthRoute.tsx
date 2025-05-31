import { Navigate } from "react-router-dom";

interface PrivateRouteProps {
  element: React.ReactElement;
  allowedRoles?: number[];
}

const AuthRoute = ({ element, allowedRoles }: PrivateRouteProps) => {
  const userId = sessionStorage.getItem("id");
  const userRole = parseInt(sessionStorage.getItem("role") || "1");

  // 로그인 확인 (userId가 있는지 확인)
  if (!userId) {
    return <Navigate to="/login" replace />;
  }

  // 특정 role이 필요한 경우 role 검사
  if (allowedRoles && !allowedRoles.includes(userRole)) {
    return <Navigate to="/" replace />;
  }

  return element;
};

export default AuthRoute;
