/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import Title from "../../components/Text/Title";
import thumbnail from "../../assets/images/council_thumbnail.jpeg";
import people from "../../assets/images/council_people.jpeg";
import { temp_council } from "../../assets/data/temp/temp_council";
import SectionTitle from "../../components/Text/SectionTitle";
import H20 from "../../components/Gap/H20";
import { tempCouncilEvents } from "../../assets/data/temp/temp_council";
import { useResponsive } from "../../hooks/ResponsiveContext";

const StudentCouncilPage = (): React.ReactElement => {
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  // 썸네일 + 슬로건 + 설명
  const StudentCouncilIntro = () => {
    return (
      <div
        css={css({
          display: "flex",
          flexDirection: isMobile || isTablet ? "column" : "row",
          alignItems: "center",
          gap: 30,
        })}
      >
        <img
          src={thumbnail}
          css={{
            width: 250,
            objectFit: "contain",
            ":hover": {
              cursor: "pointer",
            },
          }}
        />
        <div
          css={css({
            display: "flex",
            flexDirection: "column",
            gap: 10,
            fontWeight: 700,
          })}
        >
          <div
            css={css({
              fontSize: 35,
              color: colors.yiu.darkBlue,
            })}
          >
            {temp_council.name}
          </div>
          <div
            css={css({
              fontSize: 20,
              color: colors.gray.darkGray,
            })}
          >
            {temp_council.slogan}
          </div>
          <div
            css={css({
              fontWeight: 500,
              fontSize: 17,
              color: colors.text.body,
              lineHeight: 1.5,
            })}
          >
            {temp_council.description}
          </div>
        </div>
      </div>
    );
  };

  // 학생회 사업 및 행사
  const StudentCouncilEvents = () => {
    return (
      <div>
        <SectionTitle title={"학생회 사업 및 행사"} />
        <H20 />
        <div css={containerStyle}>
          <table css={tableStyle}>
            <tbody>
              {tempCouncilEvents.map((event, index) => (
                <tr key={index}>
                  <td css={titleStyle}>{event.title}</td>
                  <td css={descStyle}>{event.description}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  };

  // 학생회 조직도
  const StudentCouncilPeople = () => {
    return (
      <div>
        <SectionTitle title={"조직도"} />
        <H20 />
        <img
          src={people}
          css={{
            width: "100%",
            maxWidth: 700,
            objectFit: "contain",
            ":hover": {
              cursor: "pointer",
            },
          }}
        />
      </div>
    );
  };

  // MAIN
  return (
    <div>
      <Title title="학생회 소개" />
      <div
        css={css({
          marginTop: 50,
          marginBottom: 50,
          display: "flex",
          flexDirection: "column",
          gap: 50,
        })}
      >
        {/* 학생회 소개 */}
        <StudentCouncilIntro />
        {/* 학생회 사업 및 행사 */}
        <StudentCouncilEvents />
        {/* 조직도 */}
        <StudentCouncilPeople />
      </div>
    </div>
  );
};

export default StudentCouncilPage;

// 스타일
const containerStyle = css`
  width: 100%;
  display: flex;
  justify-content: start;
`;

const tableStyle = css`
  width: 100%;
  /* max-width: 900px; */
  border-collapse: collapse;
  border-top: 2px solid #444;
  border-bottom: 2px solid #444;
  font-size: 16px;
  line-height: 1.6;

  tr {
    border-top: 1px solid #ddd;
  }
`;

const titleStyle = css`
  width: 25%;
  font-weight: bold;
  padding: 12px;
  background-color: #f9f9f9;
`;

const descStyle = css`
  width: 75%;
  padding: 12px;
`;

// const styles = {
//   container: css`
//     max-width: 1200px;
//     margin: auto;
//     padding: 50px 20px;
//   `,
//   title: css`
//     text-align: center;
//     font-size: 32px;
//     font-weight: bold;
//     margin-bottom: 40px;
//   `,
//   grid: css`
//     display: grid;
//     grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
//     gap: 20px;
//   `,
//   card: css`
//     background: white;
//     border-radius: 10px;
//     overflow: hidden;
//     box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
//     transition: transform 0.3s ease;
//     &:hover {
//       transform: translateY(-5px);
//     }
//   `,
//   image: css`
//     width: 100%;
//     height: 200px;
//     object-fit: cover;
//   `,
//   cardContent: css`
//     padding: 20px;
//   `,
//   cardTitle: css`
//     font-size: 20px;
//     font-weight: bold;
//     margin-bottom: 10px;
//   `,
//   cardDesc: css`
//     font-size: 14px;
//     color: gray;
//   `,
// };
