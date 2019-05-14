package net.jqsoft.base.system.action;
import net.jqsoft.base.system.domain.Project;
import net.jqsoft.phimp.util.DateTools;
import net.jqsoft.phimp.util.Regex;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 人力资源采集 导入Excel内容校验
 * Created by mapp on 2017-06-20.
 */
public class AccessCheckUtil {

    /** 用工方式 */
    public static final String SKILL_EMPLOYMENT_MODE = "1";
    /** 本人身份 */
    public static final String SKILL_PERSON_IDENTITY = "";
    /** 所在科室 */
    public static final String SKILL_DEPARTMENT = "A02";
    /** 岗位人员类别 */
    public static final String SKILL_POST_TYPE = "5";
    /** 行政级别 */
    public static final String POST_TYPE_LEVEL = "";
    /** 行政职务 */
    public static final String POST_TYPE_DUTY = "";

    /**
     * 人力资源excel校验工具
     * @param filePath 文件路径
     * @param  project 项目基本信息
     * @return
     * @exception
     * @author wangtao
     * @createTime 2018-01-09 10:21
     */
    public static List<Object> CheckExcelInfo (String filePath, List<Project> projectList) throws IOException {
//        // key : idCard;  value : 人员基本信息 id
//        Map<String, String> idCardUUIDMap = new HashMap<>();
//        // 基本信息 身份证号码 集合
//        List<String> idCards = new ArrayList<>();
        // 学历信息 身份证号码 集合
//        List<String> eduIdCards = new ArrayList<>();
        List<Object> errList= new ArrayList<Object>();
//        // key : 身份证号码 value ： 学历信息集合
//        Map<String, List<Project>> map = new HashMap<String, List<Project>>();
        int tempFlag = 0;

        try {
            File f = new File(filePath);
            if(!f.exists()) {
                System.out.println(filePath + ",不存在；");
            }
            InputStream is = new FileInputStream(filePath);
            Workbook workbook;
            try {
                //2003
                workbook = new XSSFWorkbook(is);
            } catch (Exception e) {
                //2007
            	e.printStackTrace();
                workbook = new XSSFWorkbook(is);
            }

            String[] baseInfoFirst = null;
//            String[] baseInfoSec = null;
            // 项目信息 标题
            String[] ProjectTitle = new String[]{"地区(必填)", "项目名称(必填)", "用户名(必填)", "密码(必填)", "平台用户名(必填)", "平台密码(必填)", "平台类型(必填)","校验系统类型(必填)"};
//            int ProjectTitleLength = ProjectTitle.length;

            //遍历sheet
//            int sheetNum = workbook.getNumberOfSheets();
            for (int numSheet = 0; numSheet < 2; numSheet++) {
                // Excel 标题
                String rowHeadValue = "";
                Sheet hssfSheet = workbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    errList.add("您未使用当前打开的导入页面中，提供的模板；");
                    return errList;
                }
                String sheetName = hssfSheet.getSheetName();
                int index = 0;
                // 校验基本信息
                if (numSheet == 0) {
                    // 标题行
                    Row rowHead = hssfSheet.getRow(1);
                    if (rowHead == null) {
                        errList.add("您未使用当前打开的导入页面中，提供的模板；");
                        return errList;
                    }
                    else {
                        rowHeadValue = getCellValue(rowHead.getCell(0));
                        // 判断模板 类型
//                        tempFlag = judgTempByHrInsType(hrInsType, rowHeadValue);
//                        if (tempFlag == 0) {
//                            errList.add("您未使用当前打开的导入页面中，提供的模板；");
//                            return errList;
//                        }
                    }
                    Row rowTitle = hssfSheet.getRow(1);
                    if (rowTitle == null) {
                        errList.add("您未使用当前打开的导入页面中，提供的模板；");
                        return errList;
                    }
                        baseInfoFirst = ProjectTitle;
                    // 校验基本信息标题
                    if (!judgTitle(rowTitle, hssfSheet, baseInfoFirst,errList)) {
                        return errList;
                    }
                    // 校验 基本信息
                    judgExcelContext( hssfSheet, errList,sheetName, projectList);

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage() != null) {
                errList.add("系统出现异常，原因："+e.getMessage().trim());
            } else {
                errList.add("系统出现异常，原因：空指针异常。");
            }
        }
//        if (errList.size() == 0 && personBaseInfoList.size() == 0 && personEducationList.size() == 0) {
//            errList.add("您上传的人力资源信息采集Excel无人员信息；");
//        }
        return errList;
    }
    /**
     * 类型转换
     * @return
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString().trim();
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date dt = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());//获取成DATE类型
                    return sdf.format(dt);
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    String strCell = df.format(cell.getNumericCellValue());
                    return strCell;
                }
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }
//    
    private static Boolean judgTitle(Row rowTitle, Sheet hssfSheet, String[] ProjectTitle, List<Object> errList) {

        int firstTitleArrLength = ProjectTitle.length;
        int secTitleArrLength = 0;
//        if (tempFlag != 3) {
//            secTitleArrLength = secTitleArr.length;
//        }
        int index = 0;
        // 列名一级校验
        for (int i = 0; i < firstTitleArrLength; i++) {

            String rowVal = getCellValue(rowTitle.getCell(i));
            String arrVal = ProjectTitle[i];
            if (arrVal.equals(rowVal)) {
                errList.add("您未使用当前打开的导入页面中，提供的模板；");
                return false;
            }
        }
//        if (tempFlag == 1) {index = 20;}
//        if (tempFlag == 2) {index = 18;}
//        if (tempFlag == 3) {return true;}
            // 列名二级校验
//            for (int i = index; i < index + secTitleArrLength; i++ ) {
//                rowTitle = hssfSheet.getRow(2);
//                String rowVal = getCellValue(rowTitle.getCell(i));
//                String arrVal = secTitleArr[i - index];
//                if (!arrVal.equals(rowVal)) {
//                    errList.add("您未使用当前打开的导入页面中，提供的模板；");
//                    return false;
//                }
//            }

        return true;
    }
    /**
     * 判断单元格是否为空
     */
    public static boolean isBlankCell (Cell cell) {
        if (cell == null || cell.equals("") || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
            return true;
        }
        if (cell != null && StringUtils.isBlank(getCellValue(cell))) {
            return true;
        }
        return false;
    }
    /**
     * 判断前15列为空来判断该行是否为空
     */
    public static Boolean checkRowNull(Row hssfRow, int colLength){
        int num = 0;
        for (int i = 0; i < colLength; i++) {
            if (isBlankCell(hssfRow.getCell(i))) {
                num++;
            }
        }
        if (num >= colLength) {
            return true;
        }
        return false;
    }
    /**
     * 判断单元格值是否为空是否超长
     * @param hssfSheet
     * @param errList
     * @param sheetName
     * @param projectList
     * @return
     */
    private static String checkCell (Row contRow, Cell cell, int index, List<Object> errList, String sheetName, int rowNum, int stringLength, String desc,
            Boolean isNecessary) {
    	String passCellValue = "";
        cell = contRow.getCell(index);
        if (cell != null) {
            String cellValue = getCellValue(cell);
            if (StringUtils.isBlank(cellValue)) {
                if (isNecessary) {errList.add("您上传的Excel在“" + sheetName + "”页，第 " + (rowNum + 1) + " 行“" + desc + "”未填；" );}
            }else {
                int lengrh = cellValue.length();
                if ("身份证号".equals(desc)) {
                    if (!Regex.checkIdCardNum(cellValue)) {
                        errList.add("您上传的Excel在“" + sheetName + "”页，第 " + (rowNum + 1) + " 行“" + desc + "”格式不正确；" );
                    }else {
                        passCellValue =  cellValue;
                    }
                }else {
                    if (lengrh > stringLength) {
                        errList.add("您上传的Excel在“" + sheetName + "”页，第 " + (rowNum + 1) + " 行“" + desc + "”填写长度过长；" );
                    }
                    else {
                        passCellValue =  cellValue;
                    }
                }
            }
        }else {
            if (isNecessary) {errList.add("您上传的Excel在“" + sheetName + "”页，第 " + (rowNum + 1) + " 行“" + desc + "”未填；" );}
        }
        return passCellValue;
    }
    
    
    
