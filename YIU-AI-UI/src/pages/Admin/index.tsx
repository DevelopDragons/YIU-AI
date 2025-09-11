/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { useState } from "react";
import {
  Routes,
  Route,
  Link,
  Outlet,
  useLocation,
  useNavigate,
} from "react-router-dom";
import { ExpandMore, ExpandLess } from "@mui/icons-material";
import NewsForm from "./News/NewsForm";
import AdminHome from "./Home";
import { adminMenu } from "../../models/admin";
import { colors } from "../../assets/styles/colors";
import YIU_logo from "../../assets/images/YIU_logo.png";
import SmallButton from "../../components/Button/SmallButton";

// hover + active 스타일 공통 정의
const activeStyle = {
  color: colors.yiu.green,
  backgroundColor: colors.gray.lightGray,
};

const baseButtonStyle = css({
  padding: 15,
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  cursor: "pointer",
  borderRadius: 10,
  color: colors.gray.gray,
  textDecoration: "none",
  transition: "all 0.3s",
  fontWeight: 600,
  ":hover": activeStyle,
});

// 하위 메뉴 스타일
const childButtonStyle = css([
  baseButtonStyle,
  {
    display: "block",
    padding: "10px 15px",
  },
]);

const Sidebar = () => {
  const [openMenus, setOpenMenus] = useState<{ [key: string]: boolean }>({});
  const location = useLocation();

  const toggleMenu = (name: string) => {
    setOpenMenus((prev) => ({ ...prev, [name]: !prev[name] }));
  };

  return (
    <div
      css={css({
        width: 250,
        minHeight: "100vh",
        backgroundColor: colors.gray.white,
        paddingLeft: 10,
        paddingRight: 10,
      })}
    >
      {/* 로고 */}
      <div
        css={css({
          paddingLeft: 10,
          paddingRight: 10,
          height: 70,
          display: "flex",
          flexDirection: "row",
          justifyContent: "start",
          alignItems: "center",
          gap: 10,
        })}
      >
        <img
          src={YIU_logo}
          css={{
            width: 35,
            objectFit: "contain",
          }}
        />
        <span css={css({ fontWeight: 600, color: colors.gray.black })}>
          용인대학교 AI융합학부
        </span>
      </div>

      {adminMenu.map((menu) => {
        const isActive = menu.path && location.pathname === menu.path;

        return (
          <div key={menu.name}>
            {menu.children ? (
              <div
                css={[baseButtonStyle, openMenus[menu.name] ? activeStyle : {}]}
                onClick={() => toggleMenu(menu.name)}
              >
                {menu.name}
                {openMenus[menu.name] ? <ExpandLess /> : <ExpandMore />}
              </div>
            ) : (
              <Link
                to={menu.path!}
                css={[baseButtonStyle, isActive ? activeStyle : {}]}
              >
                {menu.name}
              </Link>
            )}

            {menu.children && openMenus[menu.name] && (
              <div css={css({ paddingLeft: 20 })}>
                {menu.children.map((child) => {
                  const isChildActive = location.pathname === child.path;
                  return (
                    <Link
                      key={child.name}
                      to={child.path}
                      css={[childButtonStyle, isChildActive ? activeStyle : {}]}
                    >
                      {child.name}
                    </Link>
                  );
                })}
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
};

const Header = () => {
  const navigate = useNavigate();
  return (
    <div
      css={css({
        height: 70,
        backgroundColor: colors.gray.white,
        paddingLeft: 15,
        paddingRight: 15,
        display: "flex",
        justifyContent: "end",
        alignItems: "center",
      })}
    >
      <SmallButton
        title={"EXIT"}
        onClick={() => navigate(`/`, { replace: true })}
        colored={false}
      />
    </div>
  );
};

const AdminPage = () => {
  const location = useLocation();
  const hideSidebar = location.pathname.includes("form");

  return (
    <div
      css={css`
        display: flex;
        min-height: 100vh;
      `}
    >
      {!hideSidebar && <Sidebar />}
      <div
        css={css({
          flex: 1,
          backgroundColor: colors.gray.background,
        })}
      >
        <Header />
        {/* Outlet으로 하위 라우트가 들어옴 */}
        <Outlet />
      </div>
    </div>
  );
};

export default AdminPage;
