/** @jsxImportSource @emotion/react */
import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  Grid,
  TextField,
  Typography,
  InputLabel,
  Avatar,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";
import ReactQuill from "react-quill";
import Quill from "quill";
import ImageResize from "quill-image-resize-module-react";
import "react-quill/dist/quill.snow.css";
import { styled } from "@mui/material/styles";
import { css } from "@emotion/react";
import { authFileAPI } from "../../../services";
import { quillStyles } from "../../../assets/styles/common";
import SmallButton from "../../../components/Button/SmallButton";
import SectionTitle from "../../../components/Text/SectionTitle";
import { border1 } from "../../../assets/styles/borderLine";
import { useNavigate } from "react-router-dom";
import { FORM_MAX_WIDTH } from "../../../global";
import { handleError } from "../../../services/ErrorHandling";
import { ApiType } from "../../../services/type";
import { useRecoilValue } from "recoil";
import { selectedNewsState } from "../../../recoil/news";
import { colors } from "../../../assets/styles/colors";

Quill.register("modules/imageResize", ImageResize);

const modules = {
  toolbar: [
    [{ header: [1, 2, 3, 4, 5, false] }],
    [{ font: [] }],
    [{ size: ["small", false, "large", "huge"] }],
    ["bold", "italic", "underline", "strike"],
    [{ color: [] }, { background: [] }],
    [{ list: "ordered" }, { list: "bullet" }],
    [{ indent: "-1" }, { indent: "+1" }],
    [{ align: [] }],
    ["link", "video", "code-block"],
    ["clean"],
  ],
  imageResize: {
    modules: ["Resize", "DisplaySize", "Toolbar"],
    displayStyles: { backgroundColor: "black", border: "none", color: "white" },
    handleStyles: { backgroundColor: "red" },
  },
};

const formats = [
  "header",
  "font",
  "size",
  "bold",
  "italic",
  "underline",
  "strike",
  "color",
  "background",
  "list",
  "bullet",
  "indent",
  "align",
  "link",
  "video",
  "code-block",
];

const UploadInput = styled("input")({ display: "none" });

const formSectionTitle = css`
  font-weight: 700;
  font-size: 1.25rem;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
`;

const StyledPaper = styled(Card)`
  border-radius: 10px;
  box-shadow: none;
`;

const StyledCardContent = styled(CardContent)`
  padding: 20px;
  &:last-child {
    padding-bottom: 20px;
  }
`;

const StyledLabel = styled(InputLabel)`
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
`;

