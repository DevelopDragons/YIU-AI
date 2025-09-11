/** @jsxImportSource @emotion/react */
import { useLocation, useNavigate, Outlet } from "react-router-dom";
import Header from "./components/Common/Header/Header";
import React, { useState } from "react";
import Footer from "./components/Common/Footer/Footer";
import { css } from "@emotion/react";
import {
  Box,
  Collapse,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ExpandLessIcon from "@mui/icons-material/ExpandLess";
import { useResponsive } from "./hooks/ResponsiveContext";
import { navItems } from "./models/menu";
import { colors } from "./assets/styles/colors";
import { border1 } from "./assets/styles/borderLine";
import CloseIcon from "@mui/icons-material/Close";
import TextButton from "./components/Button/TextButton";

const Layout: React.FC = () => {
  const { isDesktopOrLaptop } = useResponsive();
  const navigate = useNavigate();
  const location = useLocation();

  const [mobileOpen, setMobileOpen] = React.useState(false);
  const [openIndex, setOpenIndex] = useState<number | null>(null);
  const drawerWidth = 240;

  const handleDrawerToggle = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const handleClick = (index: number) => {
    const item = navItems[index];
    if (!item.subMenu.length && item.link) {
      navigate(item.link);
      return;
    }
    setOpenIndex(openIndex === index ? null : index);
  };

  React.useEffect(() => {
    if (location.pathname === "/") {
      setOpenIndex(null);
      return;
    }
    const indexToOpen = navItems.findIndex((item) => {
      if (item.link && location.pathname === item.link) return true;
      return item.subMenu.some((sub) => sub.link === location.pathname);
    });

    if (indexToOpen !== -1) {
      setOpenIndex(indexToOpen);
    }
  }, [location.pathname]);

  const drawer = (
    <Box sx={{ width: drawerWidth }}>
      {/* Drawer 헤더 */}
      <div
        css={css({
          minHeight: 50,
          backgroundColor: colors.yiu.green,
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          color: "white",
          fontWeight: "bold",
          fontSize: "1.25rem",
          padding: 15,
        })}
      >
        <div>AI융합학부</div>
        <div onClick={handleDrawerToggle} css={css({ cursor: "pointer" })}>
          <CloseIcon />
        </div>
      </div>

      <Divider />

      {/* 로그인 | 회원가입 */}
      <div
        css={css({
          display: "flex",
          justifyContent: "space-between",
          backgroundColor: colors.gray.lightGray,
          padding: "5px 30px",
          gap: 20,
        })}
      >
        <TextButton title={"로그인"} onClick={() => navigate("/sign-in")} />
        <TextButton title={"회원가입"} onClick={() => navigate("/sign-up")} />
      </div>

      <Divider />

      {/* 메뉴 리스트 */}
      <List disablePadding>
        {navItems.map((item, index) => (
          <div key={item.label}>
            <ListItem disablePadding>
              <ListItemButton
                onClick={() => handleClick(index)}
                sx={{
                  textAlign: "left",
                  px: 3,
                  py: 2,
                  backgroundColor:
                    openIndex === index
                      ? colors.gray.lightGray
                      : colors.gray.white,
                  borderBottom: border1,
                  fontWeight: "bold",
                  "&:hover": {
                    borderLeft: `5px solid ${colors.yiu.green}`,
                    backgroundColor: colors.gray.lightGray,
                  },
                }}
              >
                <ListItemText primary={item.label} />
                {item.subMenu.length > 0 &&
                  (openIndex === index ? (
                    <ExpandLessIcon />
                  ) : (
                    <ExpandMoreIcon />
                  ))}
              </ListItemButton>
            </ListItem>

            <Collapse in={openIndex === index} timeout="auto" unmountOnExit>
              <List disablePadding>
                {item.subMenu.map((subItem) => (
                  <ListItem key={subItem.label} disablePadding>
                    <ListItemButton
                      onClick={() => navigate(subItem.link)}
                      sx={{
                        textAlign: "left",
                        pl: 5,
                        py: 1.5,
                        color: colors.gray.lightBlack,
                        "&:hover": {
                          backgroundColor: colors.gray.lightGray,
                        },
                      }}
                    >
                      <ListItemText primary={subItem.label} />
                    </ListItemButton>
                  </ListItem>
                ))}
              </List>
            </Collapse>
          </div>
        ))}
      </List>
    </Box>
  );
  // --- 조건부 렌더링 처리 ---
  const isAdminPage = location.pathname.startsWith("/admin");

  if (isAdminPage) {
    // AdminPage는 Layout UI 없이 Outlet만 렌더링
    return <Outlet />;
  }
  return (
    <div>
      {!isDesktopOrLaptop && (
        <Drawer
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true,
          }}
          sx={{
            "& .MuiDrawer-paper": {
              boxSizing: "border-box",
              width: drawerWidth,
            },
          }}
        >
          {drawer}
        </Drawer>
      )}

      {/* Header */}
      <Header handleDrawerToggle={handleDrawerToggle} />

      {/* Body */}
      <div
        style={{
          paddingTop: 85,
          minHeight: 1000,
          whiteSpace: "pre-line",
        }}
      >
        <Outlet />
      </div>

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default Layout;
