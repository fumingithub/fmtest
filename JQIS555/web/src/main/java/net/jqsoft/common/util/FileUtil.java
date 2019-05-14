/**
 * @author taodd 
 */
package net.jqsoft.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * @author Taodd
 *
 *@desc 文件操作类
 */
public class FileUtil {
	
	/**
	    * 获取单个文件的MD5值！
	    * @param file
	    * @return
	    */
	   public static String getFileMD5(File file) {
	     if (!file.isFile()){
	       return null;
	     }
	     MessageDigest digest = null;
	     FileInputStream in=null;
	     byte buffer[] = new byte[1024];
	     int len;
	     try {
	       digest = MessageDigest.getInstance("MD5");
	       in = new FileInputStream(file);
	       while ((len = in.read(buffer, 0, 1024)) != -1) {
	         digest.update(buffer, 0, len);
	       }
	       in.close();
	     } catch (Exception e) {
	       e.printStackTrace();
	       return null;
	     }
	     BigInteger bigInt = new BigInteger(1, digest.digest());
	     return bigInt.toString(16);
	   }
	   
	   
	   /***************************************************************************
	    * 返回一个定长的随机字符串(只包含大小写字母、数字)
	    *
	    * @param length
	    * 随机字符串长度
	    * @return 随机字符串
	    * @return
	    */
	   public static String getRandomFileName(int length) {
	    String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    StringBuffer sb = new StringBuffer();
	    Random random = new Random();
	    for (int i = 0; i < length; i++) {
	     sb.append(allChar.charAt(random.nextInt(allChar.length())));
	    }
	    return sb.toString();
	   }
	   
	   /**
		 * <b>方法名</b>：writeZip<br>
		 * @author <font color='blue'>haowg</font>
		 * @date 2014-6-19 下午12:01:53
		 * @param filePath 压缩文件所在目录
		 * @param encoding 编码
		 * @param Savepath 文件解压后的目录
		 * @param allFilePath 文件解压后文件所在目录 （参数可为NULL）
		 */
		//public static void writeZip(File file, String encoding, String Savepath, List<File> picFiles, List<String> picFilesName) {
		public static void writeZip(File file, String encoding, String Savepath) {
			int count = -1;
			// 输入流
			InputStream inputStream = null;
			// 输出流
			OutputStream fileOutputStream = null;
			// 输出缓冲流
			BufferedOutputStream bufferedOutputStream = null;
			//File file = new File(filePath);
			ZipFile zipFile = null;
			try {
				// 文件保存目录
				if (!file.exists()) {
					file.mkdir();
				}
				// 解决中文乱码问题
				zipFile = new ZipFile(file, encoding);
				Enumeration<?> entries = zipFile.getEntries();
				while (entries.hasMoreElements()) {
					byte buf[] = new byte[1024];
					ZipEntry entry = (ZipEntry) entries.nextElement();
					String filename = entry.getName();
					boolean ismkdir = false;
					// 检查此文件是否带有文件夹
					if (filename.lastIndexOf("/") != -1) {
						ismkdir = true;
//						if (allFilePath != null) {
//							allFilePath.add(filename.substring(0, filename.lastIndexOf("/") + 1));
//						}
					}
					filename = Savepath + filename;
					// 如果是文件夹先创建
					if (entry.isDirectory()) {
						file = new File(filename);
						file.mkdirs();
						continue;
					}
					file = new File(filename);
					if (!file.exists()) {
						// 如果是目录先创建
						if (ismkdir) {
							// 目录先创建
							new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs();
						}
					}
					// 创建文件
					file.createNewFile();
					inputStream = zipFile.getInputStream(entry);
					fileOutputStream = new FileOutputStream(file);
					bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
					while ((count = inputStream.read(buf)) > -1) {
						bufferedOutputStream.write(buf, 0, count);
					}
					// 刷新缓冲输出流
					bufferedOutputStream.flush();
					// 关闭缓冲输出流
					bufferedOutputStream.close();
					// 关闭文件输出流
					fileOutputStream.close();
					// 关闭输入流
					inputStream.close();
				}
				// 关闭文件解压
				zipFile.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				// 关闭输入、输出流
				closeBufferedOut(bufferedOutputStream);
				closeOutputStream(fileOutputStream);
				closeInputStream(inputStream);
				closeZipFile(zipFile);
			}
		}
		
		/**
		 * 
		 * @methodName:closeInputStream
		 * @decription: 关闭输入流
		 * @param inputStream
		 * @auth {haowg@pingtech.com.cn}
		 * @date 2014-6-4
		 */
		private static void closeInputStream(InputStream inputStream) {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 
		 * <b>方法名</b>：closeBufferedOut<br>
		 * <b>功能</b>：关闭输出缓冲流<br>
		 * 
		 * @author <font color='blue'>haowg</font>
		 * @date 2014-6-11 下午04:16:32
		 * @param stream
		 */
		private static void closeBufferedOut(BufferedOutputStream stream) {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 
		 * <b>方法名</b>：closeZipFile<br>
		 * <b>功能</b>：关闭压缩文件<br>
		 * 
		 * @author <font color='blue'>haowg</font>
		 * @date 2014-6-11 下午04:15:53
		 * @param zipFile
		 */
		private static void closeZipFile(ZipFile zipFile) {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 
		 * <b>方法名</b>：closeOutputStream<br>
		 * <b>功能</b>关闭输出流<br>
		 * 
		 * @author <font color='blue'>haowg</font>
		 * @date 2014-6-11 下午04:15:28
		 * @param outputStream
		 */
		private static void closeOutputStream(OutputStream outputStream) {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 下载生成的文件到本地
		 * @param filePath 文件路径
		 * @param response
		 * @param isDel true:下载完后删除源文件
		 */
		public static void downLoad(String filePath,HttpServletResponse response,boolean isDel) {
			File sourcePath = new File(filePath); // 源文件路径
			try {
				String filename = sourcePath.getName();
				InputStream fis = new BufferedInputStream(new FileInputStream(
						sourcePath));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();
				// 设置response的Header
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes("utf-8"), "ISO-8859-1"));
				response.addHeader("Content-Length", "" + sourcePath.length());
				OutputStream toClient = new BufferedOutputStream(response
						.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
				if(isDel)
					sourcePath.delete();  //下载完毕删除原word文件
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
