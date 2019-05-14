package net.jqsoft.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonUtil {

	private static Log log = LogFactory.getLog(CommonUtil.class);

	
	private static final String CONTENT_TYPE_PLAIN = "text/plain;charset=UTF-8";
	private static final String CONTENT_TYPE_HTML  = "text/html;charset=UTF-8";
	private static final String CONTENT_TYPE_XML   = "text/xml;charset=UTF-8";
	
	
	/**
	 * 随即生成数字
	 * @param length
	 * @return
	 */
	public static String generateStringByNum(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random(System.currentTimeMillis());
		String charStr = "0123456789";
		for (int i = 0; i < length; i++) {
			sb.append(charStr.charAt(random.nextInt(charStr.length())));
		}
		return sb.toString();
	}

	/**
	 * 将输入流转化成字节数组
	 * @param
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static byte[] getBytesFromStream(InputStream is) throws IOException {
		byte[] data = null;

		Collection chunks = new ArrayList();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;

		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}
		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			}
			finally {
				if (is != null) {
					is.close();
				}
				if (bos != null) {
					bos.flush();
					bos.close();
				}
			}
		}
		return data;
	}

	public String getStringByBytes(byte[] bytes) {
		String str = new String(bytes);
		return str;
	}

	/**
	 * 接受请求并以流的形式返回给客户端
	 *
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void clientResponse(HttpServletResponse response, String content) throws IOException {
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			sos.write(content.getBytes("utf-8"));
		}
		finally {
			if (sos != null) {
				sos.flush();
				sos.close();
			}
		}
	}

	/**
	 * XML接受请求并以流的形式返回给客户端
	 *
	 * @param response
	 * @param inputStream
	 * @throws IOException
	 */
	public static void clientResponseByStream(HttpServletResponse response, InputStream inputStream) throws IOException {
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			sos.write(getBytesFromStream(inputStream));
		}
		finally {
			if(sos != null ){
				sos.flush();
				sos.close();
			}
		}
	}

	/**
	 * 从指定的url地址下载文件并存储到指定的本地路径中。
	 *
	 * @param fileurl
	 * @param savepath
	 * @return
	 */
	public static void downLoadFile(String fileurl, String savepath) throws IOException {

		int bytesum = 0;
		int byteread = 0;
		InputStream is = null;
		FileOutputStream fos = null;

		// 下载网络文件
		try {
			URL url = new URL(fileurl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			fos = new FileOutputStream(savepath);
			byte[] buffer = new byte[1024];
			while ((byteread = is.read(buffer)) != -1) {
				bytesum += byteread;
				fos.write(buffer, 0, byteread);
			}
		}
		finally {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}

	}

	/**
	 * 生成字母和数字混合的n位随机数
	 *
	 * @param length
	 * @return
	 */
	public static String generateString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random(System.currentTimeMillis());
		String charStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < length; i++) {
			sb.append(charStr.charAt(random.nextInt(charStr.length())));
		}
		return sb.toString();
	}

	/**
	 * 根据路径创建文件夹
	 *
	 * @param path
	 * @return
	 */
	public static String creatFolder(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return path;
	}

	/**
	 * 根据当前日期新建目录
	 *
	 * @param format
	 *        日期的格式
	 * @return 返回目录创建后的路径
	 */
	public static String createDateFolder(String root, String format) {
		if (format == null || "".equals(format)) {
			format = "yyyy-MM-dd";
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datePath = sdf.format(date);
		File filePath = new File(root + datePath);
		if (!filePath.exists()) {
//			filePath.setWritable(true, false);
//			filePath.setReadable(true, false);
			filePath.mkdir();
		}
		return datePath;
	}

	/**
	 * 循环创建文件夹
	 *
	 * @param rootPath
	 * @param folderPath
	 * @return
	 */
	public static String createFolder(String rootPath, String folderPath) {
		folderPath = folderPath.replaceAll("\\\\", "/");
		if (!folderPath.startsWith("/")) {
			folderPath = "/" + folderPath;
		}
		if (!folderPath.endsWith("/")) {
			folderPath = folderPath + "/";
		}
		String[] folders = folderPath.split("/");
		for (int i = 0; i < folders.length; i++) {
			rootPath = rootPath + "/" + folders[i];
			CommonUtil.creatFolder(rootPath);
		}
		return folderPath;
	}

	/**
	 * 复制文件
	 *
	 * @param source
	 *        源文件路径
	 * @param destination
	 *        目标文件路径
	 * @throws IOException
	 */
	public static void copyFile(String source, String destination) throws IOException {
		File sourceFile = null;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			sourceFile = new File(source);
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(destination);
			byte[] buffer = new byte[1024];
			int c;
			while ((c = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, c);
			}
		}
		finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}

	/**
	 * 上传文件
	 *
	 * @param source
	 *        源文件路径
	 * @param destination
	 *        目标文件路径
	 * @throws IOException
	 */
	public static void uploadFile(File source, File destination) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(source), 1024);
			out = new BufferedOutputStream(new FileOutputStream(destination), 1024);
			byte[] buffer = new byte[1024];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * 按字节截取字符串
	 *
	 * @param orignal
	 *        原始字符串
	 * @param subcount
	 *        截取位数
	 */
	public static String subStringByByte(String orignal, int subcount) {
		int reInt = 0;
		String reStr = "";
		if (orignal == null) {
			return "";
		}
		char[] tempChar = orignal.toCharArray();
		for (int kk = 0; (kk < tempChar.length && subcount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		return reStr;
	}

	/**
	 * 把字符串截成等长的分数 汉字占2字节，数字字母占一个字节
	 *
	 * @param orignal
	 * @param subcount
	 * @return
	 */
	public static List<String> equallyDividedString(String orignal, int subcount) {
		List<String> list = new ArrayList<String>();
		while (true) {
			String str = subStringByByte(orignal, subcount);
			orignal = orignal.replaceFirst(str, "");
			list.add(str);
			if ("".equals(orignal)) {
				break;
			}
		}
		return list;
	}

	/**
	 * 获得配置文件信息
	 *
	 * @param configFile
	 * @param key
	 * @return
	 */
	public static String getProperties(String configFile, String key) {
		InputStream inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(configFile);
		Properties p = new Properties();
		String value = null;
		try {
			p.load(inputStream);
			value = p.getProperty(key);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 获得配置文件信息
	 *
	 * @param configFile
	 * @param key
	 * @return
	 */
	public static String getYTKJProperties(String key) {
		String configFile = "yitukongjian.properties";
		InputStream inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(configFile);
		Properties p = new Properties();
		String value = null;
		try {
			p.load(inputStream);
			value = p.getProperty(key);
			value = new String(value.getBytes(("ISO8859-1")), "utf-8");
			if (!key.endsWith("Start") && !key.endsWith("PhotoPath")) {
				value += "--n";
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 将文件编码成base64格式
	 *
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static String encodeFile(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			byte[] bytes = getBytesFromStream(is);
			byte[] encoded = Base64.encodeBase64(bytes);
			String fileBase64 = new String(encoded, "utf-8");
			return fileBase64;
		}
		finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 从base64编码中获得文件
	 *
	 * @param encode
	 * @param destination
	 * @return
	 * @throws IOException
	 */
	public static File getFileFromFileCode(String encode, String destination) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			byte[] bytes = encode.getBytes();
			byte[] decoded = Base64.decodeBase64(bytes);
			is = getInputStreamFromBytes(decoded);
			os = new BufferedOutputStream(new FileOutputStream(destination), 1024);
			byte[] buffer = new byte[1024];
			int c;
			while ((c = is.read(buffer)) > 0) {
				os.write(buffer, 0, c);
			}
			File file = new File(destination);
			return file;
		}
		finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.flush();
				os.close();
			}
		}
	}

	/**
	 * 将字节数组转为流
	 *
	 * @param bytes
	 * @return
	 */
	public static InputStream getInputStreamFromBytes(byte[] bytes) throws IOException {
		InputStream is = null;
		try {
			if (bytes == null || bytes.length <= 0) {
				return is;
			}
			is = new ByteArrayInputStream(bytes);
		}
		finally {
			if (is != null) {
				is.close();
			}
		}
		return is;
	}

	/**
	 * 下载网络文件
	 *
	 * @param url
	 * @param destination
	 *        存放的地址
	 * @throws IOException
	 */
	public static void downloadFromNet(String url, String destination) throws IOException {
		int bytesum = 0;
		int byteread = 0;
		InputStream is = null;
		OutputStream os = null;
		int buffer = 1024;
		URL u = new URL(url);
		try {
			URLConnection conn = u.openConnection();
			is = conn.getInputStream();
			os = new BufferedOutputStream(new FileOutputStream(destination), buffer);
			byte[] bytes = new byte[buffer];
			while ((byteread = is.read(bytes)) > 0) {
				bytesum += byteread;
				os.write(bytes, 0, byteread);
			}
			log.debug(bytesum);
		}
		finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}

		}
	}


	/**
	 * 根据文件路径，得到文件名
	 *
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path) {
		path = path.replaceAll("\\\\", "/");
		String[] strs = path.split("/");
		int length = strs.length;
		if (length < 1) {
			return path;
		}
		return strs[length - 1];
	}

	/**
	 * 将内容写到文本文件里
	 *
	 * @param text
	 * @param path
	 * @throws IOException
	 */
	public static void writeTxt(String text, String fileName) throws IOException {
		final String mode = "rws";
		final String encoding = "utf-8";
		RandomAccessFile raf = null;
		File file=new File(fileName);
		try {
			if(!file.exists())
			{
				file.createNewFile();
			}
			raf = new RandomAccessFile(fileName, mode);
			raf.seek(raf.length());
			raf.write(text.getBytes(encoding));
		}
		finally {
			if (raf != null) {
				raf.close();
			}
		}
	}
	
	/**  
	 * 删除单个文件  
	 * @param   sPath    被删除文件的文件名  
	 * @return 单个文件删除成功返回true，否则返回false  
	 */  
	public static boolean deleteFile(String sPath) {   
	    boolean flag = false;   
	    File file = new File(sPath);   
	    // 路径为文件且不为空则进行删除   
	    if (file.isFile() && file.exists()) {   
	        file.delete();   
	        flag = true;   
	    }   
	    return flag;   
	}  
	/**
	 * 获得全球唯一ID
	 * @return UUID
	 */
	public static String getUUID()
	{
		java.util.UUID uuid=java.util.UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	public static void main(String args[]) {
		try {
			writeTxt("111\r\n", "a.txt");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getEntityFromJson 根据json数据数组解析返回所对应的实体对象集合
	 * @param <T>
	 * @param <T>
	 * @param jsonarray
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getEntityFromJson (Class clazz, JSONArray jsonarray) {
		List entityList = new ArrayList();
		if (jsonarray != null) {
			for(Iterator iter = jsonarray.iterator(); iter.hasNext();){ 
				JSONObject jsonObject = (JSONObject)iter.next(); 
				entityList.add(JSONObject.toBean(jsonObject, clazz)); 
				} 
		}
		return entityList;
	}

	/**
	 * 截取AreaCode
	 * @param Code
	 * @return
	 */
	public static String TrimAreaCode(String Code)
	{
		String NowCode="";
		if(!"".equals(Code)&&Code!=null)
		{
		if("000000".equals(Code)){
			NowCode="";
		}else{
			if(Code.length()==6){
				if("0000".equals(Code.substring(2, 6))){
					NowCode=Code.substring(0,2);
				}else if("00".equals(Code.substring(4,6))){
					NowCode=Code.substring(0,4);
				}else{
					NowCode=Code;
				}
			}else{//县级以下不做截取
				NowCode=Code;
			}
		}
		}else{
			NowCode="";
		}
		return NowCode;
	}
	
	public static boolean isNullOrEmpty(Object obj) {  
        if (StringUtils.isBlank(String.valueOf(obj)))  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    }
	
	/**
	 * 获得访问地ip
	 * @return
	 */
	public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    if (null != ip && ip.indexOf(',') != -1) {
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (null != ips[i] && !"unknown".equalsIgnoreCase(ips[i])) {
					ip = ips[i];
					break;
				}
			}
		}
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	
	}
	
	/**
	 * 获得本地ip
	 * @return
	 */
	public static String getLocalIP(){   
		InetAddress addr = null;   
        try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}   
        String ipAddrStr = "";   
		if(addr != null){
			byte[] ipAddr = addr.getAddress();   
			for (int i = 0; i < ipAddr.length; i++) {   
				if (i > 0) {   
					ipAddrStr += ".";   
				}   
				ipAddrStr += ipAddr[i] & 0xFF;   
			}   
		}      
//        System.out.println(ipAddrStr);   
        return ipAddrStr;   
        
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	/**
	 * 仅限新农合使用，获取当前用户所属地区a
	 * @param isCounty 是否获取当前用户所在县
	 */
	public static String getSysUserAreaId(boolean isCounty){
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		String areaId = loginer.getAreaCode();
		if(areaId == null){
			return null;
		}
		if (isCounty){
			areaId = areaId.substring(0,6);
		}
		return areaId;
	}

	/**
	 * @author Simba
	 * @date 2016/03/24
	 * @desc 获取当前系统时间
	 */
	public static Integer getSysTime(){
		Date date = new Date();
		return ((int)(date.getTime()/1000));
	}

	public static  String getUid(){
		return SpringSecurityUtils.getCurrentUserName();
	}
	
	/**
	 * 向客户端输出数据
	 * 
	 * @param isError
	 * @param contentType
	 * @param text
	 * @return
	 */
	private static String render(boolean isError, String contentType, String text) {
		HttpServletResponse response = null;
		PrintWriter printWriter = null;
		
		try {
			response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			if (isError) {
				response.setStatus(500);
			}

			printWriter = response.getWriter();
			printWriter.write(text);
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (printWriter != null) {
					printWriter.close();
					printWriter = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
		return null;
	}
	

	/**
	 * 对文本数据进行Html编码
	 * 
	 * @param text
	 * @return
	 */
	public static String HTMLEncode(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}
		
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '&') {
				result.append("&amp;");
			} 
//			else if (character == '\"') {
//				result.append("&quot;");
//			}
			else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
	
	
	public static String renderHTMLEncodedText(String text) {
		return renderText(CommonUtil.HTMLEncode(text));
	}
	
	public static String renderText(String text) {
		return CommonUtil.render(false, CONTENT_TYPE_PLAIN, text);
	}

	public static String renderHtml(String html) {
		return CommonUtil.render(false, CONTENT_TYPE_HTML, html);
	}

	public static String renderXML(String xml) {
		return CommonUtil.render(false, CONTENT_TYPE_XML, xml);
	}
	
	public static String renderHTMLEncodedErrText(String text) {
		return renderErrText(CommonUtil.HTMLEncode(text));
	}
	
	public static String renderErrText(String text) {
		return CommonUtil.render(true, CONTENT_TYPE_PLAIN, text);
	}

	public static String renderErrHtml(String html) {
		return CommonUtil.render(true, CONTENT_TYPE_HTML, html);
	}

	public static String renderErrXML(String xml) {
		return CommonUtil.render(true, CONTENT_TYPE_XML, xml);
	}
	
	
	/**
     * 获取web根目录
     * @author			xiaohf
     * @createDate		2017年1月6日下午2:18:19                                                                 
     * @return
     */
//	public static String getWebContentPath() {
//		URL url = CommonUtil.class.getResource("");
//		if (url != null) {
//			String path = url.toString();
//			int webInfIndex = path.indexOf("WEB-INF");
//			if (webInfIndex != -1) {
//				path = path.substring(path.indexOf("/")+1, webInfIndex);
//			}
//			try {
//				path = java.net.URLDecoder.decode(path, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			return path;
//		}
//		
//		return "";
//	}
	public static String getWebContentPath() {
		URL url = CommonUtil.class.getResource("");
		if (url != null) {
			String path = url.toString();
			int webInfIndex = path.indexOf("WEB-INF");
			if (webInfIndex != -1) {
				path = path.substring(path.indexOf("/")+1, webInfIndex);
			}
			try {
				path = java.net.URLDecoder.decode(path, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return path;
		}
		
		return "";
	}
	public static Timestamp getCurrentTime(){
		Timestamp time = new Timestamp(System.currentTimeMillis());
		return time;
	}
	/**
	 * 获取web根目录
	 * @param clazz : 类
	 * @author			xiaohf
	 * @createDate		2017年1月6日下午2:18:19
	 * @return
	 */
	public static String getWebContentPath(Class clazz) {
		URL url = clazz.getResource("");
		if (url != null) {
			String path = url.toString();
			int webInfIndex = path.indexOf("WEB-INF");
			if (webInfIndex != -1) {
				path = path.substring(path.indexOf("/")+1, webInfIndex);
			}
			try {
				path = java.net.URLDecoder.decode(path, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return path;
		}

		return "";
	}
}



