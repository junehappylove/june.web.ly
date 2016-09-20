package com.june.controller.system;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.june.mapper.LogMapper;
import com.june.controller.index.BaseController;
import com.june.entity.LogFormMap;
import com.june.plugin.PageView;
import com.june.util.Common;
import com.june.util.MessageUtil;

/**
 * 
 * @author june 2014-11-19
 * @Email: wjw.happy.love@163.com
 * @version 3.0v
 */
@Controller
@RequestMapping("/log/")
public class LogController extends BaseController {
	@Inject
	private LogMapper logMapper;

	@RequestMapping(LIST)
	public String listUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/log/list";
	}

	@ResponseBody
	@RequestMapping(FIND_BY_PAGE)
	public PageView findByPage( String pageNow,
			String pageSize,String column,String sort) throws Exception {
		LogFormMap logFormMap = getFormMap(LogFormMap.class);
		String order = "";
		if(Common.isNotEmpty(column)){
			order = MessageUtil.resource(ORDER_BY, column, sort);
		}else{
			order = ORDER_BY_ID_ASC;
		}
		logFormMap.put("$orderby", order);
		logFormMap=toFormMap(logFormMap, pageNow, pageSize,logFormMap.getStr(PARAM_ORDER_BY));
        pageView.setRecords(logMapper.findByPage(logFormMap));
		return pageView;
	}
}