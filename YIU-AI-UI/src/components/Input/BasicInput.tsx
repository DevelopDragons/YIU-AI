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

interface BasicInputProps {
  key: string;
  name: string;
  label: string;
  type?: string;
  value: string;
  placeholder?: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onClick?: () => void;
  InputProps?: any;
  disabled?: boolean;
}

const BasicInput = (props: BasicInputProps): React.ReactElement => {
  const { isNarrow, isMobile, isNotMobile, isTablet, isDesktopOrLaptop } =
    useResponsive();

  return (
    // <TextField
    //   key={props.key}
    //   label={props.label}
    //   variant="outlined"
    //   fullWidth
    //   sx={{
    //     flex: 1,
    //     minWidth: 150,
    //     height: commonHeight,
    //     "& .MuiOutlinedInput-root": {
    //       "&.Mui-focused fieldset": {
    //         borderColor: colors.yiu.green, // 포커스 시 테두리 색
    //       },
    //     },
    //     "& label.Mui-focused": {
    //       color: colors.yiu.green, // 포커스 시 라벨 색
    //     },
    //   }}
    //   InputProps={{
    //     endAdornment: props.keyword && (
    //       <InputAdornment position="end">
    //         <IconButton size="small" onClick={props.onClickClear}>
    //           <CloseIcon fontSize="small" />
    //         </IconButton>
    //       </InputAdornment>
    //     ),
    //   }}
    // />
    <TextField
      key={props.key}
      name={props.name}
      label={props.label}
      type={props.type ?? "text"}
      variant="outlined"
      fullWidth
      value={props.value}
      onChange={props.onChange}
      onKeyDown={(e) => {
        if (e.key === "Enter") {
          props.onClick && props.onClick(); // ✨ 이렇게 수정하세요!
        }
      }}
      placeholder={props.placeholder ?? ""}
      disabled={props.disabled ?? false}
      sx={{
        "& .MuiOutlinedInput-root": {
          "&.Mui-focused fieldset": {
            borderColor: colors.yiu.green, // 포커스 시 테두리 색
          },
        },
        "& label.Mui-focused": {
          color: colors.yiu.green, // 포커스 시 라벨 색
        },
      }}
      InputProps={props.InputProps}
    />
  );
};

export default BasicInput;
