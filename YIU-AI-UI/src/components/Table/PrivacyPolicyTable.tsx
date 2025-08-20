/** @jsxImportSource @emotion/react */
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { privacyPolicyData } from "../../assets/data/sign_up_condition";
import { colors } from "../../assets/styles/colors";

const PrivacyPolicyTable = () => {
  return (
    <TableContainer>
      <Table size="small" aria-label="privacy policy table">
        <TableHead>
          <TableRow>
            <TableCell sx={{ backgroundColor: colors.gray.lightGray }}>
              목적
            </TableCell>
            <TableCell sx={{ backgroundColor: colors.gray.lightGray }}>
              항목
            </TableCell>
            <TableCell sx={{ backgroundColor: colors.gray.lightGray }}>
              보유기간
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {privacyPolicyData.map((row, index) => (
            <TableRow key={index}>
              <TableCell>{row.purpose}</TableCell>
              <TableCell>{row.items}</TableCell>
              <TableCell>{row.retention}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default PrivacyPolicyTable;
