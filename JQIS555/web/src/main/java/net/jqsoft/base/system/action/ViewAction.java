package net.jqsoft.base.system.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.BaseAction;

import net.jqsoft.base.system.domain.UploadFile;
import net.jqsoft.base.system.service.IUploadFileService;
import net.jqsoft.common.util.FileConverterToSWF;

 /**
 * @author: yinzh
 * @date: 2015-5-27
 * @desc: 图片或者pdf文件预览action
 */
public class ViewAction extends BaseAction {
 
	private static final long serialVersionUID = 4410493488841650458L;

	@Autowired
	private IUploadFileService uploadFileService;
	
	public String view(){
		//获取文件id
		String fileId =  this.getHttpServletRequest().getParameter("fileId");
		String filePath = "";
		if(fileId != null && !fileId.equals("")){
			UploadFile file = this.uploadFileService.get(fileId);
			if (file != null && StringUtils.isNotEmpty(file.getFileName())) {
				String fileType = file.getFileName().substring(
						file.getFileName().indexOf("."));
				// 转换文件成swf格式
				if (fileType.contains("doc") || fileType.contains("txt") || fileType.contains("xls")) {
					filePath = FileConverterToSWF.getInstance().doc2pdf(
							file.getFilePath());
				} else {
					filePath = file.getFilePath();
				}
				FileConverterToSWF.getInstance().pdfOrJpg2swf(filePath);
			}
		}
		String swfPath="";
		if(StringUtils.isNotBlank(filePath))
			swfPath = filePath.substring(0, filePath.lastIndexOf("."))+".swf";
		this.getHttpServletRequest().setAttribute("swfPath", swfPath);
		//默认不允许打印
		this.getHttpServletRequest().setAttribute("printCount", 0);
		return "view";
	}


	
}
