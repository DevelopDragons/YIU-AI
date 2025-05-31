import React, { useState, useEffect } from "react";
import pdf from "../../assets/comments_v2.pdf";

interface PDFViewerProps {
  pdf: string;
}

const PDFViewer = ({ pdf }: PDFViewerProps): React.ReactElement => {
  return (
    <embed type="application/pdf" src={pdf} width={"100%"} height={1000} />
  );
};

export default PDFViewer;
