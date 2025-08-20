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

const SignInPage = (): React.ReactElement => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    id: "",
    pwd: "",
  });

  const [showPassword, setShowPassword] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // 로그인 유효성 검사
  const validateForm = () => {
    const { id, pwd } = formData;

    if (!id || !pwd) {
      alert("학번과 비밀번호를 모두 입력하세요.");
      return false;
    }

    return true;
  };

  const _doSignIn = async () => {
    if (!validateForm()) return;
    try {
      const response = await defaultAPI.post(`/login`, formData);
      // 로그인 결과 저장
      sessionStorage.setItem("user", JSON.stringify(response.data));
      sessionStorage.setItem("accessToken", response.data.token.accessToken);
      sessionStorage.setItem("refreshToken", response.data.token.refreshToken);
      navigate("/");
    } catch (err) {
      handleError(ApiType.SIGN_IN, err);
    }
  };

  return (
    <div
      css={css({
        height: 800,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        // backgroundColor: colors.gray.lightGray,
      })}
    >
      <Box
        css={css({
          width: 400,
          padding: "40px 30px",
          backgroundColor: colors.gray.white,
          borderRadius: 12,
          display: "flex",
          flexDirection: "column",
          gap: 25,
        })}
      >
        {/* 타이틀 */}
        <Typography
          variant="h4"
          css={css({
            fontWeight: 800,
            textAlign: "center",
            color: colors.gray.black,
          })}
        >
          로그인
        </Typography>

        {/* 아이디 입력 */}
        <BasicInput
          key={"id"}
          name={"id"}
          label={"학번"}
          value={formData.id}
          placeholder={"학번을 입력하세요 ex) 202033013"}
          onChange={handleInputChange}
          onClick={() => _doSignIn()}
        />

        {/* 비밀번호 입력 */}
        <BasicInput
          key={"pwd"}
          name={"pwd"}
          label={"비밀번호"}
          type={showPassword ? "text" : "password"}
          value={formData.pwd}
          placeholder={"비밀번호를 입력하세요"}
          onChange={handleInputChange}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  onClick={() => setShowPassword(!showPassword)}
                  edge="end"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            ),
          }}
          onClick={() => _doSignIn()}
        />

        {/* 로그인 버튼 */}
        <FullWidthButton title={"로그인"} onClick={() => _doSignIn()} />

        {/* 회원가입 링크 */}
        <Typography
          variant="body2"
          css={css({
            textAlign: "center",
            color: colors.gray.darkGray,
            cursor: "pointer",
            "&:hover": { textDecoration: "underline" },
          })}
          onClick={() => navigate("/sign-up")}
        >
          계정이 없으신가요? 회원가입
        </Typography>
      </Box>
    </div>
  );
};

export default SignInPage;
