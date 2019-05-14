package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.Attachment;
import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.UploadFile;
import net.jqsoft.base.system.service.IAttachmentService;
import net.jqsoft.base.system.service.IProjectService;
import net.jqsoft.base.system.service.IUploadFileService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.common.util.CurrentUserUtil;
import net.jqsoft.common.util.FileUtil;
import net.jqsoft.common.util.SystemConstants;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;
import org.zcframework.security.object.Loginer;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传
 * @author taodongdong 2015/5/12
 */
public class UploadAction extends MyEntityAction<UploadFile, IUploadFileService> {
    
    private static final long serialVersionUID = -7522091074240031217L;
    private static final Log log = LogFactory.getLog(UploadAction.class);
    private static final String FILEPATH_QC = "/qcdatabase";
    private static final String FILEPATH_HRHEAD = "/hr/head";
    private static final String FILEPATH_HREXCEL = "/hr/excel";
    @Value("${file_path_root}")
    private String FILE_PATH_ROOT;
//    private static final String FILE_PATH_ROOT = CommonUtil.getProperties("config.properties", "file_path_root");
    private static final String FILE_PATH_FIRST = CommonUtil.getProperties("config.properties", "file_path_first");
    private static final String FILE_PATH_XM = CommonUtil.getProperties("config.properties", "file_path_xm");
    private static final String FILE_PATH_YW = CommonUtil.getProperties("config.properties", "file_path_yw");

    @Autowired
    private IUploadFileService uploadFileService;
    
    @Autowired
	private IProjectService projectService;
    
    @Autowired
    private IAttachmentService attachmentService;
    
    private List<Object> errList;
    
    /** 返回前台的数据 */
    private Map<String, Object> map;

    /** 文件集合的json格式 */
    private String fileJson;

    /** 上传的文件 */
    private File file;

    /** 文件名 */
    private String fname;

    /** 批量上传的文件 */
    private File[] files;

    /** 批量上传的文件名 */
    private String[] fnames;

    /** 上传的模块 */
    private Integer filePathType = 0;

    /** 未知 */
    private String type;

//    private List<Config> configList = new ArrayList<Config>();
    private List<UploadFile> fileList = new ArrayList<UploadFile>(); // 文件集合
    private List<UploadFile> fileInfoList = new ArrayList<UploadFile>(); // 身份信息
    private List<UploadFile> infoList = new ArrayList<UploadFile>(); // 文件信息集合
    private String batchNo;
    private String webAction;
    private String cardNo;
    private String jsonStr;

    @Override
    public String execute() {
        fileList = uploadFileService.queryFileByBatchNo(batchNo);
        return SUCCESS;
    }

