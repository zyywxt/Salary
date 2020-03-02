package zyy.wxt.upload.service;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import zyy.wxt.upload.domain.salary;
import org.apache.poi.ss.usermodel.CellType;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {
    public List getBankListByExcel(InputStream in, String fileName) throws Exception {
        List list = new ArrayList<salary>();
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum() - 1; j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                salary s = new salary();
                for (int y = row.getFirstCellNum(); y <= row.getLastCellNum(); y++) {
                    int month = (int) row.getCell(0).getNumericCellValue();
                    double income = row.getCell(1).getNumericCellValue();
                    double insurance = row.getCell(2).getNumericCellValue();
                    double Attach = row.getCell(3).getNumericCellValue();
                    s.setMonth(month);
                    s.setIncome(income);
                    s.setInsurance(insurance);
                    s.setAttach(Attach);
                }

                list.add(s);
            }

        }
        work.close();
        return list;

    }

    /**
     * 判断文件格式
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }
}
