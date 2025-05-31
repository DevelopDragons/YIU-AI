/** @jsxImportSource @emotion/react */
import { useLocation } from "react-router-dom";
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
  Typography,
} from "@mui/material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ExpandLessIcon from "@mui/icons-material/ExpandLess";
import { useResponsive } from "./hooks/ResponsiveContext";
import { navItems } from "./models/menu";
import { colors } from "./assets/styles/colors";
import { border1 } from "./assets/styles/borderLine";
import CloseIcon from "@mui/icons-material/Close";

interface LayoutProps {
  className?: string;
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ className, children }) => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const location = useLocation();
  const showHeader =
    location.pathname !== "/management" && location.pathname !== "/user";

  const [mobileOpen, setMobileOpen] = React.useState(false);

  const handleDrawerToggle = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const [openIndex, setOpenIndex] = useState<number | null>(null);

  const handleClick = (index: number) => {
    const item = navItems[index];
    if (!item.subMenu.length && item.link) {
      window.location.href = item.link; // 또는 react-router로 이동하려면 useNavigate 사용
      return;
    }
    setOpenIndex(openIndex === index ? null : index);
  };

  const drawerWidth = 240;

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
    <Box
      sx={{
        width: drawerWidth,
      }}
    >
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
        {/* AI융합학부 */}
      </div>
      <Divider />
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
                      href={subItem.link}
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

  // const container =
  //   window !== undefined ? () => window().document.body : undefined;

  return (
    <div className={className}>
      <Drawer
        // container={container}
        variant="temporary"
        open={mobileOpen}
        onClose={handleDrawerToggle}
        ModalProps={{
          keepMounted: true, // Better open performance on mobile.
        }}
        sx={{
          display: { xs: "block", sm: "block", md: "none" },
          "& .MuiDrawer-paper": { boxSizing: "border-box", width: drawerWidth },
        }}
      >
        {drawer}
      </Drawer>
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
        {children}
      </div>
      {/* Footer */}
      <Footer />
    </div>
  );
};

export default Layout;
