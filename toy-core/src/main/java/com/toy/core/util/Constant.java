/*
 * Copyright (c) 2011 Ruaho All rights reserved.
 */
package com.toy.core.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 定义系统中用到的常量
 * 
 */
public class Constant {

	/** 字段分隔符号 */
	public static final String SEPARATOR = ",";
	/** 文件分隔符号 */
	public static final String PATH_SEPARATOR = "/";
	/** 字符集 */
	public static final String ENCODING = "UTF-8";
	/** 回车符 */
	public static final String STR_ENTER = "\r\n";
	/** 参数：PK */
	public static final String _PK_ = "_PK_";
    /** 参数：查询字段 */
    public static final String PARAM_SELECT = "_SELECT_";
    /** 参数：查询表，支持多个 */
    public static final String PARAM_TABLE = "_TABLE_";
    /** 参数：过滤条件 */
    public static final String PARAM_WHERE = "_WHERE_";
    /** 参数：排序设置 */
    public static final String PARAM_ORDER = "_ORDER_";
    /** 参数：分组设置 */
    public static final String PARAM_GROUP = "_GROUP_";
    /** 参数：获取记录行数 */
    public static final String PARAM_ROWNUM = "_ROWNUM_";

	/** 系统参数 */
	public static final String SY_CONFIG = "SY_CONFIG";
	public static final String SY_META = "SY_META";

	public static final String WEB_PORT = "WEB_PORT";

	public static final String EVENT_PATH = "/eventbus/*";

	public static final String ACT_ADD = 	"SY.ADD";
	public static final String ACT_MODIFY = "SY.MODIFY";
	public static final String ACT_DELETE = "SY.DELETE";
	public static final String ACT_QUERY = 	"SY.QUERY";
	public static final String ACT_EXPORT = "SY.EXPORT";

	public static Set<String> actSet = new HashSet<>();
	static {
		actSet.add(Constant.ACT_ADD);
		actSet.add(Constant.ACT_MODIFY);
		actSet.add(Constant.ACT_DELETE);
		actSet.add(Constant.ACT_QUERY);
		actSet.add(Constant.ACT_EXPORT);
	}
	
	public static String getMessage(){
		/**
		 {
			"USER": "admin",
			// 场景的MD5编码
			"SCENE_MD5": "1F69B3D54C2F95A014EA3CC131A34D5B",
			"CREATE_TIME": "2016-06-24 10:25:12",
			"DATA": {
				"SY_SERV": [{
					"_PK_": "111"
					"SERV_ID": "56563EDF23B23981B8836FC60",
					"SERV_CODE": "SY_SERV",
					"SERV_NAME": "服务配置",
					"SY_SERV_ITEM": [{
						"SERV_ID": "J234KK443KJ6KJ456K45K6456",
						"OBJECT_ID": "",
						"ITEM_ID": "",
						"ITEM_NAME": ""
					}, {
						"SERV_ID": "J234KK443KJ6KJ456K45K6456",
						"OBJECT_ID": "",
						"ITEM_ID": "",
						"ITEM_NAME": ""
					}]
				}]
			}
		}
		 */
		return "哈哈你看到啦";
	}
}
