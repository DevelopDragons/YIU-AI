/** @jsxImportSource @emotion/react */
import { useQuery } from "@tanstack/react-query";
import { css } from "@emotion/react";
import { colors } from "../../assets/styles/colors";
import { useState } from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { border1 } from "../../assets/styles/borderLine";
import {
  Checkbox,
  FormControlLabel,
  InputAdornment,
  IconButton,
  Paper,
  MenuItem,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import FullWidthButton from "../../components/Button/FullWidthButton";
import { signUpCondition } from "../../assets/data/sign_up_condition";
import MediumButton from "../../components/Button/MediumButton";
import PrivacyPolicyTable from "../../components/Table/PrivacyPolicyTable";
import {
  UserDepartment,
  UserDepartmentLabel,
  UserEntrance,
  UserRole,
  UserStatus,
  UserStatusLabel,
  UserTrack,
  UserTrackLabel,
} from "../../models/enum";
import { Member, MemberRole } from "../../models/member";
import { defaultAPI } from "../../services";
import { authAPI } from "../../services";
import BasicInput from "../../components/Input/BasicInput";
import BasicSelect from "../../components/Input/BasicSelect";
import { useNavigate } from "react-router-dom";
import { handleError } from "../../services/ErrorHandling";
import { ApiType } from "../../services/type";

const SignUpPage = (): React.ReactElement => {
  const navigate = useNavigate();

  const [step, setStep] = useState<1 | 2>(1);
  const [checked, setChecked] = useState({
    all: false,
    terms: false,
    privacy: false,
  });

  const [formData, setFormData] = useState({
    id: "",
    pwd: "",
    confirmPwd: "",
    name: "",
    grade: 1,
    role: UserRole.STUDENT,
    status: UserStatus.STUDENT,
    major: "",
    department: UserDepartment.AI_CONVERGENCE,
    track: UserTrack.SINGLE,
    entrance: UserEntrance.FRESH,
    professor: "이경재",
    emailVerifyCode: "",
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  // 이메일 인증 관련 상태
  const [emailStep, setEmailStep] = useState<"idle" | "verify" | "done">(
    "idle"
  );
  const [authNum, setAuthNum] = useState<string>("");
  const [resendCount, setResendCount] = useState<number>(0);

  const handleCheck = (key: keyof typeof checked) => {
    setChecked((prev) => {
      const updated = { ...prev, [key]: !prev[key] };
      if (key === "all") {
        Object.keys(updated).forEach((k) => {
          updated[k as keyof typeof updated] = updated.all;
        });
      } else {
        updated.all = updated.terms && updated.privacy;
      }
      return updated;
    });
  };

  const handleNextStep = () => {
    if (checked.terms && checked.privacy) {
      setStep(2);
    } else {
      alert("필수 약관에 동의해야 합니다.");
    }
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | { name?: string; value: unknown }>
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name!]: value }));
  };

  // 이메일 인증 요청
  const sendEmailVerification = async () => {
    if (!formData.id) {
      alert("학번을 먼저 입력하세요!");
      return;
    }
    try {
      const data = { id: formData.id };
      const response = await authAPI.post(`/register/email`, data);
      setAuthNum(response.data); // 인증번호 저장
      alert("인증 메일이 발송되었습니다.");
      setEmailStep("verify");
      // 학번에서 major 추출 (5,6번째 자리)
      setFormData((prev) => ({
        ...prev,
        major: prev.id.slice(4, 6),
      }));
    } catch (err) {
      alert("이메일 인증 요청에 실패했습니다.");
    }
  };

  // 이메일 인증번호 재전송
  const resendEmailVerification = async () => {
    if (!formData.id) {
      alert("학번을 먼저 입력하세요!");
      return;
    }
    if (resendCount >= 5) {
      alert("인증번호 재전송은 최대 5회까지만 가능합니다.");
      return;
    }
    try {
      const data = { id: formData.id };
      const response = await authAPI.post(`/register/email`, data);
      setAuthNum(response.data);
      setResendCount((prev) => prev + 1);
      alert("인증번호가 재전송되었습니다.");
    } catch (err) {
      alert("인증번호 재전송에 실패했습니다.");
    }
  };

  // 이메일 인증 확인
  const verifyEmailCode = () => {
    if (authNum == formData.emailVerifyCode) {
      alert("이메일 인증이 완료되었습니다!");
      setEmailStep("done");
    } else {
      alert("인증번호가 일치하지 않습니다.");
    }
  };

  // 회원가입 유효성 검사
  const validateForm = () => {
    const { pwd, confirmPwd, emailVerifyCode, name, id } = formData;

    if (emailStep !== "done") {
      alert("이메일 인증을 완료하세요.");
      return false;
    }

    if (!id || !name || !pwd || !confirmPwd || !emailVerifyCode) {
      alert("모든 정보를 입력하세요.");
      return false;
    }

    const pwdRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+=-]).{8,16}$/;
    if (!pwdRegex.test(pwd)) {
      alert("비밀번호는 영문자+숫자+특수문자를 포함한 8~16자리여야 합니다.");
      return false;
    }

    if (pwd !== confirmPwd) {
      alert("비밀번호가 일치하지 않습니다.");
      return false;
    }

    return true;
  };

  // 회원가입 요청
  const _doSignUp = async () => {
    if (!validateForm()) return;
    try {
      await defaultAPI.post(`/register`, formData);
      alert("회원가입이 완료되었습니다!");
      navigate("/sign-in");
    } catch (err) {
      handleError(ApiType.SIGN_UP, err);
    }
  };

  // 교수 데이터 가져오기
  const { data: professors = [] } = useQuery<Member[]>({
    queryKey: ["professor"],
    queryFn: async () => {
      const res = await defaultAPI.get(`/member`);
      return res.data.filter(
        (member: Member) => member.role === MemberRole.PROFESSOR
      );
    },
  });

  return (
    <div
      css={css({
        minHeight: 800,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        padding: 20,
        marginTop: 50,
        marginBottom: 100,
      })}
    >
      <Box
        css={css({
          width: step === 1 ? 700 : 500,
          padding: "40px 30px",
          backgroundColor: colors.gray.white,
          borderRadius: 12,
          display: "flex",
          flexDirection: "column",
          gap: 25,
        })}
      >
        {step === 1 && (
          <div>
            <Typography
              variant="h4"
              css={css({
                fontWeight: 700,
                color: colors.gray.black,
              })}
            >
              회원가입 약관
            </Typography>
            <Box
              css={css({
                display: "flex",
                flexDirection: "column",
                gap: 15,
                overflowY: "auto",
                marginTop: 50,
                marginBottom: 50,
              })}
            >
              {/* 전체 동의 */}
              <Box css={css({ borderBottom: border1, paddingBottom: 15 })}>
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={checked.all}
                      onChange={() => handleCheck("all")}
                      css={css({
                        "&.Mui-checked": {
                          color: colors.yiu.green,
                        },
                      })}
                      sx={{ "& .MuiSvgIcon-root": { fontSize: 24 } }}
                    />
                  }
                  label="전체 동의하기"
                  css={css({
                    fontWeight: 600,
                    "& .MuiTypography-root": {
                      fontWeight: 600,
                      fontSize: "1rem",
                    },
                  })}
                />
              </Box>

              {/* 필수 약관 */}
              <Box
                css={css({ display: "flex", flexDirection: "column", gap: 15 })}
              >
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={checked.terms}
                      onChange={() => handleCheck("terms")}
                      css={css({
                        "&.Mui-checked": {
                          color: colors.yiu.green,
                        },
                      })}
                      sx={{ "& .MuiSvgIcon-root": { fontSize: 20 } }}
                    />
                  }
                  label="[필수] 회원가입 약관"
                />
                <Box
                  css={css({
                    maxHeight: 150,
                    overflowY: "auto",
                    padding: 8,
                    border: border1,
                    borderRadius: 6,
                  })}
                >
                  <Typography variant="body2" color="text.secondary">
                    {signUpCondition}
                  </Typography>
                </Box>
              </Box>

              {/* 개인정보 */}
              <Box
                css={css({ display: "flex", flexDirection: "column", gap: 15 })}
              >
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={checked.privacy}
                      onChange={() => handleCheck("privacy")}
                      css={css({
                        "&.Mui-checked": {
                          color: colors.yiu.green,
                        },
                      })}
                      sx={{ "& .MuiSvgIcon-root": { fontSize: 20 } }}
                    />
                  }
                  label="[필수] 개인정보 수집 및 이용"
                />
                <Paper
                  css={css({
                    maxHeight: 200,
                    overflowY: "auto",
                    borderRadius: 5,
                  })}
                >
                  <PrivacyPolicyTable />
                </Paper>
              </Box>
            </Box>
            <FullWidthButton title={"다음"} onClick={handleNextStep} />
          </div>
        )}

        {step === 2 && (
          <>
            <Box css={css({ display: "flex", alignItems: "center", gap: 10 })}>
              <IconButton onClick={() => setStep(1)} size="small">
                <ArrowBackIcon />
              </IconButton>
              <Typography
                variant="h4"
                css={css({ fontWeight: 700, flexGrow: 1 })}
              >
                회원가입
              </Typography>
            </Box>
            <Box
              css={css({ display: "flex", flexDirection: "column", gap: 20 })}
            >
              {/* 학번 */}
              <BasicInput
                key={"id"}
                name={"id"}
                label={"학번"}
                value={formData.id}
                placeholder={"학번을 입력하세요."}
                onChange={handleInputChange}
                disabled={emailStep === "done"}
              />

              {/* 이메일 인증 */}
              {emailStep === "idle" && (
                <MediumButton
                  title="이메일 인증"
                  onClick={sendEmailVerification}
                />
              )}

              {emailStep === "verify" && (
                <>
                  <BasicInput
                    key={"emailVerifyCode"}
                    name={"emailVerifyCode"}
                    label={"인증번호 입력"}
                    value={formData.emailVerifyCode || ""}
                    placeholder={"학교 이메일로 전송된 인증번호를 입력하세요."}
                    onChange={handleInputChange}
                  />
                  <Box css={css({ display: "flex", gap: 10 })}>
                    <MediumButton
                      title="인증번호 재전송"
                      onClick={resendEmailVerification}
                    />
                    <MediumButton title="인증 확인" onClick={verifyEmailCode} />
                  </Box>
                </>
              )}

              {emailStep === "done" && (
                <MediumButton
                  title="인증 완료"
                  disabled={true}
                  onClick={function (): void {
                    throw new Error("Function not implemented.");
                  }}
                />
              )}

              {/* 비밀번호 */}
              <BasicInput
                key={"pwd"}
                name={"pwd"}
                label={"비밀번호 입력"}
                type={showPassword ? "text" : "password"}
                value={formData.pwd}
                placeholder={
                  "영문+특수문자+숫자를 포함하여 8~16자를 입력하세요."
                }
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
              />
              <BasicInput
                key={"confirmPwd"}
                name={"confirmPwd"}
                label={"비밀번호 확인"}
                type={showConfirmPassword ? "text" : "password"}
                value={formData.confirmPwd}
                onChange={handleInputChange}
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={() =>
                          setShowConfirmPassword(!showConfirmPassword)
                        }
                        edge="end"
                      >
                        {showConfirmPassword ? (
                          <VisibilityOff />
                        ) : (
                          <Visibility />
                        )}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />

              {/* 이름 */}
              <BasicInput
                key={"name"}
                name={"name"}
                label={"이름"}
                value={formData.name}
                placeholder={"이름을 입력하세요."}
                onChange={handleInputChange}
              />

              {/* 학년 */}
              <BasicSelect
                name="grade"
                label="학년"
                value={formData.grade}
                onChange={handleInputChange}
              >
                {[1, 2, 3, 4].map((g) => (
                  <MenuItem key={g} value={g}>
                    {g}학년
                  </MenuItem>
                ))}
              </BasicSelect>

              {/* 학적 */}
              <BasicSelect
                name="status"
                label="학적"
                value={formData.status}
                onChange={handleInputChange}
              >
                {Object.values(UserStatus).map((s) => (
                  <MenuItem key={s} value={s}>
                    {UserStatusLabel[s]}
                  </MenuItem>
                ))}
              </BasicSelect>

              {/* 전공 구분 */}
              <BasicSelect
                name="track"
                label="전공 구분"
                value={formData.track}
                onChange={handleInputChange}
              >
                {Object.values(UserTrack).map((t) => (
                  <MenuItem key={t} value={t}>
                    {UserTrackLabel[t]}
                  </MenuItem>
                ))}
              </BasicSelect>

              {/* 전공 */}
              <BasicSelect
                name="department"
                label="전공"
                value={formData.department}
                onChange={handleInputChange}
              >
                {Object.values(UserDepartment).map((d) => (
                  <MenuItem key={d} value={d}>
                    {UserDepartmentLabel[d]}
                  </MenuItem>
                ))}
              </BasicSelect>

              {/* 교수 선택 */}
              <BasicSelect
                name="professor"
                label="지도 교수"
                value={formData.professor}
                onChange={handleInputChange}
              >
                {professors.map((p) => (
                  <MenuItem key={p.id} value={p.name}>
                    {p.name}
                  </MenuItem>
                ))}
              </BasicSelect>
            </Box>
            <FullWidthButton title={"회원가입"} onClick={_doSignUp} />
          </>
        )}
      </Box>
    </div>
  );
};

export default SignUpPage;
