export const quillStyles = {
  "& .ql-container": {
    border: "1px solid #ccc",
    minHeight: "200px",
  },
  "& .ql-editor": {
    minHeight: "200px",
    // padding: "12px 15px",
    fontSize: "16px",
    lineHeight: "1.6",

    "& img, & video": {
      width: "auto !important",
      height: "auto !important",
      maxWidth: "100% !important",
      objectFit: "contain",
      display: "block",
      margin: "0 auto",
    },
    "& iframe": {
      display: "block",
      margin: "0 auto",
    },
  },
};
