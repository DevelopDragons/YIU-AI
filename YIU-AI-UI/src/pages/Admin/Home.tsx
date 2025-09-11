/** @jsxImportSource @emotion/react */
import { useState, useEffect } from "react";
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import BasicInput from "../../components/Input/BasicInput";
import { IconButton, InputAdornment, SelectChangeEvent } from "@mui/material";
import FullWidthButton from "../../components/Button/FullWidthButton";
import { defaultAPI } from "../../services";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { handleError } from "../../services/ErrorHandling";
import { ApiType } from "../../services/type";
import { setUser } from "../../utils/session";

const AdminHome = (): React.ReactElement => {
  const navigate = useNavigate();

  return (
    <div
      css={css({
        display: "flex",
        justifyContent: "center", // 가로 가운데
        alignItems: "center", // 세로 가운데
        minHeight: 700,
        fontSize: 20,
        fontWeight: 700,
        color: colors.gray.mediumGray,
      })}
    >
      준비중
    </div>
  );
};

export default AdminHome;