    /**
     * 上传文件
     * @return 返回前台的数据
     * @throws Exception
     */
    public String uploadFile() throws Exception {
        map = new HashMap<String, Object>();
        map.put("flag", false); // 是否上传成功标识

        if (StringUtils.isBlank(fname)) {
            fname = this.getHttpServletRequest().getParameter("Filename");
            if(StringUtils.isBlank(fname)) {
                map.put("msg", "缺少参数fileName");
                return "json";
            }
        }

        if (null != file) {
            // 创建文件夹路径
            String relationPath = "",realPath = "";
//            if (filePathType == 99) {// 校验库结构维护
//                relationPath = FILEPATH_QC;
//            }
//            if (filePathType == 88) {// 人力资源头像
//                relationPath = FILEPATH_HRHEAD;
//                // 判断文件是否是图片
//                Image im = isPicture(file);
//                if(im != null) {
//                    // 是图片，判断像素   该功能暂时注释，不需要
//                    /*int pwidth = im.getWidth(null);
//                    int pheight = im.getHeight(null);
//                    String picrgb = Cache.getMapSysConfigValue().get("hr_head_rgb");
//                    if(!(pwidth+"*"+pheight).equals(picrgb)) {
//                        map.put("msg", "文件像素为：" + pwidth + "*" + pheight+"；要求像素为："+picrgb+"。");
//                        return "json";
//                    }*/
//                } else {
//                    map.put("msg", "文件：" + fname+"，不是有效图片。");
//                    return "json";
//                }
//            }
                relationPath = FILEPATH_HREXCEL;
            SimpleDateFormat yy = new SimpleDateFormat("yyyy");
            Integer year = Integer.parseInt(yy.format(new Date())); // 年
//            SimpleDateFormat mm = new SimpleDateFormat("MM");
//            Integer month = Integer.parseInt(mm.format(new Date())); // 月
//            String dstPath = relationPath + "/" + year + "/" + month; // 文件数据库路径
            String dstPath = relationPath + "/" + year; // 文件数据库路径
            realPath = FILE_PATH_ROOT + dstPath;//文件保存路径
            mdDir(realPath, 1); // 创建目录

            // 初始化变量
            fname = URLDecoder.decode(fname, "utf-8"); // 处理文件名中文乱码
            // 文件名
            String fileName = fname.substring(0, fname.lastIndexOf("."));
            String fileType = fname.substring(fname.lastIndexOf(".") + 1); // 文件类型
            String userId = CurrentUserUtil.getCurrentUser().getId().toString(); // 用户id
            String userName = CurrentUserUtil.getCurrentUser().getUsername(); // 用户名
            Date nowDate = UtilDateTime.nowDate(); // 当前时间
            String fileMd5 = FileUtil.getFileMD5(file);//计算文件md5
            
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String year2 = format.format(new Date());
            String newFileName ="";
              newFileName = FileUtil.getRandomFileName(12) + fileName + "." + fileType; // 服务器上保存的文件全名
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 格式化日期

            // 附件表对象-赋值
            Attachment attachment = new Attachment();
            attachment.setName(fileName); // 附件名称，加密前的原文件名
            attachment.setType(fileType); // 文件类型
            attachment.setFileSize((int) file.length()); // 文件大小
            attachment.setCreator(userId);
            attachment.setCreatorName(userName);
            attachment.setCreateTime(nowDate);
            attachment.setUpdator(userId);
            attachment.setUpdatorName(userName);
            attachment.setUpdateTime(nowDate);
            attachment.setId(CommonUtil.getUUID());
//            attachment.setRelateBizCode("HEALTH_CARD"); // 关连业务代码：HEALTH_CARD-健康卡信息
            attachment.setPath(dstPath + "/" + newFileName); // 文件路径
            attachment.setEncodeName(newFileName.substring(0, newFileName.lastIndexOf("."))); // 加密后密码
            attachment.setMd5(fileMd5);

            Attachment attachment1 = attachmentService.getFileByMd5(fileMd5);
            boolean isExist = false; // 判断文件是否存在
            if(null != attachment1){
                isExist = mdDir(FILE_PATH_ROOT+ attachment1.getPath(), 2);
            }
            if(null != attachment1 && isExist){ // 已有相同md5文件存在
                // 不上传附件至服务器，创建一条新附件记录，路径保持不变
                attachment.setPath(attachment1.getPath());
                attachmentService.create(attachment);
            } else { // 新上传文件
                // 文件上传
                File destination = new File(realPath + "/"+ newFileName);
                CommonUtil.uploadFile(file, destination);
                // 保存记录
                attachmentService.create(attachment);
            }
            // 为返回前台的数据进行赋值
            map.put("flag", true);
            map.put("attachmentId", attachment.getId());
            map.put("uploadTime", sdf.format(new Date()));
            map.put("fname", fname);
        } else {
            map.put("msg", "未检测到上传文件");
        }
        return "json";
    }
    
    /**
     * @wangtao
     * excel保存上传保存
     */
    public String saveExcelInfo() {
//		String orgId = getHttpServletRequest().getParameter("orgId");
		String excelName = getHttpServletRequest().getParameter("excelName");
//		Organization org = organizationService.get(orgId);
//		String hrInsType = getHttpServletRequest().getParameter("hrInsType");
		// 附件ID
		String attachmentId = getHttpServletRequest().getParameter("attachmentId");
		List<Project> projectList = new ArrayList<Project>();
		try {
			Attachment attachment = this.attachmentService.get(attachmentId);
			if (attachment != null) {
				String filePath = FILE_PATH_ROOT + attachment.getPath();
				errList = AccessCheckUtil.CheckExcelInfo(filePath, projectList);
				if(errList.size()==0){
					projectService.saveImpExlProject(projectList);
				}
			}
//			result = new Result(true, "接入方信息导入成功！", "", false);
		} catch (Exception e) {
			e.printStackTrace();
			errList.clear();
			errList.add("数据保存异常！");
		}
		return "errList";
	}
    /**
     * @author zuoc
     * @desc 重新加载附件
     */
    public String reloadFile(){
        String applyId = this.getHttpServletRequest().getParameter("applyId");
        List<Map> fileList = uploadFileService.queryFileInfo(applyId);
        if (fileList.size() > 0) {
            //fileJson = fileList.toString();
            //fileJson = fileJson.replaceAll("=", ":");
            JSONArray jsonArray = JSONArray.fromObject(fileList);
            fileJson = jsonArray.toString();
        }
        return "fileJson";
    }
    
