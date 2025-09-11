/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useRecoilState } from "recoil";
import { SelectedNewsAtom } from "../../recoil/news";
import DOMPurify from "dompurify";
import dayjs from "dayjs";
import PageHeader from "../../components/Text/PageHeader";
import TitleBgImg from "../../assets/images/PageHeader/news.jpg";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import { News } from "../../models/news";
import { useNavigate } from "react-router-dom";
import ListIcon from "@mui/icons-material/List";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { border1 } from "../../assets/styles/borderLine";
import NewsNavButton from "../../components/Button/NewsNavButton";
import {
  Box,
  Grid,
  Avatar,
  Button,
  Dialog,
  DialogContent,
  IconButton,
} from "@mui/material";
import { quillStyles } from "../../assets/styles/common";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { useState } from "react";

const NewsDetailPage = (): React.ReactElement => {
  const [selectedNews, setSelectedNews] = useRecoilState(SelectedNewsAtom);
  const navigate = useNavigate();
  const [galleryOpen, setGalleryOpen] = useState(false);
  const [currentGalleryIndex, setCurrentGalleryIndex] = useState(0);

  const { data: news } = useQuery<News[]>({
    queryKey: ["news"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/news`);
      return res.data.slice().reverse();
    },
  });

  if (!selectedNews) return <div>뉴스를 선택해주세요.</div>;

  const currentIndex = news?.findIndex((n) => n.id === selectedNews.id) ?? -1;
  const prevNews = currentIndex > 0 ? news?.[currentIndex - 1] : null;
  const nextNews =
    news && currentIndex < news.length - 1 ? news[currentIndex + 1] : null;

  // 갤러리 클릭
  const handleGalleryClick = (index: number) => {
    setCurrentGalleryIndex(index);
    setGalleryOpen(true);
  };
  const handleGalleryClose = () => setGalleryOpen(false);
  const prevGallery = () => {
    if (!selectedNews?.gallery) return;
    setCurrentGalleryIndex(
      (prev) =>
        (prev - 1 + selectedNews.gallery.length) % selectedNews.gallery.length
    );
  };
  const nextGallery = () => {
    if (!selectedNews?.gallery) return;
    setCurrentGalleryIndex((prev) => (prev + 1) % selectedNews.gallery.length);
  };

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

        {/* 날짜 */}
        <div
          css={css({
            display: "flex",
            flexDirection: "row",
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
        {selectedNews.thumbnails?.[0] && (
          <img
            src={`${process.env.REACT_APP_URL}/files/show?id=${selectedNews.thumbnails?.[0]?.id}`}
            css={css({
              width: "100%",
              maxWidth: 900,
              objectFit: "contain",
              border: border1,
              alignSelf: "center",
            })}
          />
        )}

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
        <Box sx={quillStyles}>
          <div
            className="ql-editor"
            css={css({ padding: 10, marginBottom: 30 })}
            dangerouslySetInnerHTML={{
              __html: DOMPurify.sanitize(selectedNews.contents, {
                ADD_TAGS: ["iframe", "video", "source"],
                ADD_ATTR: [
                  "allow",
                  "allowfullscreen",
                  "frameborder",
                  "scrolling",
                  "playsinline",
                  "controls",
                  "autoplay",
                  "loop",
                  "muted",
                  "class",
                  "style",
                  "target",
                  "rel",
                ],
                KEEP_CONTENT: true,
              }),
            }}
          />
        </Box>

        {/* 갤러리 */}
        {selectedNews.gallery && selectedNews.gallery.length > 0 && (
          <div>
            <h3
              css={css({
                fontSize: 20,
                fontWeight: 700,
                marginBottom: 15,
              })}
            >
              Gallery
            </h3>
            <Grid container spacing={2}>
              {selectedNews.gallery.map((item, idx) => (
                <Grid item xs={6} sm={4} md={3} key={item.id}>
                  <Avatar
                    variant="rounded"
                    src={`${process.env.REACT_APP_URL}/files/show?id=${item.id}`}
                    sx={{
                      width: "100%",
                      height: 120,
                      objectFit: "cover",
                      cursor: "pointer",
                    }}
                    onClick={() => handleGalleryClick(idx)}
                  />
                </Grid>
              ))}
            </Grid>
          </div>
        )}

        {/* 첨부파일 */}
        {selectedNews.file && selectedNews.file.length > 0 && (
          <div
            css={css({
              marginTop: 30,
              display: "flex",
              flexDirection: "column",
              gap: 10,
            })}
          >
            <h3
              css={css({
                fontSize: 20,
                fontWeight: 700,
                marginBottom: 10,
              })}
            >
              Files
            </h3>
            {selectedNews.file.map((item) => (
              <Button
                key={item.id}
                variant="outlined"
                sx={{ textTransform: "none", justifyContent: "flex-start" }}
                onClick={() =>
                  window.open(
                    `${process.env.REACT_APP_URL}/files/download?id=${item.id}`,
                    "_blank"
                  )
                }
              >
                {item.originName}
              </Button>
            ))}
          </div>
        )}

        {/* 이전/목록/다음 */}
        <div
          css={css({
            borderTop: border1,
            marginTop: 50,
            marginBottom: 50,
            display: "flex",
            flexDirection: "column",
            gap: 10,
            fontSize: 14,
            color: colors.gray.darkGray,
          })}
        >
          <div
            css={css({
              alignSelf: "end",
              display: "flex",
              alignItems: "center",
              padding: 10,
              gap: 5,
              cursor: "pointer",
              color: colors.gray.mediumGray,
            })}
            onClick={() => navigate("/news")}
          >
            <ListIcon />
            <span>목록</span>
          </div>

          {prevNews && (
            <NewsNavButton
              type="prev"
              newsItem={prevNews}
              onClick={(item) => {
                setSelectedNews(item);
                navigate(`/news/:${item.id}`, { replace: true });
              }}
            />
          )}
          {nextNews && (
            <NewsNavButton
              type="next"
              newsItem={nextNews}
              onClick={(item) => {
                setSelectedNews(item);
                navigate(`/news/:${item.id}`, { replace: true });
              }}
            />
          )}
        </div>
      </div>

      {/* 갤러리 모달 */}
      <Dialog open={galleryOpen} onClose={handleGalleryClose} maxWidth="lg">
        <DialogContent
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            position: "relative",
            p: 2,
          }}
        >
          <IconButton
            onClick={prevGallery}
            sx={{ position: "absolute", left: 0, top: "50%" }}
          >
            <ArrowBackIosIcon />
          </IconButton>

          {selectedNews?.gallery && selectedNews.gallery.length > 0 && (
            <img
              src={`${process.env.REACT_APP_URL}/files/show?id=${selectedNews.gallery[currentGalleryIndex].id}`}
              style={{
                maxHeight: "80vh",
                maxWidth: "90vw",
                objectFit: "contain",
              }}
            />
          )}

          <IconButton
            onClick={nextGallery}
            sx={{ position: "absolute", right: 0, top: "50%" }}
          >
            <ArrowForwardIosIcon />
          </IconButton>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default NewsDetailPage;
