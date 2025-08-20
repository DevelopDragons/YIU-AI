/** @jsxImportSource @emotion/react */
import * as React from "react";
import { css } from "@emotion/react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";
import { yiuAiInfo } from "../../../assets/data/yiu_ai_info";
import YIU_logo from "../../../assets/images/YIU_logo.png";
import { useNavigate } from "react-router-dom";
import { colors } from "../../../assets/styles/colors";
import yiuInfo from "../../../assets/data/yiu_info";
import { Menu, MenuItem } from "@mui/material";
import { navItems } from "../../../models/menu";
import DropdownMenu from "./DropdownMenu";
import TextButton from "../../Button/TextButton";
import { useResponsive } from "../../../hooks/ResponsiveContext";
import { clearUser, isLoggedIn } from "../../../utils/session";

type HeaderProps = {
  handleDrawerToggle: () => void;
};

const Header: React.FC<HeaderProps> = ({ handleDrawerToggle }) => {
  const navigate = useNavigate();
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();
  const headerHeight = 85; // 고정된 헤더 높이 설정 (필요한 값으로 변경 가능)

  return (
    <AppBar
      component="nav"
      position="fixed"
      sx={{
        backgroundColor: colors.gray.white,
        boxShadow: "none",
      }}
    >
      {/* 상단 얇은 바 (로그인/회원가입) */}
      {isDesktopOrLaptop && (
        <div
          css={css({
            display: "flex",
            justifyContent: "flex-end",
            backgroundColor: colors.gray.lightGray,
            padding: "5px 30px",
            gap: 20,
          })}
        >
          {isLoggedIn() ? (
            <TextButton title={"로그아웃"} onClick={() => clearUser()} />
          ) : (
            <>
              <TextButton
                title={"로그인"}
                onClick={() => navigate("/sign-in")}
              />
              <TextButton
                title={"회원가입"}
                onClick={() => navigate("/sign-up")}
              />
            </>
          )}
        </div>
      )}

      {/* 기존 Header 툴바 */}
      <Toolbar
        sx={{
          minHeight: 85,
          height: 85,
          display: "flex",
          justifyContent: isDesktopOrLaptop ? "space-between" : "flex-start",
          alignItems: "center",
          px: 3,
        }}
      >
        {/* 모바일 메뉴 버튼 */}
        {!isDesktopOrLaptop && (
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, color: colors.gray.black }}
          >
            <MenuIcon />
          </IconButton>
        )}

        {/* 로고 및 네임 */}
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "start",
            alignItems: "center",
            gap: 1.5,
            ":hover": { cursor: "pointer" },
          }}
          onClick={() => navigate("/")}
        >
          <img
            src={YIU_logo}
            css={{
              width: 50,
              objectFit: "contain",
            }}
          />
          <div
            css={css({
              fontWeight: 600,
              fontSize: 25,
              color: colors.gray.black,
            })}
          >
            {`${yiuInfo.name_ko} ${yiuAiInfo.name}`}
          </div>
        </Box>

        {/* 메뉴 */}
        <Box sx={{ display: isDesktopOrLaptop ? "flex" : "none", gap: 1.5 }}>
          {navItems.map((item) => (
            <DropdownMenu
              key={item.label}
              label={item.label}
              link={item.link}
              subMenu={item.subMenu}
            />
          ))}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
