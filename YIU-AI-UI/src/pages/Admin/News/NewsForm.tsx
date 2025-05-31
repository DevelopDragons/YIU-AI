/** @jsxImportSource @emotion/react */
import React, { useState } from "react";
import {
  Box,
  Button,
  Card,
  CardContent,
  Grid,
  TextField,
  Typography,
  InputLabel,
} from "@mui/material";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { styled } from "@mui/material/styles";
// import dynamic from "next/dynamic";
// // Quill을 dynamic import로 SSR 방지
// const ReactQuill = dynamic(() => import("react-quill"), { ssr: false });
import "react-quill/dist/quill.snow.css";

const UploadInput = styled("input")({
  display: "none",
});

const Section = styled(Box)`
  margin-bottom: 24px;
`;

const NewsForm: React.FC = () => {
  const [title, setTitle] = useState("");
  const [shorts, setShorts] = useState("");
  const [contents, setContents] = useState("");
  const [thumbnail, setThumbnail] = useState<File[]>([]);
  const [gallery, setGallery] = useState<File[]>([]);
  const [files, setFiles] = useState<File[]>([]);

  const handleFileChange =
    (setter: React.Dispatch<React.SetStateAction<File[]>>) =>
    (event: React.ChangeEvent<HTMLInputElement>) => {
      if (event.target.files) {
        setter(Array.from(event.target.files));
      }
    };

  const handleSubmit = () => {
    // 폼 데이터 처리 로직
    console.log({ title, shorts, contents, thumbnail, gallery, files });
  };

  return (
    <Card sx={{ maxWidth: 900, margin: "0 auto", p: 2 }}>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          공지사항 작성
        </Typography>

        <Section>
          <TextField
            fullWidth
            label="제목"
            variant="outlined"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </Section>

        <Section>
          <TextField
            fullWidth
            label="요약 문구"
            variant="outlined"
            value={shorts}
            onChange={(e) => setShorts(e.target.value)}
          />
        </Section>

        <Section>
          <InputLabel sx={{ mb: 1 }}>내용</InputLabel>
          <ReactQuill value={contents} onChange={setContents} />
        </Section>

        <Section>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={4}>
              <InputLabel htmlFor="thumbnail-upload">썸네일</InputLabel>
              <UploadInput
                id="thumbnail-upload"
                type="file"
                accept="image/*"
                onChange={handleFileChange(setThumbnail)}
              />
              <label htmlFor="thumbnail-upload">
                <Button variant="outlined" component="span" fullWidth>
                  업로드
                </Button>
              </label>
              {thumbnail.length > 0 && (
                <Typography variant="body2" sx={{ mt: 1 }}>
                  {thumbnail[0].name}
                </Typography>
              )}
            </Grid>

            <Grid item xs={12} sm={4}>
              <InputLabel htmlFor="gallery-upload">갤러리</InputLabel>
              <UploadInput
                id="gallery-upload"
                type="file"
                accept="image/*"
                multiple
                onChange={handleFileChange(setGallery)}
              />
              <label htmlFor="gallery-upload">
                <Button variant="outlined" component="span" fullWidth>
                  다중 업로드
                </Button>
              </label>
              {gallery.length > 0 && (
                <Typography variant="body2" sx={{ mt: 1 }}>
                  {gallery.length}개 파일 선택됨
                </Typography>
              )}
            </Grid>

            <Grid item xs={12} sm={4}>
              <InputLabel htmlFor="file-upload">첨부파일</InputLabel>
              <UploadInput
                id="file-upload"
                type="file"
                multiple
                onChange={handleFileChange(setFiles)}
              />
              <label htmlFor="file-upload">
                <Button variant="outlined" component="span" fullWidth>
                  파일 업로드
                </Button>
              </label>
              {files.length > 0 && (
                <Typography variant="body2" sx={{ mt: 1 }}>
                  {files.length}개 파일 선택됨
                </Typography>
              )}
            </Grid>
          </Grid>
        </Section>

        <Box textAlign="right">
          <Button variant="contained" color="primary" onClick={handleSubmit}>
            등록하기
          </Button>
        </Box>
      </CardContent>
    </Card>
  );
};

export default NewsForm;