    /**
     * @author Taodd
     * 
     * @desc 批量上传文件
     * flag--3数据库已存在上传文件，不可再上传
     */
    /*public String uploadFiles(){
        try {
        String applyId = this.getHttpServletRequest().getParameter("applyId");
        String batchNo = this.getHttpServletRequest().getParameter("batchNo");
        String configCode = this.getHttpServletRequest().getParameter("code");
        //查询上传附件类型名称
        Dict dict = this.dictService.queryDictByPcodeAndCode(configCode, SystemConstants.FILE_PCODE).get(0);
        String fileTypeName = dict.getName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat yy = new SimpleDateFormat("yyyy");
        SimpleDateFormat mm = new SimpleDateFormat("MM");
        Integer year = Integer.parseInt(yy.format(new Date()));// 年
        Integer moonth = Integer.parseInt(mm.format(new Date()));// 月
        // 创建文件夹路径
        String relationPath=FILE_PATH_FIRST;
        if(filePathType == 1) {// 救助一体化--方案--项目资料
            relationPath += FILE_PATH_XM;
        }else if (filePathType == 2) {//救助一体化 --救助业务申请
            relationPath += FILE_PATH_YW;
        }
        String dstPath = relationPath+"/"+year+"/"+moonth;//文件数据库路径
        String folderPath =FILE_PATH_ROOT+dstPath;//文件保存路径
        mdDir(folderPath,1);//创建目录
        map = new HashMap<String, Object>();
        if(filePathType == 0||null==fnames){
            String filename = this.getHttpServletRequest().getParameter("Filename");
            if(org.apache.commons.lang.StringUtils.isEmpty(filename)) {
                map.put("msg", "缺少参数filePathType或fileNames");
                return "json";
            }
            fnames = new String[]{filename};
            files = ((MultiPartRequestWrapper) this.getHttpServletRequest()).getFiles("file");
        }
        List<UploadFile> uploadFileList = uploadFileService.queryFileByBatchNo(batchNo);
        StringBuffer jsonInfo = new StringBuffer(); 
        StringBuffer fileIds = new StringBuffer(); 
        jsonInfo.append("[");
        if(null!=files){
            for (int i = 0; i < files.length; i++) {
                boolean fileExist = false;
                String fileMd5  = "";
                String filename=fnames[i];
                String fileType = filename.substring(filename.lastIndexOf(".")+1);
                //服务器上保存的文件全名
                String newName = FileUtil.getRandomFileName(12)+"."+fileType;
                fileMd5 = FileUtil.getFileMD5(files[i]);//计算文件md5
                UploadFile uploadFile = new UploadFile();

                uploadFile = uploadFileService.getFileByMd5(fileMd5);
                
                boolean re =false;
                if(null!=uploadFile){
                    re =mdDir(FILE_PATH_ROOT+uploadFile.getFilePath(), 2);//判断文件是否存在
                }
                if(null!=uploadFile&&re){//已有相同md5文件存在
                    uploadFile.setId(CommonUtil.getUUID());
                    uploadFile.setFileName(filename);
                    //文件名和md5相同时不上传
                    for(UploadFile file : uploadFileList){
                        if(file!=null&&file.getFileName().equals(filename)){
                            fileExist = true;
                            break;
                        }
                    }
                    if(!fileExist){
                        uploadFileService.create(uploadFile);
                        jsonInfo.append(",{\"fileId\":\""+uploadFile.getId()+"\",\"uploadTime\":\""+sdf.format(uploadFile.getCreateTime())+"\",\"typeName\":\""+fileTypeName+"\",");
                        jsonInfo.append("\"type\":\""+configCode+"\",\"fileName\":\""+filename+"\"}");
                        fileIds.append(","+uploadFile.getId());
                    }
                }else{//新上传文件
                    File destination = new File(folderPath+"/"+newName);
                    //文件上传
                    CommonUtil.uploadFile(files[i], destination);
                    // 保存记录
                    String fileId =uploadFileService.saveAttach(filename, dstPath+"/"+newName,fileMd5,files[i].length());
                    
                    jsonInfo.append(",{\"fileId\":\""+fileId+"\",\"uploadTime\":\""+sdf.format(new Date())+"\",\"typeName\":\""+fileTypeName+"\",");
                    jsonInfo.append("\"type\":\""+configCode+"\",\"fileName\":\""+filename+"\"}");
                    fileIds.append(","+fileId);
                }
            }
            map.put("flag", true);
            if(jsonInfo.length()>1 && !jsonInfo.toString().equals("[")){
                jsonInfo.deleteCharAt(1);
            }
            jsonInfo.append("]");
            if(org.apache.commons.lang.StringUtils.isNotBlank(fileIds.toString())){
                fileIds.deleteCharAt(0);
            }
            map.put("uploadTime", sdf.format(new Date()));
            map.put("type", configCode);
            map.put("typeName", fileTypeName);
            map.put("files", jsonInfo.toString());
            map.put("fileIds", fileIds.toString());
            map.put("configCode", configCode);
            map.put("batchNo", batchNo);
            if(StringUtils.isNotEmpty(fileIds)&& StringUtils.isNotEmpty(configCode)){
                this.uploadFileService.saveApplyAndAttach(fileIds, configCode, applyId,batchNo);
            }
        }else{
            map.put("flag", false);
            map.put("msg", "未检测到上传文件");
        }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", false);
        }
        return "json";
    }*/
    
