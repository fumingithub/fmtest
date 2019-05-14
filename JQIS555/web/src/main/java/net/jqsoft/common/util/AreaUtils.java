package net.jqsoft.common.util;

/**
 * 
 * 区域编码工具
 * @author wangjie
 *
 */
public class AreaUtils {
	/**
	 * 获取编码级别
	 * @param code 区域编码
	 * @return String 编码级别
	 */
	public static String getAreaLevel(String code){
		int length = 0;
		char flag = '0';
		for(int i=code.length()-1; i>=0;i--){
			char c = code.charAt(i);
			if(c!=flag){
				length = i+1;
				break;
			}
		}
		return length/2+"";
	}
	
	/**
	 * 获取编码的省编码
	 * 如果参数级别大于省  返回原编码
	 * @param code 区域编码
	 * @return String 省编码
	 */
	public static String getProvince(String code){
		String province = code;
		if(Integer.valueOf(getAreaLevel(code))>1){
			province = code.substring(0, 2);
			province += "0000";
		}
		return province;
	}
	/**
	 * 获取编码的市编码
	 * 如果参数级别大于市  返回原编码
	 * @param code 区域编码
	 * @return String 市编码
	 */
	public static String getCity(String code){
		String province = code;
		if(Integer.valueOf(getAreaLevel(code))>2){
			province = code.substring(0, 4);
			province += "00";
		}
		return province;
	}
	/**
	 * 获取编码的县编码
	 * 如果参数级别大于县  返回原编码
	 * @param code 区域编码
	 * @return String 县编码
	 */
	public static String getCounty(String code){
		String province = code;
		if(Integer.valueOf(getAreaLevel(code))>3){
			province = code.substring(0, 6);
		}
		return province;
	}
}