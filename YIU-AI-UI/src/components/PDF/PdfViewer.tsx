import React, { useState, useEffect } from "react";

interface PDFViewerProps {
  id: number;
}

const PDFViewer = ({ id }: PDFViewerProps): React.ReactElement => {
  const [pdfUrl, setPdfUrl] = useState<string>();

  useEffect(() => {
    fetch(`${process.env.REACT_APP_URL}/files/show?id=${id}`)
      .then((res) => res.blob())
      .then((blob) => {
        const url = URL.createObjectURL(blob);
        setPdfUrl(url);
      });
  }, []);

  return (
    <embed type="application/pdf" src={pdfUrl} width={"100%"} height={1000} />
  );
};

export default PDFViewer;
