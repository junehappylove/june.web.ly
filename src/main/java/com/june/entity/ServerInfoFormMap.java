package com.june.entity;

import com.june.annotation.TableSeg;
import com.june.common.Constants;
import com.june.util.FormMap;

@TableSeg(tableName = "ly_server_info", id=Constants.PARAM_ID)
public class ServerInfoFormMap extends FormMap<String, Object> {
	private static final long serialVersionUID = 1L;
}