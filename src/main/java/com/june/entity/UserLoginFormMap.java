package com.june.entity;

import com.june.annotation.TableSeg;
import com.june.common.Constants;
import com.june.util.FormMap;



/**
 * 实体表
 */
@TableSeg(tableName = "ly_userlogin", id=Constants.PARAM_ID)
public class UserLoginFormMap extends FormMap<String,Object>{

	/**
	 *@descript
	 *@author june
	 *@date 2015年3月29日
	 *@version 1.0
	 */
	private static final long serialVersionUID = 1L;

}