const NewsForm: React.FC = () => {
  const navigate = useNavigate();
  const selectedNews = useRecoilValue(selectedNewsState);
  const isEdit = !!selectedNews;

  const [title, setTitle] = useState("");
  const [shorts, setShorts] = useState("");
  const [contents, setContents] = useState("");

  // 파일 상태
  const [thumbnailFiles, setThumbnailFiles] = useState<any[]>([]);
  const [galleryFiles, setGalleryFiles] = useState<any[]>([]);
  const [fileFiles, setFileFiles] = useState<any[]>([]);

  // 삭제된 파일 ID
  const [deletedThumbnailIds, setDeletedThumbnailIds] = useState<number[]>([]);
  const [deletedGalleryIds, setDeletedGalleryIds] = useState<number[]>([]);
  const [deletedFileIds, setDeletedFileIds] = useState<number[]>([]);

  // 삭제 모달 상태
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [deleteTarget, setDeleteTarget] = useState<{
    type: "file" | "news";
    index?: number;
    fileType?: "thumbnail" | "gallery" | "file";
  } | null>(null);

  // 수정 모드 초기화
  useEffect(() => {
    const convertToFile = async (fileObj: any): Promise<File> => {
      const response = await fetch(
        `${process.env.REACT_APP_URL}/files/show?id=${fileObj.id}`
      );
      const blob = await response.blob();
      return new File([blob], fileObj.originName, { type: blob.type });
    };

    const initFiles = async () => {
      if (selectedNews) {
        setTitle(selectedNews.title);
        setShorts(selectedNews.shorts);
        setContents(selectedNews.contents);

        if (selectedNews.thumbnails) {
          const thumbs = await Promise.all(
            selectedNews.thumbnails.map((f) => convertToFile(f))
          );
          setThumbnailFiles(thumbs);
        }

        if (selectedNews.gallery) {
          const gallery = await Promise.all(
            selectedNews.gallery.map((f) => convertToFile(f))
          );
          setGalleryFiles(gallery);
        }

        if (selectedNews.file) {
          const files = await Promise.all(
            selectedNews.file.map((f) => convertToFile(f))
          );
          setFileFiles(files);
        }
      }
    };

    initFiles();
  }, [selectedNews]);

  const getFilePreview = (file: any) =>
    file instanceof File
      ? URL.createObjectURL(file)
      : `${process.env.REACT_APP_URL}/files/show?id=${file.id}`;

  // 파일 삭제 클릭
  const handleFileDeleteClick = (
    type: "thumbnail" | "gallery" | "file",
    index: number
  ) => {
    setDeleteTarget({ type: "file", index, fileType: type });
    setIsDeleteModalOpen(true); // 모달 즉시 열기
  };

  // 뉴스 삭제 클릭
  const handleNewsDeleteClick = () => {
    setDeleteTarget({ type: "news" });
    setIsDeleteModalOpen(true); // 모달 즉시 열기
  };

  // 삭제 confirm
  const confirmDelete = async () => {
    if (!deleteTarget) return;

    setIsDeleteModalOpen(false); // 모달 먼저 닫기

    if (deleteTarget.type === "file") {
      const { index, fileType } = deleteTarget;
      if (fileType !== undefined && index !== undefined) {
        if (fileType === "thumbnail") {
          const file = thumbnailFiles[index];
          if (file.id) setDeletedThumbnailIds((prev) => [...prev, file.id]);
          setThumbnailFiles((prev) => prev.filter((_, i) => i !== index));
        }
        if (fileType === "gallery") {
          const file = galleryFiles[index];
          if (file.id) setDeletedGalleryIds((prev) => [...prev, file.id]);
          setGalleryFiles((prev) => prev.filter((_, i) => i !== index));
        }
        if (fileType === "file") {
          const file = fileFiles[index];
          if (file.id) setDeletedFileIds((prev) => [...prev, file.id]);
          setFileFiles((prev) => prev.filter((_, i) => i !== index));
        }
      }
    } else if (deleteTarget.type === "news" && selectedNews) {
      try {
        await authFileAPI.delete(`/news/admin?id=${selectedNews.id}`);
        alert("뉴스가 삭제되었습니다.");
        navigate(-1);
      } catch (err) {
        handleError(ApiType.NEWS, err);
      }
    }

    setDeleteTarget(null); // target 초기화
  };

  // 모달 닫기
  const cancelDelete = () => setIsDeleteModalOpen(false);

  const handleSave = async () => {
    try {
      const formData = new FormData();
      formData.append("title", title);
      formData.append("shorts", shorts);
      formData.append("contents", contents);

      // 새 파일만 FormData에 추가
      thumbnailFiles.forEach((file) => {
        if (file instanceof File) formData.append("thumbnail", file);
      });
      galleryFiles.forEach((file) => {
        if (file instanceof File) formData.append("gallery", file);
      });
      fileFiles.forEach((file) => {
        if (file instanceof File) formData.append("file", file);
      });

      // 삭제된 파일 ID 전송
      formData.append(
        "deletedThumbnailIds",
        JSON.stringify(deletedThumbnailIds)
      );
      formData.append("deletedGalleryIds", JSON.stringify(deletedGalleryIds));
      formData.append("deletedFileIds", JSON.stringify(deletedFileIds));

      if (isEdit && selectedNews) {
        await authFileAPI.put(`/news/admin?id=${selectedNews.id}`, formData);
        alert("뉴스가 수정되었습니다.");
      } else {
        await authFileAPI.post("/news/admin", formData);
        alert("뉴스가 등록되었습니다.");
      }
      navigate(-1);
    } catch (err) {
      handleError(ApiType.NEWS, err);
    }
  };

  return (
    <Box
      css={css({
        margin: "0 auto",
        padding: "50px 30px",
        mx: "auto",
        maxWidth: FORM_MAX_WIDTH,
      })}
    >
      {/* 헤더 */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 2,
        }}
      >
        <SectionTitle title={isEdit ? "뉴스 수정" : "뉴스 등록"} />
        <Box sx={{ display: "flex", gap: 1 }}>
          {isEdit && (
            <SmallButton
              title="삭제"
              onClick={handleNewsDeleteClick}
              colored
              color={colors.basic.error}
              hoverColor={colors.basic.darkError}
            />
          )}
          <SmallButton
            title="취소"
            onClick={() => navigate(-1)}
            colored={false}
            bordered
          />
          <SmallButton
            title={isEdit ? "수정" : "등록"}
            onClick={handleSave}
            colored
          />
        </Box>
      </Box>

      {/* 기본정보 */}
      <StyledPaper>
        <StyledCardContent>
          <Typography css={formSectionTitle}>기본정보</Typography>
          <TextField
            fullWidth
            label="제목"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            sx={{ mb: 2 }}
          />
          <TextField
            fullWidth
            label="요약 문구"
            multiline
            rows={2}
            value={shorts}
            onChange={(e) => setShorts(e.target.value)}
            sx={{ mb: 2 }}
          />
          <Box sx={quillStyles} mb={2}>
            <ReactQuill
              value={contents}
              onChange={setContents}
              modules={modules}
              formats={formats}
              theme="snow"
            />
          </Box>
        </StyledCardContent>
      </StyledPaper>

      {/* 파일 업로드 */}
      <StyledPaper sx={{ mt: 3 }}>
        <StyledCardContent>
          <Typography css={formSectionTitle}>파일</Typography>

          {/* 썸네일 */}
          <Box mb={2}>
            <StyledLabel>썸네일</StyledLabel>
            <label htmlFor="thumbnail-upload">
              <UploadInput
                id="thumbnail-upload"
                type="file"
                accept="image/*"
                onChange={(e) =>
                  e.target.files?.[0] && setThumbnailFiles([e.target.files[0]])
                }
              />
              <Button
                variant="outlined"
                component="span"
                fullWidth
                sx={{ border: border1 }}
              >
                업로드
              </Button>
            </label>
            {thumbnailFiles.map((file, idx) => (
              <Box key={idx} mt={1}>
                <Avatar
                  src={getFilePreview(file)}
                  variant="rounded"
                  sx={{ width: 120, height: 90, cursor: "pointer" }}
                  onClick={() => handleFileDeleteClick("thumbnail", idx)}
                />
                <Typography variant="body2">
                  {file.name || file.originName}
                </Typography>
              </Box>
            ))}
          </Box>

          {/* 갤러리 */}
          <Box mb={2}>
            <StyledLabel>갤러리</StyledLabel>
            <label htmlFor="gallery-upload">
              <UploadInput
                id="gallery-upload"
                type="file"
                accept="image/*"
                multiple
                onChange={(e) => {
                  const files = e.target.files;
                  if (files)
                    setGalleryFiles((prev) => [...prev, ...Array.from(files)]);
                }}
              />
              <Button
                variant="outlined"
                component="span"
                fullWidth
                sx={{ border: border1 }}
              >
                다중 업로드
              </Button>
            </label>
            <Grid container spacing={1} mt={1}>
              {galleryFiles.map((file, idx) => (
                <Grid item key={idx}>
                  <Avatar
                    src={getFilePreview(file)}
                    variant="rounded"
                    sx={{ width: 80, height: 60, cursor: "pointer" }}
                    onClick={() => handleFileDeleteClick("gallery", idx)}
                  />
                </Grid>
              ))}
            </Grid>
          </Box>

          {/* 첨부파일 */}
          <Box>
            <StyledLabel>첨부파일</StyledLabel>
            <label htmlFor="file-upload">
              <UploadInput
                id="file-upload"
                type="file"
                multiple
                onChange={(e) => {
                  const files = e.target.files;
                  if (files)
                    setFileFiles((prev) => [...prev, ...Array.from(files)]);
                }}
              />
              <Button
                variant="outlined"
                component="span"
                fullWidth
                sx={{ border: border1 }}
              >
                파일 업로드
              </Button>
            </label>
            <Box mt={1}>
              {fileFiles.map((file, idx) => (
                <Typography
                  key={idx}
                  variant="body2"
                  sx={{ cursor: "pointer" }}
                  onClick={() => handleFileDeleteClick("file", idx)}
                >
                  📎 {file.name || file.originName}
                </Typography>
              ))}
            </Box>
          </Box>
        </StyledCardContent>
      </StyledPaper>

      {/* 삭제 확인 모달 */}
      <Dialog open={isDeleteModalOpen} onClose={cancelDelete}>
        <DialogTitle>삭제 확인</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {deleteTarget?.type === "file"
              ? "선택한 파일을 삭제하시겠습니까?"
              : "선택한 뉴스를 삭제하시겠습니까?"}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={cancelDelete}>취소</Button>
          <Button onClick={confirmDelete} color="error">
            삭제
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default NewsForm;
