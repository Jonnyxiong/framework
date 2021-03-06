package com.jsmsframework.common.util;


import com.jsmsframework.common.dto.JxlExcel;
import com.jsmsframework.common.dto.R;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * excel文件工具类
 *
 */
public class JxlExcelUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JxlExcelUtils.class);

	/**
	 * 导出excel文件，文件已存在则覆盖
	 * 
	 * @param excel
	 *            excel文件信息
	 * @return
	 */
	public static boolean exportExcel(JxlExcel excel) {
		String filePath = excel.getFilePath();
		String title = excel.getTitle();
		List<String> remarkList = excel.getRemarkList();
		List<Map<String, String>> headerList = excel.getHeaderList();
		List<Map<String, Object>> dataList = excel.getDataList();
		int pageRowCount = excel.getPageRowCount();
		boolean showPage = excel.isShowPage();
		boolean showRownum = excel.isShowRownum();
		boolean showGridLines = excel.isShowGridLines();
		boolean showTitle = excel.isShowTitle();
		LOGGER.debug("【导出excel文件】开始：filePath={}", filePath);

		if (dataList == null || dataList.size() < 1) {
			LOGGER.debug("【导出excel文件】数据为空：filePath={}", filePath);
			return true;
		}
		if (showRownum) {
			Map<String, String> header = new HashMap<String, String>();
			header.put("name", "序号");
			header.put("width", "10");
			header.put("key", "rownum");
			headerList.add(0, header);

			int i = 1;
			for (Map<String, Object> data : dataList) {
				data.put("rownum", i++);
			}
		}

		makeDir(filePath);
		WritableWorkbook workbook = null;
		try {
			// 标题样式
			WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 24, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat titleFormat = new WritableCellFormat(NumberFormats.TEXT);
			titleFormat.setFont(titleFont);
			titleFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			titleFormat.setAlignment(Alignment.CENTRE);

			// 备注样式
			WritableFont remarkFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat remarkFormat = new WritableCellFormat(NumberFormats.TEXT);
			remarkFormat.setFont(remarkFont);
			remarkFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			// 表头样式
			WritableFont headerFont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat headerFormat = new WritableCellFormat(NumberFormats.TEXT);
			headerFormat.setFont(headerFont);
			headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			headerFormat.setAlignment(Alignment.CENTRE);
			headerFormat.setBackground(Colour.GRAY_25);

			// 正文样式
			WritableFont bodyFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat bodyFormat = new WritableCellFormat(NumberFormats.TEXT);
			bodyFormat.setFont(bodyFont);
			bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			bodyFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			workbook = Workbook.createWorkbook(new File(filePath));
			WritableSheet sheet = null;
			int totalCount = dataList.size();// 总行数
			int currentPage = 0; // 当前页号
			int totalPage = totalCount / pageRowCount + (totalCount % pageRowCount == 0 ? 0 : 1);// 总页数

			int pageNode = 0;// 当前分页节点
			int rownum = 0;// 当前行数
			int columnCount = headerList.size();// 总列数
			for (int i = 0; i < totalCount; i++) {
				if (i >= pageNode) {// 分页
					rownum = 0;
					currentPage++;
					pageNode = pageRowCount * currentPage;
					sheet = workbook.createSheet("第" + currentPage + "页", currentPage - 1);
					
					if(showGridLines){
						sheet.getSettings().setShowGridLines(false); // 去掉整个sheet中的网格线
					}
					
					if(showTitle){
						sheet.setRowView(0, 700);
						sheet.mergeCells(0, 0, columnCount - 1, 0);// 合并单元格
						sheet.addCell(new Label(0, rownum++, title, titleFormat));// 标题
					}

					for (String remark : remarkList) {
						sheet.mergeCells(0, rownum, columnCount - 1, rownum);
						sheet.addCell(new Label(0, rownum++, remark, remarkFormat));// 备注
					}
					
					if (showPage) {
						sheet.mergeCells(0, rownum, columnCount - 1, rownum);
						sheet.addCell(new Label(0, rownum++, "共" + totalCount + "条纪录，每页显示" + pageRowCount + "条，当前"
								+ currentPage + "/" + totalPage + "页", remarkFormat));
					}

					for (int j = 0; j < columnCount; j++) {
						sheet.setColumnView(j, Integer.parseInt(headerList.get(j).get("width")));
						sheet.addCell(new Label(j, rownum, headerList.get(j).get("name"), headerFormat));// 表头
					}
					rownum++;
				}

				Map<String, Object> data = dataList.get(i);
				for (int j = 0; j < columnCount; j++) {
					sheet.addCell(new Label(j, rownum, Objects.toString(data.get(headerList.get(j).get("key")), null),
							bodyFormat));// 正文
				}
				rownum++;
			}
			workbook.write();
			LOGGER.debug("【导出excel文件】成功：filePath=" + filePath);
			return true;
		} catch (Throwable e) {
			LOGGER.error("【导出excel文件】失败：filePath=" + filePath, e);
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (Throwable e) {
				LOGGER.error("【导出excel文件】关闭失败：filePath=" + filePath, e);
			}
		}
		return false;
	}


	/**
	 * 导入excel文件
	 * 
	 * @param filePath
	 *            导入的文件路径
	 * @return
	 */
	public static List<List<String>> importExcel(String filePath , boolean isFilterBlankRow) throws Exception {
		LOGGER.debug("【导入excel文件】开始：filePath={}", filePath);
		List<List<String>> dataList = new ArrayList<List<String>>();
		File file = new File(filePath);
		if (!file.isFile()) {
			LOGGER.error("【导入excel文件】文件不存在：filePath={}", filePath);
			return dataList;
		}

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			int rowCount = sheet.getRows();// 行数
			for (int i = 0; i < rowCount; i++) {
				List<String> data = new ArrayList<String>();
				for (Cell cell : sheet.getRow(i)) {
					data.add(cell.getContents());
				}
				dataList.add(data);
			}
		} catch (Throwable e) {
			LOGGER.error("【导入excel文件】失败：filePath=" + filePath, e);
			throw e;
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (Throwable e) {
				LOGGER.error("【导入excel文件】关闭失败：filePath=" + filePath, e);
			}
		}

		if(isFilterBlankRow){
			dataList = filterBlankRow(dataList);

		}
		LOGGER.debug("【导入excel文件】结束：dataList.size()={}, filePath={}", dataList.size(), filePath);
		return dataList;
	}

	/**
	 * 创建路径
	 * 
	 * @param path
	 */
	private static void makeDir(String path) {
		int last = path.lastIndexOf("/");
		if (last > 0) {
			File file = new File(path.substring(0, last));
			if (!file.isDirectory()) {
				file.mkdirs();
			}
		}
	}


	/**
	 * 校验Excel格式
	 * @param uploadFile
	 * @param uploadContentType
	 * @return
	 */
	@Deprecated
	public static String validateExcel(File uploadFile, String uploadContentType) {
		if (StringUtils.isBlank(uploadContentType)) {
			return "请先选择导入Excel";
		}

		if (uploadFile.length() > 2097152L) {
			return "您选择的文件大于2M,请将excel拆分后重新上传";
		}

//		if (!("application/vnd.ms-excel".equalsIgnoreCase(uploadContentType))) {
//			return "导入文件格式错误，目前支持.xls格式(97-2003)，请使用模板";
//		}
		return null;
	}


	/**
	 * 过滤Excel中的空白行
	 * @param list
	 * @return
	 */
	private static List<List<String>> filterBlankRow(List<List<String>> list) {

		if(list != null && list.size() > 0){
			List<List<String>> tempList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				List<String> row = list.get(i);
				boolean isNotEmpty = false;
				// 过滤掉excel中的空白行，一行数据中所有单元格为空则判断该行为空白行
				for (String cell : row) {
					if (StringUtils.isNotBlank(cell)) {
						isNotEmpty = true;
					}
				}
				if (isNotEmpty) {
					tempList.add(row);
				}
			}
			list = tempList;
		}
		return list;
	}



	public static void main(String[] args) {
//		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("key1", "数据11");
//		data.put("key2", "数据12");
//		data.put("key3", "数据13");
//		dataList.add(data);
//		data = new HashMap<String, Object>();
//		data.put("key1", "数据21");
//		data.put("key2", "数据22");
//		data.put("key3", "数据23");
//		dataList.add(data);
//
//		JxlExcel excel = new JxlExcel();
//		excel.setFilePath("E:/home/导出excel文件.xls");
//		excel.setTitle("标题");
//		excel.addRemark("备注1");
//		excel.addRemark("备注2");
//		excel.addRemark("备注3");
//		excel.addHeader(10, "表头1", "key1");
//		excel.addHeader(20, "表头2", "key2");
//		excel.addHeader(30, "表头3", "key3");
//		excel.addHeader(40, "表头4", "key4");
//		excel.setDataList(dataList);
//		boolean result = JxlExcelUtils.exportExcel(excel);
//		LOGGER.debug("导出结果：result={}", result);

//			String filePath = "D://test2.xls";
//			System.out.println(importExcel(filePath));
		// List<List<String>> importDataList =
		// JxlExcelUtils.importExcel("E:/home/导出excel文件.xls");
		// LOGGER.debug("导入结果：importDataList={}", importDataList);
	}

}
