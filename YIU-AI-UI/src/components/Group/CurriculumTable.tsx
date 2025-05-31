/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TitleBgImg from "../../assets/images/page_title_bg_img.jpg";
import { useResponsive } from "../../hooks/ResponsiveContext";
import altImg from "../../assets/images/alt_professor.jpg";
import {
  Paper,
  Tab,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Tabs,
} from "@mui/material";
import { border1 } from "../../assets/styles/borderLine";
import CurriculumListItem from "./CurriculumListItem";
import { Curriculum } from "../../models/curriculum";

interface CurriculmTableProps {
  data?: Curriculum[];
}

const CurriculumTable = ({ data }: CurriculmTableProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <TableContainer component={Paper} elevation={0} square>
      <Table
        // stickyHeader
        css={css({
          borderCollapse: "collapse",
        })}
      >
        <TableHead>
          <TableRow>
            <TableCell align="center" rowSpan={2} css={headerCellStyle}>
              이수구분
            </TableCell>
            <TableCell align="center" rowSpan={2} css={headerCellStyle}>
              학년
            </TableCell>
            <TableCell align="center" rowSpan={2} css={headerCellStyle}>
              학기
            </TableCell>
            {isDesktopOrLaptop ||
              (isTablet && (
                <TableCell align="center" rowSpan={2} css={headerCellStyle}>
                  학수번호
                </TableCell>
              ))}
            <TableCell align="center" rowSpan={2} css={headerCellStyle}>
              교과목명
            </TableCell>

            {isDesktopOrLaptop && (
              <>
                <TableCell align="center" rowSpan={2} css={headerCellStyle}>
                  학점
                </TableCell>
                <TableCell align="center" colSpan={2} css={headerCellStyle}>
                  주간수업시수
                </TableCell>
              </>
            )}
          </TableRow>
          {isDesktopOrLaptop && (
            <>
              <TableRow>
                <TableCell align="center" css={headerCellStyle}>
                  이론
                </TableCell>
                <TableCell align="center" css={headerCellStyle}>
                  실습
                </TableCell>
              </TableRow>
            </>
          )}
        </TableHead>
        <TableBody
          css={css({
            border: border1,
            // border: "none",
            // boxShadow: "none",
          })}
        >
          {data?.map((row) => (
            <CurriculumListItem key={row.id} item={row} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default CurriculumTable;

const headerCellStyle = css({
  border: border1,
  fontWeight: 800,
  fontSize: 16,
  backgroundColor: colors.gray.brightLightGray,
});