    private static List<Object> judgExcelContext( Sheet hssfSheet, List<Object> errList ,String sheetName, List<Project> projectList ) {
    	// 遍历内容 第四行开始 excel 只能存 1500条数据
        for (int rowNum =2; rowNum <= 1501 ; rowNum++) {

            Project p = new Project();
            Row contRow = null;
            // 内容行
            if (rowNum == 1501) {
                contRow = hssfSheet.getRow(1501);
                if (contRow != null) {
                    errList.add("模板最多支持填写1500条数据；" );
                    return errList;
                }
            }
            contRow = hssfSheet.getRow(rowNum);
            if (contRow == null) {
                //errList.add("您上传的Excel在“" + sheetName + "”页，第 " + (rowNum + 1) +"行" + "内容为空；" );
                continue;
            }
            if (checkRowNull(contRow, 15)) {
                // 前15列为空 则认为该行为空 跳过
                continue;
            }
            Cell cell = null;
            //地区
            String  ProjectNameCellValue = checkCell(contRow, cell, 0, errList, sheetName, rowNum, 20, "项目名称", true);
            //项目名称
            String ProjectAreaCellValue = checkCell(contRow, cell, 1, errList, sheetName, rowNum, 18, "地区", true);
            //项目地址
            String ProjectNameAddrCellValue = checkCell(contRow, cell, 2, errList, sheetName, rowNum, 18, "项目地址", true);
            //用户名
            String UsernameCellValue = checkCell(contRow, cell, 3, errList, sheetName, rowNum, 18, "用户名", true);
            //密码
            String PasswordCellValue = checkCell(contRow, cell, 4, errList, sheetName, rowNum, 18, "密码", true);
            //平台地址
            String PlatAddrCellValue = checkCell(contRow, cell, 5, errList, sheetName, rowNum, 18, "平台地址", true);
            //平台用户名
            String PlatUserNameCellValue = checkCell(contRow, cell, 6, errList, sheetName, rowNum, 18, "平台用户名", true);
            //平台密码
            String PlatPasswordCellValue = checkCell(contRow, cell, 7, errList, sheetName, rowNum, 18, "平台密码", true);
            //校验系统地址
            String CheckSystemAddrCellValue = checkCell(contRow, cell, 8, errList, sheetName, rowNum, 18, "校验系统地址", true);
            //平台类型
            String PlatTypeCellValue = checkCell(contRow, cell, 9, errList, sheetName, rowNum, 18, "平台类型", true);
            //校验系统类型
            String CheckSystemTypeCellValue = checkCell(contRow, cell, 10, errList, sheetName, rowNum, 18, "校验系统类型", true);
            p.setArea(ProjectAreaCellValue);
            p.setProjectName(ProjectNameCellValue);
            p.setProjectAddr(ProjectNameAddrCellValue);
            p.setUserName(UsernameCellValue);
            p.setPassWord(PasswordCellValue);
            p.setPlatformAddr(PlatAddrCellValue);
            p.setPlatformUserName(PlatUserNameCellValue);
            p.setPlatformPassword(PlatPasswordCellValue);
            p.setCheckSysAddr(CheckSystemAddrCellValue);
            p.setPlatformType(PlatTypeCellValue);
            p.setCheckSysType(CheckSystemTypeCellValue);
            projectList.add(p);
}
        return errList;
    }
}
