/** @jsxImportSource @emotion/react */
import React, { useState } from "react";
import { css } from "@emotion/react";
import { TableRow, TableCell, Collapse } from "@mui/material";
import { SubjectProps } from "../../models/subject";
import { colors } from "../../assets/styles/colors";
import { border1 } from "../../assets/styles/borderLine";
import { useResponsive } from "../../hooks/ResponsiveContext";
import { Graduation } from "../../models/graduation";
import PDFViewer from "../PDF/PdfViewer";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

interface GraduationRequirementGroupProps {
  item: Graduation;
  first: boolean;
}

const GraduationRequirementGroup = ({
  item,
  first,
}: GraduationRequirementGroupProps): React.ReactElement => {
  // 반응형 화면
  const { isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  const [open, setOpen] = useState(first ? true : false);

  return (
    <>
      <div
        onClick={() => setOpen(!open)}
        css={css({
          color: colors.yiu.green,
          fontWeight: 700,
          fontSize: 25,
          paddingBottom: 10,
          borderBottom: `1.5px solid ${colors.gray.mediumBrightGray}`,
          marginBottom: 30,
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
          cursor: "pointer",
        })}
      >
        {item.year}
        <ExpandMoreIcon
          css={css({
            color: colors.gray.mediumBrightGray,
          })}
        />
      </div>
      <Collapse
        in={open}
        timeout="auto"
        css={css({ paddingBottom: open ? 50 : 0 })}
      >
        <PDFViewer pdf={`${process.env.REACT_APP_URL}/files/show?id=${item.file?.[0]?.id}`} />
      </Collapse>
    </>
  );
};

export default GraduationRequirementGroup;
