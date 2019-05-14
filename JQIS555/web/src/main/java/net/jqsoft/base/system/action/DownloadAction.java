package net.jqsoft.base.system.action;

import org.zcframework.core.view.support.BaseAction;

import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.common.util.FileUtil;

/**
 * 下载
 * @author liuj 2015-8-6
 */
public class DownloadAction extends BaseAction {
	
	private static final long serialVersionUID = -7522091074240031217L;
	private static final String FILE_PATH_ROOT = CommonUtil.getProperties("config.properties", "file_path_root");
	private String filePath;
	
	/**
	 * 下载报表预览插件pdfreader
	 * @throws Exception 
	 */
	public void downPDFReader() throws Exception {
		String dir = getHttpServletRequest().getSession().getServletContext().getRealPath("/")+"/report/plugins/readerdc_cn_d_install.exe";
		FileUtil.downLoad(dir, this.getHttpServletResonse(), false);
	}

	/**
	 * 下载上传文件
	 * @throws Exception 
	 */
	public void downFile() throws Exception {
		FileUtil.downLoad(FILE_PATH_ROOT+filePath, this.getHttpServletResonse(), false);
	}
}