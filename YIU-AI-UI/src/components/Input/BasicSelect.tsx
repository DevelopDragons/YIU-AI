/** @jsxImportSource @emotion/react */
/** @jsxImportSource @emotion/react */
import { css } from "@emotion/react";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import { colors } from "../../assets/styles/colors";
import { ReactNode } from "react";

interface BasicSelectProps<T = string | number> {
  name: string;
  label: string;
  value: T;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  fullWidth?: boolean;
  children: ReactNode;
}

const BasicSelect = (props: BasicSelectProps): React.ReactElement => {
  return (
    <TextField
      select
      name={props.name}
      label={props.label}
      value={props.value}
      onChange={props.onChange}
      fullWidth={props.fullWidth}
      css={css({
        "& .MuiOutlinedInput-root": {
          "&.Mui-focused fieldset": {
            borderColor: colors.yiu.green,
          },
        },
        "& label.Mui-focused": {
          color: colors.yiu.green, // 포커스 시 라벨 색
        },
      })}
    >
      {props.children}
    </TextField>
  );
};

export default BasicSelect;
