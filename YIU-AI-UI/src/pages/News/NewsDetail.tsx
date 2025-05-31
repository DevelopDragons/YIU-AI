/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { temp_news } from "../../assets/data/temp/temp_news";
import { useRecoilState } from "recoil";
import { SelectedNewsAtom } from "../../recoil/notice";
import dayjs from "dayjs";
import { border1 } from "../../assets/styles/borderLine";
import PageHeader from "../../components/Text/PageHeader";
import TitleBgImg from "../../assets/images/PageHeader/news.jpg";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";

const NewsDetailPage = (): React.ReactElement => {
  const [selectedNews, setSelectedNews] = useRecoilState(SelectedNewsAtom);

  return (
    <div>
      <PageHeader backgroundImage={TitleBgImg} title={"학부소식"} />
      <div
        css={css({
          padding: "5%",
          maxWidth: 1000,
          margin: "0 auto",
          display: "flex",
          flexDirection: "column",
          gap: 50,
          marginTop: 50,
          marginBottom: 100,
        })}
      >
        {/* 제목 */}
        <div
          css={css({
            fontSize: 30,
            color: colors.yiu.green,
            fontWeight: 700,
            textAlign: "center",
          })}
        >
          {selectedNews.title}
        </div>
        {/* 날짜 및 정보 */}
        <div
          css={css({
            display: "flex",
            flexDirection: "row",
            // justifyContent: "space-between",
            gap: 5,
            alignItems: "center",
            borderTop: border1,
            borderBottom: border1,
            paddingTop: 15,
            paddingBottom: 15,
            color: colors.gray.mediumGray,
          })}
        >
          <CalendarMonthIcon fontSize="small" />
          <span>
            {dayjs(selectedNews.createAt).format("YYYY-MM-DD hh:mm:ss")}
          </span>
        </div>
        {/* 썸네일 */}
        <img
          src={`${process.env.REACT_APP_URL}/files/show?id=${selectedNews.thumbnails?.[0]?.id}`}
          // src={boss}
          css={css({
            width: "100%",
            maxWidth: 900,
            objectFit: "contain",
            border: border1,
            alignSelf: "center",
          })}
        />
        {/* 단신 */}
        <div
          css={css({
            backgroundColor: colors.gray.brightLightGray,
            color: colors.yiu.darkBlue,
            fontSize: 20,
            fontWeight: 700,
            padding: 20,
            textAlign: "center",
          })}
        >
          {selectedNews.shorts}
        </div>
        {/* 본문 */}
        <div
          css={css({
            fontSize: 16,
            color: colors.text.black,
          })}
        >
          {selectedNews.contents}
        </div>
      </div>
    </div>
  );
};

export default NewsDetailPage;