    /**
     * @author Taodd
     * 
     * @desc type 1创建目录 2 判断文件是否存在
     */
    public Boolean mdDir(String path,Integer type){
        File dirPath =new File(path);
        if(!dirPath.exists()){
            if(type==1){//创建目录 
                dirPath.mkdirs();
            }else{//判断文件是否存在
                return false;
            }
        }
        return true;
    }

    /**
     * 删除附件
     * @author yinzh
     * @param
    */
    public void deleteFile(){
        String fileId = this.getHttpServletRequest().getParameter("fileId");
        boolean flag = true;
        try {
            if(StringUtils.isNotEmpty(fileId)){
                this.uploadFileService.deleteFile(fileId);
                this.getHttpServletResonse().getWriter().print(flag);
            }
        } catch (Exception e) {
            flag = false;
            log.info(e+"删除附件失败");
            try {
                this.getHttpServletResonse().getWriter().print(flag);
            } catch (IOException e1) {
                log.info(e1);
            }
        }
    }

    /**
     * 根据batchNo查询该批次号下所有的身份证号对应的附件信息
     * @author lidp
     */
    public String queryFile() {
        //configList = configService.queryAll();
        fileInfoList = uploadFileService.queryInfoByBatchNo(batchNo);
        return "queryResult";
    }
    
    /**
     * 根据batchNo查询该批次号下所有的身份证号对应的附件信息
     * @author lidp 2015-08-27
     */
    public String displayTabs() {
        cardNo = this.getHttpServletRequest().getParameter("cardNo");        
        infoList = uploadFileService.queryInfoByCardNo(cardNo);
        return "displaytabs";
    }
    
