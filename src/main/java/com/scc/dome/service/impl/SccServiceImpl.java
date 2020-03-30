package com.scc.dome.service.impl;

import com.scc.dome.dao.SccDao;
import com.scc.dome.service.SccService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class SccServiceImpl implements SccService {

    @Autowired
    private SccDao sccDao;

    @Override
    public void uploadZip(MultipartFile multipartFile) {
        try {
            File file = File.createTempFile("prefix_", multipartFile.getOriginalFilename());

            System.out.println(file.getPath());

//            multipartFile.transferTo(file);//multipartFile 转 file  方法1
            FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());//multipartFile 转 file   方法2

//            if (!file.exists()) return;
            ZipFile zipFile = new ZipFile(file);

//            解决使用Spring Boot、Multipartfile上传文件路径错误问题
/*// 前端传入mulFileSource
// 创建压缩前源文件
            File fileSourcePath = new File("tmp/source/");
            File fileSource = new File(fileSourcePath, mulFileSource.getOriginalFilename());
            if (!fileSourcePath.exists()) {
                fileSourcePath.mkdirs();
            }
// 将接收得图片暂存到临时文件中
            mulFileSource.transferTo(fileSource);*/

            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements(); ) {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();//文件名、
                System.out.println(name);
//                InputStream inputStream = zipFile.getInputStream(zipEntry);
//                this.readExcel(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ttt(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            readExcel(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void test() {
        Map test = sccDao.test();
        System.out.println(test);
    }

    private void readExcel(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            XSSFRow sheetRow = sheet.getRow(0);
            XSSFCell cell = sheetRow.getCell(1);
            System.out.println(cell == null);
          /*  for (int i = 1; i < rows; i++) {
                XSSFRow sheetRow = sheet.getRow(i);
                XSSFCell cell = sheetRow.getCell(0);
                System.out.println(cell==null);
//                XSSFCell cel2 = sheetRow.getCell(1);

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
