/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import {
  Box,
  Button,
  Tab,
  Tabs,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import { useState, useEffect } from "react";
import { MicroDegreeCategory } from "../../models/enum";
import { MD } from "../../models/md";
import Title from "../../components/Text/Title";
import { colors } from "../../assets/styles/colors";
import { SubjectProps } from "../../models/subject";
import { border1 } from "../../assets/styles/borderLine";
import CurriculumListItem from "../../components/Group/CurriculumListItem";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { useQuery } from "@tanstack/react-query";
import { defaultAPI } from "../../services";
import LoadingSpin from "../../components/Spin/LoadingSpin";
import GetDataErrorResultView from "../../components/Result/GetDataError";

const ConvergenceMdPage = (): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const [tabValue, setTabValue] = useState(0);
  const [selectedMD, setSelectedMD] = useState<MD | null>(null);

  const getTabData = () => {
    if (tabValue === 0) {
      return convergenceAndMD?.filter(
        (item) => item.category === MicroDegreeCategory.Convergence
      ) ?? [];
    }
    return convergenceAndMD?.filter((item) => item.category === MicroDegreeCategory.MD) ?? [];
  };

  useEffect(() => {
    const initialData = getTabData()[0] || null;
    setSelectedMD(initialData);
  }, [tabValue]);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  const handleBoxClick = (md: MD) => {
    setSelectedMD(md);
  };

  const {
    data: convergenceAndMD,
    isLoading,
    error
  } = useQuery<MD[]>({
    queryKey: ["convergenceAndMD"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/microdegree`);
      return res.data;
    }
  })

  if (isLoading) return <LoadingSpin />
  if (error) return <GetDataErrorResultView />

  

  return (
    <div>
      <Title title="융합전공/마이크로디그리(MD)" />
      <Tabs
        value={tabValue}
        onChange={handleTabChange}
        centered
        variant="fullWidth"
        TabIndicatorProps={{
          style: { backgroundColor: colors.yiu.green, height: 5 },
        }}
        sx={{
          "& .MuiTab-root": {
            fontSize: isMobile ? 18 : 20,
            fontWeight: 600,
            color: "#BDBDBD", // 비활성화된 탭 색상
          },
          "& .Mui-selected": {
            color: `${colors.yiu.green} !important`, // 활성화된 탭 색상
          },
        }}
        css={css({ marginTop: 50 })}
      >
        <Tab label="융합전공" />
        <Tab label="마이크로디그리(MD)" />
      </Tabs>

      <Box
        css={css({
          display: "flex",
          justifyContent: "start",
          gap: 20,
          marginTop: 30,
        })}
      >
        {getTabData().map((md) => (
          <Button
            key={md.title}
            onClick={() => handleBoxClick(md)}
            css={css({
              padding: 15,
              minWidth: isMobile ? 100 : 200,
              borderRadius: 0,
              fontSize: isMobile ? 14 : 16,
              border: `1.5px solid ${colors.gray.lightGray}`,
              backgroundColor:
                selectedMD?.title === md.title
                  ? colors.yiu.green
                  : colors.gray.white,
              color:
                selectedMD?.title === md.title
                  ? colors.gray.white
                  : colors.yiu.green,
              fontWeight: selectedMD?.title === md.title ? 800 : 600,
              textAlign: "center",
              cursor: "pointer",
              "&:hover": {
                backgroundColor:
                  selectedMD?.title === md.title
                    ? colors.yiu.green
                    : colors.gray.brightLightGray,
                transition: "all 0.3s ease-in-out",
              },
            })}
          >
            {md.title}
          </Button>
        ))}
      </Box>

      {selectedMD && (
        <Box
          css={css({
            display: "flex",
            flexDirection: "column",
            gap: 30,
            marginTop: 50,
            padding: 50,
            borderLeft: `5px solid ${colors.yiu.green}`,
          })}
        >
          <div>
            <h2>개요</h2>
            <p>{selectedMD.description}</p>
          </div>

          <div>
            <h2>이수요건</h2>
            <div>
              <TableContainer
                component={Paper}
                elevation={0}
                square
                css={css({ border: "none" })}
              >
                <Table
                  // stickyHeader
                  css={css({
                    borderCollapse: "collapse",
                  })}
                >
                  <TableHead>
                    {!isMobile && (
                      <TableCell align="center" css={headerCellStyle}>
                        학수번호
                      </TableCell>
                    )}
                    <TableCell align="center" css={headerCellStyle}>
                      과목명
                    </TableCell>
                    <TableCell align="center" css={headerCellStyle}>
                      이수구분
                    </TableCell>
                    {!isMobile && (
                      <TableCell align="center" css={headerCellStyle}>
                        학점
                      </TableCell>
                    )}
                  </TableHead>
                  <TableBody
                    css={css({
                      border: border1,
                      // border: "none",
                      // boxShadow: "none",
                    })}
                  >
                    {selectedMD.subjects.map((s: SubjectProps) => (
                      <CurriculumListItem key={s.id} item={s} full={false} />
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </div>
          </div>
        </Box>
      )}
    </div>
  );
};

const headerCellStyle = css({
  border: border1,
  fontWeight: 800,
  fontSize: 16,
  backgroundColor: colors.gray.brightLightGray,
});

export default ConvergenceMdPage;
