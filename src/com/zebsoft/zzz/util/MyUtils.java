package com.zebsoft.zzz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class MyUtils {

	/**
	 * 上传文件
	 * 
	 * @param savePath
	 *            文件的保存路径
	 * @param uploadFile
	 *            被上传的文件
	 * @return newFileName
	 */
	public static String upload(String uploadFileName, String savePath, File uploadFile) {
		String newFileName = getUUIDName(uploadFileName, savePath);
		try {
			FileOutputStream fos = new FileOutputStream(savePath + newFileName);
			FileInputStream fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFileName;
	}

	public static String getUUIDName(String fileName, String dir) {
		String[] split = fileName.split("\\.");
		String extendFile = "." + split[split.length - 1].toLowerCase();
		return java.util.UUID.randomUUID().toString() + extendFile;
	}

	/**
	 * 根据路径创建一系列的目录
	 * 
	 * @param path
	 */
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			throw e;
		} finally {
			file = null;
		}
		return false;
	}

	/**
	 * 导出EXCEL 表格
	 */
	public static String putExcel(String rootPath, String filePath, String sheetName, List<Object[]> list) {
		int rowSize = 0;
		// 打开HSSFWorkbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 工作表
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(rootPath + filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HSSFSheet sheet = null;
		if (sheetName == null || sheetName.equals("")) {
			sheet = workbook.createSheet("Sheet1");
		} else {
			sheet = workbook.createSheet(sheetName);
		}
		HSSFRow row = sheet.createRow(0);
		int g = 0;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			row = sheet.createRow(i);
			for (int m = 0; m < obj.length; m++) {
				HSSFCell cell = row.createCell(g);
				g++;
				if (obj[m] != null)
					cell.setCellValue(obj[m].toString());
			}
			g = 0;
		}
		try {
			workbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}
}
