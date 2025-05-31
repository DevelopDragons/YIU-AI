/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";
import altUser from "../../assets/images/user.webp";
import EmailIcon from "@mui/icons-material/Email";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import IconTextGroup from "../Group/IconTextGroup";
import H10 from "../Gap/H10";
import { Member, ProfessorType } from "../../models/member";

// Member 인터페이스를 props로 받아오도록 설정
interface ProfessorProps {
  item: Member; // props로 item을 받음
}

const Professor = ({ item }: ProfessorProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  // 교수 타입별 매핑 객체
  const professorTypeTextMap: { [key in ProfessorType | string]: string } = {
    FULL_TIME: "전임교수",
    ADJUNCT: "겸임교수",
    INVITED: "초빙교수",
    VISITING: "객원교수",
    RETIRED: "명예교수",
  };

  return (
    <div
      css={css({
        display: "flex",
        flexDirection: "row",
        alignItems: "start",
        gap: 30,
      })}
    >
      <img
        src={item.image.length == 0 ? altUser : `${process.env.REACT_APP_URL}/files/show?id=${item.image?.[0]?.id}`}
        // src={boss}
        css={css({
          width: 180,
          height: 230,
          border: `1px solid ${colors.gray.lightGray}`,
          marginBottom: 10, // 이미지와 텍스트 간격
        })}
      />
      <div
        css={css({
          display: "flex",
          flexDirection: "column",
          paddingTop: 20,
          gap: 10,
          fontSize: 14,
          fontWeight: 600,
          color: colors.gray.darkGray,
        })}
      >
        {/* 교수이름 */}
        <div
          css={css({
            fontSize: 20,
            fontWeight: 700,
            color: colors.yiu.green,
          })}
        >
          {item.name}
        </div>
        {/* 교수타입 */}
        <div
          css={css({
            color: colors.gray.gray,
          })}
        >
          {professorTypeTextMap[item.type ?? ""] || "-"}
        </div>
        <H10 />
        {/* 이메일 */}
        <IconTextGroup
          icon={<EmailIcon fontSize="small" />}
          title={item.mail}
        />
        {/* 전화번호 */}
        {item.type == ProfessorType.FULL_TIME && (
          <IconTextGroup
            icon={<LocalPhoneIcon fontSize="small" />}
            title={item.tel}
          />
        )}
        {item.type != ProfessorType.FULL_TIME &&
          item.type != ProfessorType.RETIRED && <div>{item.description}</div>}
        {/* 연구실 */}
        {item.type != ProfessorType.RETIRED && (
          <div
            css={css({ textDecoration: "underline", cursor: "pointer" })}
            onClick={() =>
              window.open(item.labLink, "_blank", "noopener, noreferrer")
            }
          >
            {item.labName}
          </div>
        )}
      </div>
    </div>
  );
};

export default Professor;
