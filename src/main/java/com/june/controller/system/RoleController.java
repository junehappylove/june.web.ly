package com.june.controller.system;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.june.annotation.SystemLog;
import com.june.common.Constants;
import com.june.controller.index.BaseController;
import com.june.entity.RoleFormMap;
import com.june.mapper.RoleMapper;
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
@RequestMapping("/role/")
public class RoleController extends BaseController {
	@Inject
	private RoleMapper roleMapper;

	@RequestMapping(LIST)
	public String listUI(Model model) throws Exception {
		model.addAttribute("res", findByRes());
		return Common.BACKGROUND_PATH + "/system/role/list";
	}

	@ResponseBody
	@RequestMapping(FIND_BY_PAGE)
	public PageView findByPage(String pageNow,
			String pageSize) throws Exception {
		RoleFormMap roleFormMap = getFormMap(RoleFormMap.class);
		roleFormMap=toFormMap(roleFormMap, pageNow, pageSize,roleFormMap.getStr(PARAM_ORDER_BY));
        pageView.setRecords(roleMapper.findByPage(roleFormMap));
		return pageView;
	}

	@RequestMapping(ADD_UI)
	public String addUI(Model model) throws Exception {
		return Common.BACKGROUND_PATH + "/system/role/add";
	}

	@ResponseBody
	@RequestMapping(ADD_ENTITY)
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="组管理-新增组")//凡需要处理业务逻辑的.都需要记录操作日志
	public String addEntity() throws Exception {
		RoleFormMap roleFormMap = getFormMap(RoleFormMap.class);
		roleMapper.addEntity(roleFormMap);
		return "success";
	}

	@ResponseBody
	@RequestMapping(DELETE_ENT)
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="组管理-删除组")//凡需要处理业务逻辑的.都需要记录操作日志
	public String deleteEntity() throws Exception {
		String[] ids = getParaValues(PARAM_IDS);
		for (String id : ids) {
			roleMapper.deleteByAttribute(PARAM_ID, id, RoleFormMap.class);
		}
		return "success";
	}

	@RequestMapping(EDIT_UI)
	public String editUI(Model model) throws Exception {
		String id = getPara(PARAM_ID);
		if(Common.isNotEmpty(id)){
			model.addAttribute("role", roleMapper.findbyFrist(PARAM_ID, id, RoleFormMap.class));
		}
		return Common.BACKGROUND_PATH + "/system/role/edit";
	}

	@ResponseBody
	@RequestMapping(EDIT_ENTITY)
	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="系统管理",methods="组管理-修改组")//凡需要处理业务逻辑的.都需要记录操作日志
	public String editEntity() throws Exception {
		RoleFormMap roleFormMap = getFormMap(RoleFormMap.class);
		roleMapper.editEntity(roleFormMap);
		return SUCCESS;
	}
	
	
	@RequestMapping("selRole")
	public String seletRole(Model model) throws Exception {
		RoleFormMap roleFormMap = getFormMap(RoleFormMap.class);
		Object userId = roleFormMap.get("userId");
		if(null!=userId){
			List<RoleFormMap> list = roleMapper.seletUserRole(roleFormMap);
			String ugid = NULL;
			for (RoleFormMap ml : list) {
				ugid += ml.get(PARAM_ID)+Constants.FLAG_COMMA;
			}
			ugid = Common.trimComma(ugid);
			model.addAttribute("txtRoleSelect", ugid);
			model.addAttribute("userRole", list);
			if(StringUtils.isNotBlank(ugid)){
				String where = MessageUtil.resource(WHERE_NOT_IN, PARAM_ID,ugid);
				roleFormMap.put(PARAM_WHERE, where);
			}
		}
		List<RoleFormMap> roles = roleMapper.findByWhere(roleFormMap);
		model.addAttribute("role", roles);
		return Common.BACKGROUND_PATH + "/system/user/roleSelect";
	}

}