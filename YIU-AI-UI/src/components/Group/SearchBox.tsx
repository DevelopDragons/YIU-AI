/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import {
  Select,
  MenuItem,
  TextField,
  IconButton,
  InputAdornment,
  SelectChangeEvent,
} from "@mui/material";
import { useResponsive } from "../../hooks/ResponsiveContext";
import MediumButton from "../Button/MediumButton";
import CloseIcon from "@mui/icons-material/Close";
import { colors } from "../../assets/styles/colors";

const commonHeight = 40;

interface SearchBoxProps {
  category: string;
  onChangeCategory: (e: SelectChangeEvent<string>) => void;
  keyword: string;
  onChangeKeyword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onClick: () => void;
  onClickClear: () => void;
}

const SearchBox = (props: SearchBoxProps): React.ReactElement => {
  const { isNarrow, isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    <div
      css={css({
        display: "flex",
        gap: 10,
        marginBottom: 20,
        flexWrap: "wrap",
        alignItems: "center",
      })}
    >
      {/* 카테고리 선택 */}
      {!isNarrow && (
        <Select
          value={props.category}
          onChange={props.onChangeCategory}
          size="small"
          sx={{
            height: commonHeight,
            "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
              borderColor: colors.yiu.green, // 포커스 시 테두리 색
            },
            // "&.Mui-focused .MuiSelect-select": {
            //   backgroundColor: colors.yiu.green_light, // 선택창 배경색 (선택)
            // },
            // "&.Mui-focused": {
            //   color: "#4CAF50", // 글씨 색
            // },
          }}
        >
          <MenuItem value="all">전체</MenuItem>
          <MenuItem value="title">제목</MenuItem>
          <MenuItem value="content">내용</MenuItem>
        </Select>
      )}
      {/* 검색어 입력 + X 버튼 */}
      <TextField
        value={props.keyword}
        onChange={props.onChangeKeyword}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            props.onClick();
          }
        }}
        placeholder="검색어를 입력하세요"
        size="small"
        sx={{
          flex: 1,
          minWidth: 150,
          height: commonHeight,
          "& .MuiOutlinedInput-root": {
            "&.Mui-focused fieldset": {
              borderColor: colors.yiu.green, // 포커스 시 테두리 색
            },
          },
          "& label.Mui-focused": {
            color: colors.yiu.green, // 포커스 시 라벨 색
          },
        }}
        InputProps={{
          endAdornment: props.keyword && (
            <InputAdornment position="end">
              <IconButton size="small" onClick={props.onClickClear}>
                <CloseIcon fontSize="small" />
              </IconButton>
            </InputAdornment>
          ),
        }}
      />

      {/* 검색 버튼 */}
      <MediumButton
        title="검색"
        onClick={props.onClick}
        sxOverride={{
          height: commonHeight - 2,
        }}
      />
    </div>
  );
};

export default SearchBox;
