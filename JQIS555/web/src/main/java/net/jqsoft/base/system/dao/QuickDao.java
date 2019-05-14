/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import net.jqsoft.base.system.domain.Quick;

/**
 * @author sunwm@jqsoft.net
 * @datetime 2016-02-01 16:17
 * @desc
 * @see
 */
@Repository
public class QuickDao extends JdbcDaoSupport {

	public List<Quick> serachAreaDictAll(String input, String areaId, String dictCode) {
		String sql = "SELECT * FROM (SELECT CODE AS ID, CODE AS CODE_, NAME, MNEMONIC_CODE\n" + "  FROM AREA_DICT\n"
				+ " WHERE (CODE LIKE '%" + input + "%' OR NAME LIKE '%" + input + "%' OR MNEMONIC_CODE LIKE '%" + input
				+ "%')) WHERE ROWNUM < 31";

		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	/**
	 * 知识库select2插件 搜索使用
	 * 
	 * @author zh
	 */

	public List<Quick> RepoKind(String code) {

		String sql = " SELECT T.ID,T.CODE,T.NAME FROM SYS_REPOSITORY_KIND T WHERE ( T.NAME  LIKE '%' || '" + code
				+ "' || '%' OR  T.UPDATOR LIKE '%' || UPPER('" + code + "') || '%' " + "OR T.CODE LIKE '%' || '" + code
				+ "' || '%')  ";
		// String sql = " SELECT T.NAME,T.CODE FROM SYS_PROSITORY_KIND T WHERE
		// T.NAME = '" + name + "'";

		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	public List<Quick> searchRepoKind(String codeOrname) {
		String sql = " SELECT T.ID,T.CODE, T.NAME FROM SYS_REPOSITORY_KIND T WHERE ( T.NAME  LIKE '%' || '" + codeOrname
				+ "' || '%' OR  T.UPDATOR LIKE '%' || UPPER('" + codeOrname + "') || '%' " + "OR T.CODE LIKE '%' || '"
				+ codeOrname + "' || '%') ";
		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;

	}

	/**
	 * 字典select2 插件 搜索使用
	 *
	 * @param input
	 *            输入的值 即搜索条件
	 * @param areaId
	 *            地区
	 * @param dictCode
	 *            要搜索的字典编码
	 * @return
	 */
	public List<Quick> search(String input, String areaId, String dictCode) {
		String sql = "SELECT ITEM_CODE CODE,ITEM_CODE,NAME FROM NCMS_SYS_DICT_ITEM WHERE DICT_CODE = '" + dictCode
				+ "' " + "AND (AREA_ID = '" + areaId + "' OR AREA_ID = SUBSTR('" + areaId + "',0,2) || '0000') "
				+ "AND (UPPER(ITEM_CODE) LIKE '%' || UPPER('" + input + "') || '%' OR NAME LIKE '%' || UPPER('" + input
				+ "') || '%')";
		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	/**
	 * 字典select2 插件 回显使用
	 *
	 * @param itemCode
	 *            字典项编码
	 * @param areaId
	 *            地区
	 * @param dictCode
	 *            字典编码
	 * @return
	 */
	public List<Quick> searchByCode(String itemCode, String areaId, String dictCode) {
		String sql = "SELECT ITEM_CODE,NAME FROM NCMS_SYS_DICT_ITEM WHERE DICT_CODE = '" + dictCode + "' "
				+ "AND (AREA_ID = '" + areaId + "' OR AREA_ID = SUBSTR('" + areaId + "',0,2) || '0000') "
				+ "AND ITEM_CODE = '" + itemCode + "'";
		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	/**
	 * 多级字典 查询
	 *
	 * @param areaId
	 * @param dictCode
	 *            字典编码
	 * @return
	 */
	public List<Quick> searchDict2(String areaId, String dictCode) {
		String sql = "SELECT T.ITEM_CODE,T.ITEM_NAME,T.ID,T.PARENT_ID,LEVEL, "
				+ "(SELECT COUNT(*) FROM NCMS_SYS_DICT_MULTILEVEL S WHERE S.PARENT_ID=T.ID) CHILDCOUNT "
				+ "FROM NCMS_SYS_DICT_MULTILEVEL T " + "WHERE (T.AREA_ID = '" + areaId + "' OR T.AREA_ID = SUBSTR('"
				+ areaId + "',0,2) || '0000') " + "AND T.DICT_CODE = '" + dictCode + "' "
				+ "CONNECT BY  T.PARENT_ID=PRIOR ID  START WITH T.PARENT_ID IS NULL ORDER SIBLINGS BY T.ITEM_CODE";
		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper3());
		return quicks;
	}

	/**
	 * 查询组织机构
	 * 
	 * @param orgCode
	 * @param orgName
	 * @return
	 */
	public List<Quick> queryOrg(String input, String insId) {
		String sql = "SELECT  T.CODE AS ID, T.CODE AS CODE_, T.NAME FROM SYS_ORG T WHERE (LOWER(T.CODE) LIKE '%" + input
				+ "%' OR T.NAME LIKE '%" + input + "%' OR UPPER(T.CODE) LIKE '%" + input + "%')"
				+ " OR (LOWER(T.CODE) LIKE '%" + insId + "%' OR T.NAME LIKE '%" + insId + "%' OR UPPER(T.CODE) LIKE '%"
				+ insId + "%')";
		List<Quick> quicks = this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	/**
	 * 目录和医疗机构 搜索使用 将所有参数放入map中，并支持自定义 input: 入参，传入的值 name:编码名称的字段名 默认 NAME
	 * （用于查询条件） pinyin:拼音码的字段名称,传则过滤，不传则不过滤 tableName：表名
	 * columns：字段1,字段2,字段3,字段4,字段5 ... 存ID（ID,CODE,NAME[,PINYIN_CODE]）/
	 * 存CODE（CODE,CODE,NAME[,PINYIN_CODE]） 字段1是要存储的值，是隐藏的，后面的2到3个字段是展示出来的 areaId
	 * : 地区条件 type:其他过滤条件 status: 状态（启用禁用）疾病
	 *
	 * @param param
	 * @return
	 */
	/*
	 * eg.select id,CODE AS CODE_,NAME,PINYIN from ncms_ins_hospital t where
	 * t.flag=0 and (t.area_id=SUBSTR('100000',0,2) || '0000' --是省的但县级编码没有的 and
	 * t.code not in (select code from ncms_ins_hospital r where r.flag=0 and
	 * r.area_id=SUBSTR('100000',0,6)) or t.area_id=SUBSTR('100000',0,6) )--县级的
	 * and (T.NAME LIKE '%1%' OR T.CODE LIKE '%1%' OR UPPER(T.PINYIN) LIKE
	 * UPPER('%1%'))
	 */
	public List<Quick> searchFromMap(Map param) {
		String input = "" + param.get("input");
		String columns = "" + param.get("columns");
		String sql = "SELECT " + columns + " FROM " + param.get("tableName") + " WHERE FLAG=0";
		if (param.get("status") == null)
			param.put("status", "0"); // 默认查询的是启用的数据

		Boolean bool = param.get("isStatus") != null && !(Boolean) param.get("isStatus");
		if (!bool)
			sql += " AND STATUS=" + param.get("status");

		if (param.get("areaId") != null) {
			if (param.get("isSubArea") == null)
				param.put("isSubArea", "Y");

			if (param.get("isSubArea").equals("N"))
				sql += " AND AREA_ID = '" + param.get("areaId") + "'";
			if (param.get("isSubArea").equals("Y"))
				sql += " AND AREA_ID = SUBSTR('" + param.get("areaId") + "',0,6)";
		}

		if (param.get("currentYear") == null)
			param.put("currentYear", new SimpleDateFormat("yyyy").format(new Date()));

		sql += " AND CURRENT_YEAR = '" + param.get("currentYear") + "'";

		// if(param.get("areaId") != null)
		// sql+=" AND (AREA_ID=SUBSTR('"+param.get("areaId")+"',0,2) || '0000' "
		// +
		// "AND CODE NOT IN(SELECT CODE FROM "+param.get("tableName")+" WHERE
		// FLAG=0";
		//
		// if(!bool) sql+=" AND STATUS="+param.get("status");
		//
		// if(param.get("areaId") != null)
		// sql+=" AND AREA_ID = SUBSTR('"+param.get("areaId")+"',0,6))";

		// if(param.get("areaId") != null)
		// sql+=" or AREA_ID = SUBSTR('"+param.get("areaId")+"',0,6) )";

		if (param.get("name") == null)
			param.put("name", "NAME");
		if (param.get("code") == null)
			param.put("code", "CODE");

		sql += " AND (ID = '" + input + "' OR " + param.get("name") + " LIKE '" + "%" + input + "%' OR "
				+ param.get("code") + " LIKE '" + "%" + input + "%'";
		if (param.get("pinyin") != null)
			sql += " OR UPPER(" + param.get("pinyin") + ") LIKE UPPER('" + "%" + input + "%'))";
		else
			sql += ")";

		if (param.get("hospLevel") != null)// 药品诊疗根据医院用药级别过滤
			sql += " AND HOSPITAL_LEVEL_CODE LIKE '%" + param.get("hospLevel") + "%'";

		if (param.get("type") != null)// 其他过滤条件
			sql += getConditions("" + param.get("tableName"), "" + param.get("type"));

		if (param.get("rownum") != null && param.get("rownum").equals("all"))
			sql += " ";
		else
			sql += " AND ROWNUM<=20";

		if (param.get("pinyin") != null)
			sql += " ORDER BY LENGTH(" + param.get("pinyin") + ")";
		else
			sql += " ORDER BY LENGTH(PINYIN)";

		List<Quick> quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	/**
	 * 目录和医疗机构 回显使用 将所有参数放入map中，并支持自定义 input: 入参
	 *
	 * @param param
	 * @return
	 */
	public List<Quick> searchFromMapByCode(Map param) {
		List<Quick> quicks = new ArrayList<Quick>();
		String input = "" + param.get("input");
		String columns = "" + param.get("columns");
		if (param.get("currentYear") == null)
			param.put("currentYear", new SimpleDateFormat("yyyy").format(new Date()));

		String sql = "SELECT " + columns + " FROM " + param.get("tableName") + " WHERE ID = '" + input
				+ "' OR (CODE = '" + input + "' AND AREA_ID='" + param.get("areaId") + "' AND CURRENT_YEAR = '"
				+ param.get("currentYear") + "')";
		quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper());
		return quicks;
	}

	/**
	 * 条件过滤统一方法
	 *
	 * @param tableName
	 * @param type
	 * @return
	 */
	private String getConditions(String tableName, String type) {
		String condition = "";
		if (StringUtils.isNotEmpty(type) && tableName.equals("NCMS_CATALOG_DRUG_DIAGNOSIS")) {// 药品诊疗分类判断使用，如无需分类，type可直接为null
			condition = " AND TYPE =" + type;
		}
		if (StringUtils.isNotEmpty(type) && tableName.equals("NCMS_CATALOG_ICD")) {// 疾病诊断分类判断使用，如无需分类，type可直接为null
			String flag = type.substring(1);
			if (flag.equals("n"))
				condition = " AND DIS_TYPE_CODE !=" + type.charAt(0);
			else
				condition = " AND DIS_TYPE_CODE =" + type.charAt(0);
		}
		if (StringUtils.isNotEmpty(type) && tableName.equals("NCMS_SYS_DICT_ITEM")) {// 字典项查询使用
			condition = " AND DICT_CODE ='" + type + "'";
		}
		if (StringUtils.isNotEmpty(type) && tableName.equals("NCMS_INS_HOSPITAL")) {// 机构查询使用
			condition = " AND type ='" + type + "'";
		}
		if (StringUtils.isNotEmpty(type) && tableName.equals("NCMS_CATALOG_COMPEN_TYPE")) {// 机构查询使用
			condition = " AND VISIT_TYPE ='" + type + "'"; // 0门诊，1住院
		}
		return condition;
	}

	/**
	 * 查询疾病和治疗方式对应信息 add by xnn on 2016-06-14
	 *
	 * @param input
	 *            输入的值
	 * @param areaId
	 *            地区
	 * @param type
	 *            传入的类型 0.疾病 1.治疗方式
	 * @param year
	 *            年份
	 * @return
	 */
	public List<Quick> searchIcdTreatment(String input, Integer type, String year, String areaId) {
		List<Quick> quicks = new ArrayList<Quick>();
		String sql = "";
		if (type == 0) {
			sql = "SELECT A.ID||'^'||C.ID ZONG_ID, A.ID,A.CODE,A.NAME,C.ID,C.CODE,C.NAME,0 TYPE FROM NCMS_CATALOG_ICD A "
					+ "LEFT JOIN NCMS_CATALOG_ICD_TREATMENT B ON A.ID = B.ICD_ID "
					+ "LEFT JOIN NCMS_CATALOG_TREATMENT C ON C.ID = B.TREATMENT_ID WHERE A.DIS_TYPE_CODE !=3 ";
		}
		if (type == 1) {
			sql = "SELECT A.ID||'^'||C.ID ZONG_ID, A.ID,A.CODE,A.NAME,C.ID,C.CODE,C.NAME,1 TYPE  FROM NCMS_CATALOG_TREATMENT A "
					+ "LEFT JOIN NCMS_CATALOG_ICD_TREATMENT B ON A.ID = B.TREATMENT_ID "
					+ "LEFT JOIN NCMS_CATALOG_ICD C ON C.ID = B.ICD_ID WHERE 1=1 ";
		}

		sql += "AND A.CURRENT_YEAR='" + year + "' AND A.AREA_ID='" + areaId.substring(0, 6)
				+ "' AND A.FLAG=0 AND (B.FLAG=0 OR B.FLAG IS NULL) AND (C.FLAG=0 OR C.FLAG IS NULL) "
				+ "AND A.STATUS = 0 AND(B.STATUS = 0 OR B.STATUS IS NULL) AND (C.STATUS=0 OR C.STATUS IS NULL) ";

		sql += " AND (A.ID = '" + input + "' OR A.NAME LIKE '" + "%" + input + "%' OR A.CODE LIKE '" + "%" + input
				+ "%' OR UPPER(A.PINYIN) LIKE UPPER('" + "%" + input + "%'))";
		sql += " AND ROWNUM<=20";
		quicks = (List<Quick>) this.getJdbcTemplate().query(sql, new QuickDao.QuickRowMapper2());
		return quicks;
	}

	private class QuickRowMapper implements RowMapper {
		private QuickRowMapper() {
		}

		public Object mapRow(ResultSet rs, int index) throws SQLException {
			if (rs != null && rs.getRow() != 0) {
				Quick quick = new Quick();
				quick.setId(rs.getString(1));
				quick.setText(rs.getString(2));
				if (rs.getMetaData().getColumnCount() > 2) {
					quick.setName(rs.getString(3));
				}
				if (rs.getMetaData().getColumnCount() > 3) {
					quick.setPinyin(rs.getString(4));
				}
				if (rs.getMetaData().getColumnCount() > 4) {
					quick.setParam1(rs.getString(5));
				}
				if (rs.getMetaData().getColumnCount() > 5) {
					quick.setParam2(rs.getString(6));
				}
				return quick;
			} else {
				return null;
			}
		}
	}

	private class QuickRowMapper2 implements RowMapper {
		private QuickRowMapper2() {
		}

		public Object mapRow(ResultSet rs, int index) throws SQLException {
			if (rs != null && rs.getRow() != 0) {
				Quick quick = new Quick();
				quick.setId(rs.getString(1));
				quick.setRid(rs.getString(2));
				quick.setText(rs.getString(3));
				quick.setName(rs.getString(4));
				quick.setId_(rs.getString(5));
				quick.setText_(rs.getString(6));
				quick.setName_(rs.getString(7));
				quick.setType(rs.getString(8));
				return quick;
			} else {
				return null;
			}
		}
	}

	private class QuickRowMapper3 implements RowMapper {
		private QuickRowMapper3() {
		}

		public Object mapRow(ResultSet rs, int index) throws SQLException {
			if (rs != null && rs.getRow() != 0) {
				Quick quick = new Quick();
				quick.setCode(rs.getString(1));
				quick.setName(rs.getString(2));
				quick.setId(rs.getString(3));
				quick.setId_(rs.getString(4));
				quick.setLevel(rs.getString(5));
				quick.setText(rs.getString(6));
				return quick;
			} else {
				return null;
			}
		}
	}
}