    /**
     * 保存检索添加的附件
     */
    public String saveFile() {
        String fileIds = this.getHttpServletRequest().getParameter("fileIds");
        String batchNo = this.getHttpServletRequest().getParameter("batchNo");
        //附件类型名
        String fileTypeName = "";
        List<UploadFile> files = uploadFileService.queryFileByBatchNo(batchNo);
        String fileStr = "";
        jsonStr =  "{fileStr:'null'}";
        Date createTime=new Date();
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
        if(fileIds.indexOf(",")!=-1){            
            String[] fileIdStrs = fileIds.split(",");
            for(String fileId:fileIdStrs){
                UploadFile uploadFile = uploadFileService.queryFileType(fileId);    
                boolean saveFlag = true;
                if(files!=null&&files.size()>0){
                    for(UploadFile file:files){
                    if(file.getFileCode().equals(uploadFile.getFileCode())&&file.getFileName().equals(uploadFile.getFileName())&&file.getMd5().equals(uploadFile.getMd5())){
                            saveFlag = false;
                            break;
                        }
                    }    
                }
                if(saveFlag){ //开始添加记录                
                    if(uploadFile!=null){        
                        String fileIdNew = CommonUtil.getUUID();
                        if(StringUtils.isNotEmpty(fileId)&& StringUtils.isNotEmpty(uploadFile.getFileCode())){
                            //先保存sysfile表                            
                            this.uploadFileService.saveSysFile(fileIdNew, uploadFile);
                            this.uploadFileService.saveAttach(fileIdNew, uploadFile.getFileCode(), "",batchNo);
                        }
                        //查询上传附件类型名称
//                        Dict dict = this.dictService.queryDictByPcodeAndCode(uploadFile.getFileCode(), SystemConstants.FILE_PCODE).get(0);
//                        fileTypeName = dict.getName();
//                        String upFile = fileIdNew+"^"+uploadFile.getFileCode()+"^"+uploadFile.getFileName()+"^"+time.format(createTime)+"^"+fileTypeName;
//                        fileStr = fileStr + "=" + upFile;
                    }
                }
            }
            fileStr = fileStr.substring(1, fileStr.length());
        }else{
            boolean saveFlag = true;
            UploadFile uploadFile = uploadFileService.queryFileType(fileIds);
            if(files!=null&&files.size()>0){
                for(UploadFile file:files){
                    if(file.getFileCode().equals(uploadFile.getFileCode())&&file.getFileName().equals(uploadFile.getFileName())&&file.getMd5().equals(uploadFile.getMd5())){
                        saveFlag = false;
                        break;
                    }
                }    
            }
            if(saveFlag){ //开始添加记录
                //UploadFile uploadFile = uploadFileService.queryFileType(fileIds);
                if(uploadFile!=null){
                    String fileIdNew = CommonUtil.getUUID();
                    if(StringUtils.isNotEmpty(fileIds)&& StringUtils.isNotEmpty(uploadFile.getFileCode())){
                        //先保存sysfile表                            
                        this.uploadFileService.saveSysFile(fileIdNew, uploadFile);
                        this.uploadFileService.saveAttach(fileIdNew, uploadFile.getFileCode(), "",batchNo);
                        //查询上传附件类型名称
//                        Dict dict = this.dictService.queryDictByPcodeAndCode(uploadFile.getFileCode(), SystemConstants.FILE_PCODE).get(0);
//                        fileTypeName = dict.getName();
//                        fileStr = fileIdNew+"^"+uploadFile.getFileCode()+"^"+uploadFile.getFileName()+"^"+time.format(createTime)+"^"+fileTypeName;
                    }
                }
            }            
        }
        if(fileStr!=null&&!"".equals(fileStr)){
            jsonStr = "{fileStr:'"+fileStr+"'}";
        }
    
        return "jsonStr";
    }
    
    /**
     * 跳转到查看附件页面
     * @author lidp
     */
    public String lookFile() {
        //configList = configService.queryAll();
        fileList = uploadFileService.queryFileByBatchNo(batchNo);
        return "lookFile";
    }
    
    @Override
    protected IUploadFileService getEntityManager() {
        return uploadFileService;
    }

    @Override
    public boolean needGenerateUUID() {
        return true;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFilePathType() {
        return filePathType;
    }

    public void setFilePathType(int filePathType) {
        this.filePathType = filePathType;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFilePathType(Integer filePathType) {
        this.filePathType = filePathType;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getFnames() {
        return fnames;
    }

    public void setFnames(String[] fnames) {
        this.fnames = fnames;
    }

    public String getFileJson() {
        return fileJson;
    }

    public void setFileJson(String fileJson) {
        this.fileJson = fileJson;
    }

//    public List<Config> getConfigList() {
//        return configList;
//    }
//
//    public void setConfigList(List<Config> configList) {
//        this.configList = configList;
//    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWebAction() {
        return webAction;
    }

    public void setWebAction(String webAction) {
        this.webAction = webAction;
    }

    public List<UploadFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFile> fileList) {
        this.fileList = fileList;
    }

    public List<UploadFile> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<UploadFile> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public List<UploadFile> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<UploadFile> infoList) {
        this.infoList = infoList;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}