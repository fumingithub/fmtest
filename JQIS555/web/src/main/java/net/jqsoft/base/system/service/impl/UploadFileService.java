package net.jqsoft.base.system.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.security.SpringSecurityUtils;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import net.jqsoft.base.system.dao.UploadFileDao;
import net.jqsoft.base.system.domain.UploadFile;
import net.jqsoft.base.system.service.IUploadFileService;
import net.jqsoft.common.util.CommonUtil;

/**
 * Created by liuj on 2015/5/12.
 */
@Service
public class UploadFileService extends BaseEntityService<UploadFile> implements IUploadFileService {

	
    @Autowired
    private UploadFileDao uploadFileDao;

	@Override
	protected IEntityDao<UploadFile> getDao() {
		return uploadFileDao;
	}
	
	/**
	 * @author Taodd
	 * 
	 * @desc 根据md5查询文件是否存在
	 */
	public UploadFile getFileByMd5(String md5){
		String sqlId ="UploadFile.selectByMd5";
		List<UploadFile> fileList = uploadFileDao.find(sqlId, md5);
		if(!CommonUtil.isNullOrEmpty(fileList)){
			return fileList.get(0);
		}
		return null;
	}
    
	/**
	 * 保存文件add by liuj 2015/5/13
	 * @param fileName
	 * @param filePath
	 * @return 文件记录id
	 * @throws Exception
	 */
	public String saveAttach(String fileName,String filePath,String md5,Long length) throws Exception {
		UploadFile uploadFile = new UploadFile();
		try {
			uploadFile.setId(CommonUtil.getUUID());
			uploadFile.setFileName(fileName);
			uploadFile.setFilePath(filePath);
			uploadFile.setMd5(md5);
			uploadFile.setFileSize(length);
			this.uploadFileDao.create(uploadFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return uploadFile.getId();
	}
	
	/**
	 * 访问admin页面
	 * @param 
	 * @return String
	 */
	public void deleteTempDate() {
		String editorName = SpringSecurityUtils.getCurrentUser().getUsername();
		uploadFileDao.update(getEntityName() + ".deletePersonTempDate", editorName);
		uploadFileDao.update(getEntityName() + ".deleteFamilyTempDate", editorName);
		uploadFileDao.update(getEntityName() + ".deleteMemberTempDate", editorName);
		uploadFileDao.update(getEntityName() + ".deleteHomeItemTemp", editorName);
	}

	public void saveApplyAndAttach(final StringBuffer fileIds,
			final String configCode, final String applyId,final String batchNo) {
		if (fileIds !=null && !fileIds.toString().equals("")) {
			this.uploadFileDao.getSqlMapClientTemplate().execute(
					new SqlMapClientCallback<Object>() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							executor.startBatch();
							String[] fileIdArr = fileIds.toString().split(",");
							for(int i=0;i<fileIdArr.length;i++){
								Map<String,String> paramMap = new HashMap<String,String>();
								paramMap.put("id", CommonUtil.getUUID());
								paramMap.put("fileId", fileIdArr[i]);
								paramMap.put("configCode", configCode);
								paramMap.put("applyId", applyId);
								paramMap.put("batchNo", batchNo);
								executor.insert(getEntityName()+".insertApplyAndBatch", paramMap);
							}
							executor.executeBatch();
							return null;
						}
					});
		}
	}

	public void deleteFile(String fileId) {
		// 删除附件表中记录
		this.uploadFileDao.removeById(fileId);
		// 删除附件与业务关系表中记录
		this.uploadFileDao.remove(
				getEntityName() + ".removeApplyAndAttachById", fileId);
	}
	
	/**
	 * 查看附件信息
	 * @author zuoc
	 * @param applyId
	*/
	public List<Map> queryFileInfo(String applyId) {
		return uploadFileDao.find(getEntityName() + ".queryFileInfo", applyId);
	}
	
	/**
	 * 根据批次号查看附件信息
	 * @author liuj 2015-8-7
	 * @param batchNo
	 */
	public List<UploadFile> queryFileByBatchNo(String batchNo) {
		return uploadFileDao.find(getEntityName() + ".queryFileByBatchNo", batchNo);
	}

	/**
	 * 根据批次号查询该身份证下所有的附件信息
	 * @author lidp 2015-08-27
	 * @param batchNo
	 */
	public List<UploadFile> queryInfoByBatchNo(String batchNo) {
		return uploadFileDao.find(getEntityName() + ".queryInfoByBatchNo", batchNo);
	}

	public List<UploadFile> queryInfoByCardNo(String cardNo) {
		return uploadFileDao.find(getEntityName() + ".queryInfoByCardNo", cardNo);
	}

	public UploadFile queryFileType(String fileId) {
		List<UploadFile> fileList = uploadFileDao.find(getEntityName() + ".queryFileType", fileId);
		if(!CommonUtil.isNullOrEmpty(fileList)){
			return fileList.get(0);
		}
		return null;
	}

	public void saveAttach(String fileId, String configCode, String applyId,
			String batchNo) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("id", CommonUtil.getUUID());
		paramMap.put("fileId", fileId);
		paramMap.put("configCode", configCode);
		paramMap.put("applyId", applyId);
		paramMap.put("batchNo", batchNo);
		uploadFileDao.insert(getEntityName() + ".insertApplyAndBatch", paramMap);
		
	}

	public void saveSysFile(String fileIdNew, UploadFile uploadFile) {
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("id", fileIdNew);
		paramMap.put("fileName", uploadFile.getFileName());
		paramMap.put("filePath", uploadFile.getFilePath());
		paramMap.put("md5", uploadFile.getMd5());
		paramMap.put("fileSize", uploadFile.getFileSize().toString());
		uploadFileDao.insert(getEntityName() + ".insertSysFile", paramMap);
		
	}
}
