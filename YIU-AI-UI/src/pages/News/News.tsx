/** @jsxImportSource @emotion/react */
import { useState, useEffect } from "react";
import { css } from "@emotion/react";
import PageHeader from "../../components/Text/PageHeader";
import TitleBgImg from "../../assets/images/PageHeader/news.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { SelectedNewsAtom } from "../../recoil/notice";
import { useRecoilState } from "recoil";
import { useNavigate } from "react-router-dom";
import NewsListItem from "../../components/Group/NewsListItem";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";
import { News } from "../../models/news";
import SearchBox from "../../components/Group/SearchBox";

const NewsPage = (): React.ReactElement => {
  const navigate = useNavigate();
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const [selectedNews, setSelectedNews] = useRecoilState(SelectedNewsAtom);

  const [searchCategory, setSearchCategory] = useState("all");
  const [searchText, setSearchText] = useState("");
  const [filteredNews, setFilteredNews] = useState<News[]>([]);

  const {
    data: news,
    isLoading,
    error,
  } = useQuery<News[]>({
    queryKey: ["news"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/news`);
      return res.data;
    },
  });

  // 검색
  const handleSearch = () => {
    if (!news) return;
    const keyword = searchText.trim().toLowerCase();

    const result = news.filter((item) => {
      if (searchCategory === "title") {
        return item.title.toLowerCase().includes(keyword);
      }
      if (searchCategory === "content") {
        return item.contents.toLowerCase().includes(keyword);
      }
      // 전체 검색
      return (
        item.title.toLowerCase().includes(keyword) ||
        item.contents.toLowerCase().includes(keyword)
      );
    });

    setFilteredNews(result);
  };

  // 검색어 clear
  const handleClear = () => {
    setSearchText(""); // 검색어 초기화
    if (news) setFilteredNews(news); // 전체 데이터로 복구
  };

  // 처음 로드 시 전체 데이터 표시
  // category 변경 시 자동 재검색
  useEffect(() => {
    if (!news) return;
    const keyword = searchText.trim().toLowerCase();

    const result = news.filter((item) => {
      if (searchCategory === "title") {
        return item.title.toLowerCase().includes(keyword);
      }
      if (searchCategory === "content") {
        return item.contents.toLowerCase().includes(keyword);
      }
      // 전체 검색
      return (
        item.title.toLowerCase().includes(keyword) ||
        item.contents.toLowerCase().includes(keyword)
      );
    });

    setFilteredNews(result);
  }, [searchCategory, news]);

  if (isLoading) return <LoadingSpin />;
  if (error) return <GetDataErrorResultView />;

  return (
    <div>
      <PageHeader backgroundImage={TitleBgImg} title={"소식"} />
      <div
        css={css({
          padding: "5%",
          maxWidth: 1000,
          margin: "0 auto",
        })}
      >
        {/* 검색 */}
        {news && (
          <div
            css={css({
              marginLeft: 30,
              marginRight: 30,
            })}
          >
            <SearchBox
              category={searchCategory}
              onChangeCategory={(e) =>
                setSearchCategory(e.target.value as string)
              }
              keyword={searchText}
              onChangeKeyword={(e) => setSearchText(e.target.value)}
              onClick={handleSearch}
              onClickClear={handleClear}
            />
          </div>
        )}

        {/* 필터링된 뉴스 목록 */}
        {filteredNews.map((item) => (
          <NewsListItem
            key={item.id}
            item={item}
            onClick={() => {
              setSelectedNews(item);
              navigate(`/news/:${item.id}`);
            }}
          />
        ))}
      </div>
    </div>
  );
};

export default NewsPage;
