package com.june.entity;

import com.june.annotation.TableSeg;
import com.june.common.Constants;
import com.june.util.FormMap;



/**
 * user实体表
 */
@TableSeg(tableName = "ly_user", id=Constants.PARAM_ID)
public class UserFormMap extends FormMap<String,Object>{

	/**
	 *@descript
	 *@author june
	 *@date 2015年3月29日
	 *@version 1.0
	 */
	private static final long serialVersionUID = 1L;

}
