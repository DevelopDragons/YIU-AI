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

type HeaderProps = {
  handleDrawerToggle: () => void;
};

const Header: React.FC<HeaderProps> = ({ handleDrawerToggle }) => {
  const navigate = useNavigate();

  const headerHeight = 85; // 고정된 헤더 높이 설정 (필요한 값으로 변경 가능)

  return (
    <AppBar
      component="nav"
      position="fixed"
      sx={{
        backgroundColor: colors.gray.white,
        padding: 1.5,
        height: headerHeight, // 고정된 높이 설정
        minHeight: headerHeight, // 최소 높이 설정
      }}
    >
      <Toolbar
        sx={{
          height: "100%", // Toolbar가 AppBar의 전체 높이를 차지하도록 설정
          display: "flex",
          justifyContent: { md: "space-between" },
          alignItems: "center",
        }}
      >
        {/* 모바일 메뉴 버튼 */}
        <IconButton
          color="inherit"
          aria-label="open drawer"
          edge="start"
          onClick={handleDrawerToggle}
          sx={{ mr: 2, display: { md: "none" }, color: colors.gray.black }}
        >
          <MenuIcon />
        </IconButton>
        {/* 로고 및 네임 */}
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "start",
            alignItems: "center",
            gap: 1.5,
            ":hover": {
              cursor: "pointer",
            },
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
        <Box sx={{ display: { xs: "none", sm: "none", md: "flex" }, gap: 1.5 }}>
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
