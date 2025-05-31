/** @jsxImportSource @emotion/react */
import * as React from "react";
import { css, keyframes } from "@emotion/react";
import { useNavigate } from "react-router-dom";
import { colors } from "../../../assets/styles/colors";
import { NavItem } from "../../../models/menu";

const fadeIn = keyframes`
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
`;

// DropdownMenu 컴포넌트
const DropdownMenu: React.FC<NavItem> = ({ label, link, subMenu }) => {
  const [open, setOpen] = React.useState(false);
  const navigate = useNavigate();

  return (
    <div
      css={css({
        position: "relative",
        display: "inline-block",
        textAlign: "left",
        cursor: "pointer",
      })}
      onMouseEnter={() => setOpen(true)}
      onMouseLeave={() => setOpen(false)}
    >
      <button
        css={css({
          background: "none",
          border: "none",
          cursor: "pointer",
          fontSize: 18,
          fontWeight: 700,
          color: colors.text.black,
          ":hover": {
            fontWeight: 800,
          },
        })}
        onClick={() => {
          if(link) navigate(link);
        }}
      >
        {label}
      </button>
      {open && subMenu && (
        <div
          css={css({
            position: "absolute",
            top: "100%",
            left: 0,
            backgroundColor: colors.gray.white,
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
            zIndex: 1000,
            animation: `${fadeIn} 0.3s ease-out`,
            color: colors.text.black,
            fontWeight: 700,
          })}
        >
          {subMenu.map((item, index) =>
            typeof item === "string" ? (
              <div
                key={`${item}-${index}`}
                css={css({
                  paddingLeft: 20,
                  paddingRight: 20,
                  paddingTop: 10,
                  paddingBottom: 10,
                  whiteSpace: "nowrap",
                  textAlign: "left",
                  ":hover": {
                    backgroundColor: colors.gray.lightGray,
                  },
                })}
              >
                {item}
              </div>
            ) : (
              <div
                key={item.label}
                css={css({
                  paddingLeft: 25,
                  paddingRight: 25,
                  paddingTop: 10,
                  paddingBottom: 10,
                  whiteSpace: "nowrap",
                  textAlign: "left",
                  ":hover": {
                    fontWeight: 800,
                    color: colors.yiu.green,
                    backgroundColor: colors.gray.lightGray,
                  },
                })}
                onClick={() =>
                  item.link.startsWith("http")
                    ? window.open(item.link, "_blank", "noopener, noreferrer")
                    : navigate(item.link)
                }
                // onClick={() =>
                //   window.open("http://yongin.ac.kr", "_blank", "noopener, noreferrer")
                // }
              >
                {item.label}
              </div>
            )
          )}
        </div>
      )}
    </div>
  );
};

export default DropdownMenu;
