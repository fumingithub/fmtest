package net.jqsoft.base.system.service;

import java.util.List;
import java.util.Map;

import net.jqsoft.base.system.domain.UploadFile;

import org.zcframework.core.service.IBaseEntityService;

public interface IUploadFileService extends IBaseEntityService<UploadFile> {
	
	/**
	 * 保存附件add by liuj 2015/5/13
	 * @param fileName
	 * @param filePath
	 * @return 文件记录id
	 * @throws Exception
	 */
	public String saveAttach(String fileName,String filePath,String md5,Long length) throws Exception;
	
	/**
	 * @author Taodd
	 * 
	 * @desc 根据md5查询文件是否存在
	 */
	public UploadFile getFileByMd5(String md5);
	
	/**
	 * 删除临时表数据
	 * @param 
	 * @return String
	 */
	public void deleteTempDate();

	/**
	 * 保存附件跟业务的关系
	 * @author yinzh
	 * @param
	*/
	public void saveApplyAndAttach(StringBuffer fileIds, String configCode,
			String applyId,String batchNo);

	/**
	 * 删除附件和附件与业务关系表的记录
	 * @author yinzh
	 * @param
	*/
	public void deleteFile(String fileId);
	
	/**
	 * 查看附件信息
	 * @author zuoc
	 * @param applyId
	*/
	public List<Map> queryFileInfo(String applyId);
	
	/**
	 * 根据批次号查看附件信息
	 * @author liuj 2015-8-7
	 * @param batchNo
	 */
	public List<UploadFile> queryFileByBatchNo(String batchNo);
	
	/**
	 * 根据批次号查询该身份证下所有的附件信息
	 * @author lidp 2015-08-27
	 * @param batchNo
	 */
	public List<UploadFile> queryInfoByBatchNo(String batchNo);
	
	/**
	 * 根据身份证号查询该身份证下所有的附件信息
	 * @author lidp 2015-08-27
	 * @param cardNo
	 */
	public List<UploadFile> queryInfoByCardNo(String cardNo);
	
	/**
	 * 根据fileId查询附件类型
	 */
	public UploadFile queryFileType(String fileId);
	
	/**
	 * 保存附件跟业务的关系
	 * @author yinzh
	 * @param
	*/
	public void saveAttach(String fileId, String configCode,
			String applyId,String batchNo);
	
	/**
	 * 保存附件表（SYS_FILE）
	 * @author lidp 2015-08-31
	 * @param
	*/
	public void saveSysFile(String fileIdNew, UploadFile uploadFile);

}
