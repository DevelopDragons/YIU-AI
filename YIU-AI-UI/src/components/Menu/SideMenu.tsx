/** @jsxImportSource @emotion/react */
import * as React from "react";
import { css } from "@emotion/react";
import { useNavigate, useLocation } from "react-router-dom";
import { colors } from "../../assets/styles/colors";
import { NavItem } from "../../models/menu"; // NavItem 인터페이스 임포트

const SideMenu: React.FC<{ menuItem: NavItem }> = ({ menuItem }) => {
  const navigate = useNavigate();
  const location = useLocation(); // 현재 위치 가져오기

  const handleNavigation = (link: string) => {
    navigate(link); // 클릭 시 해당 경로로 이동
  };

  return (
    <div
      css={css({
        width: 300,
        backgroundColor: colors.gray.white,
        // paddingTop: 50,
        zIndex: 1000,
        display: "flex",
        flexDirection: "column",
        alignItems: "flex-start",
        paddingLeft: 10,
      })}
    >
      <div
        css={css({
          width: 170,
          fontWeight: 700,
          fontSize: 22,
          color: colors.text.black,
          marginBottom: 10,
          paddingBottom: 15,
          paddingLeft: 10,
          textAlign: "center",
          borderBottom: `3px solid ${colors.yiu.green}`,
        })}
      >
        {menuItem.label}
      </div>
      {menuItem.subMenu.map((subItem, index) => {
        // 현재 경로와 일치하는지 확인
        const isActive = location.pathname.includes(subItem.link);
        return (
          <div
            key={`${subItem.label}-${index}`}
            css={css({
              width: 160,
              padding: 10,
              // marginLeft: 10,
              cursor: "pointer",
              color: isActive ? colors.yiu.green : colors.text.black, // 활성화된 메뉴의 색상
              backgroundColor: isActive
                ? colors.gray.lightGray
                : colors.gray.white,
              fontSize: 16,
              fontWeight: isActive ? 700 : 500, // 활성화된 메뉴의 글씨 두께
              // borderBottom: `1px solid ${colors.gray.mediumBrightGray}`,
              ":hover": {
                color: colors.yiu.green,
                fontWeight: 700,
                backgroundColor: colors.gray.lightGray,
                transition: "all 0.2s",
              },
            })}
            onClick={() => handleNavigation(subItem.link)}
          >
            {subItem.label}
          </div>
        );
      })}
    </div>
  );
};

export default SideMenu;
