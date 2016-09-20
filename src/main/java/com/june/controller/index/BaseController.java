package com.june.controller.index;

import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.june.common.Constants;
import com.june.entity.ResFormMap;
import com.june.entity.UserFormMap;
import com.june.mapper.ResourcesMapper;
import com.june.plugin.PageView;
import com.june.util.Common;
import com.june.util.FormMap;
import com.june.util.MessageUtil;

/**
 * BaseController
 * 
 * @author june 
 * @Email wjw.happy.love@163.com 
 * @date 2014-2-17
 */
public class BaseController {
	@Inject
	private ResourcesMapper resourcesMapper;
	public static final Logger logger = Logger.getLogger(BaseController.class);

	public PageView pageView = null;

	public PageView getPageView(String pageNow, String pageSize, String orderby) {
		if (Common.isEmpty(pageNow)) {
			pageView = new PageView(1);
		} else {
			pageView = new PageView(Integer.parseInt(pageNow));
		}
		if (Common.isEmpty(pageSize)) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}
		pageView.setPageSize(Integer.parseInt(pageSize));
		pageView.setOrderby(orderby);
		return pageView;
	}

	public <T> T toFormMap(T t, String pageNow, String pageSize, String orderby) {
		@SuppressWarnings("unchecked")
		FormMap<String, Object> formMap = (FormMap<String, Object>) t;
		formMap.put(PARAM_PAGING, getPageView(pageNow, pageSize, orderby));
		return t;
	}

	/**
	 * 获取返回某一页面的按扭组, <br/>
	 * @return
	 */
	public List<ResFormMap> findByRes() {
		// 资源ID
		String id = getPara(PARAM_ID);
		// 获取request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		// 通过工具类获取当前登录的bean
		UserFormMap userFormMap = (UserFormMap) Common.findUserSession(request);
		// user id
		int userId = userFormMap.getInt(PARAM_ID);
		ResFormMap resQueryForm = new ResFormMap();
		resQueryForm.put("parentId", id);
		resQueryForm.put("userId", userId);
		List<ResFormMap> rse = resourcesMapper.findRes(resQueryForm);
		// List<ResFormMap> rse = resourcesMapper.findByAttribute("parentId",
		// id, ResFormMap.class);
		for (ResFormMap resFormMap : rse) {
			Object o = resFormMap.get("description");
			if (o != null && !Common.isEmpty(o.toString())) {
				resFormMap.put("description", Common.stringtohtml(o.toString()));
			}
		}
		return rse;
	}

	/**
	 * 获取页面传递的某一个参数值, <br/>
	 * @param key
	 * @return
	 */
	public String getPara(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getParameter(key);
	}

	/**
	 * 获取页面传递的某一个数组值, <br/>
	 * @param key
	 * @return
	 */
	public String[] getParaValues(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getParameterValues(key);
	}
	/*
	 * @ModelAttribute 这个注解作用.每执行controllor前都会先执行这个方法
	 */
	/*
	 * @ModelAttribute public void init(HttpServletRequest request){ String path
	 * = Common.BACKGROUND_PATH; Object ep =
	 * request.getSession().getAttribute("basePath"); if(ep!=null){
	 * if(!path.endsWith(ep.toString())){ Common.BACKGROUND_PATH =
	 * "/WEB-INF/jsp/background"+ep; } }
	 * 
	 * }
	 */

	/**
	 * 获取传递的所有参数, 反射实例化对象，再设置属性值 通过泛型回传对象. <br/>
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T getFormMap(Class<T> clazz) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			@SuppressWarnings("unchecked")
			FormMap<String, Object> map = (FormMap<String, Object>) t;
			String order = "", sort = "";
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if (nms.endsWith("[]")) {
					String[] as = request.getParameterValues(nms);
					if (as != null && as.length != 0 && as.toString() != "[]") {
						String mname = t.getClass().getSimpleName().toUpperCase();
						if (nms.toUpperCase().startsWith(mname)) {
							nms = nms.substring(nms.toUpperCase().indexOf(mname) + 1);
							map.put(nms, as);
						}
					}
				} else {
					String as = request.getParameter(nms);
					if (!Common.isEmpty(as)) {
						String mname = t.getClass().getSimpleName().toUpperCase();
						if (nms.toUpperCase().startsWith(mname)) {
							nms = nms.substring(mname.length() + 1);
							map.put(nms, as);
						}
						if (nms.toLowerCase().equals(PARAM_COLUMN))
							order = as;
						if (nms.toLowerCase().equals(PARAM_SORT))
							sort = as;
					}
				}
			}
			if (!Common.isEmpty(order) && !Common.isEmpty(sort))
				map.put(PARAM_ORDER_BY, MessageUtil.resource(ORDER_BY, order, sort));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 获取传递的所有参数, 再设置属性值 通过回传Map对象. <br/>
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T findHasHMap(Class<T> clazz) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Enumeration<String> en = request.getParameterNames();
		T t = null;
		try {
			t = clazz.newInstance();
			@SuppressWarnings("unchecked")
			FormMap<String, Object> map = (FormMap<String, Object>) t;
			while (en.hasMoreElements()) {
				String nms = en.nextElement().toString();
				if (!"_t".equals(nms)) {
					if (nms.endsWith("[]")) {
						String[] as = request.getParameterValues(nms);
						if (as != null && as.length != 0 && as.toString() != "[]") {
							map.put(nms, as);
						}
					} else {
						String as = request.getParameter(nms);
						if (!Common.isEmpty(as)) {
							map.put(nms, as);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/** 第一个参数是待排序字段，第二个参数是排序方法[asc desc] */
	public static final String ORDER_BY = Constants.ORDER_BY;
	/** 基于字段的正序排序 order by {0} asc */
	public static final String ORDER_BY_ASC = Constants.ORDER_BY_ASC;
	/** 默认根据id的正序排序 order by id asc */
	public static final String ORDER_BY_ID_ASC = Constants.ORDER_BY_ID_ASC;
	public static final String WHERE_NOT_IN = Constants.WHERE_NOT_IN;
	public static final String PARAM_ORDER_BY = Constants.PARAM_ORDER_BY;
	public static final String PARAM_PAGING = Constants.PARAM_PAGING;
	public static final String PARAM_ID = Constants.PARAM_ID;
	public static final String PARAM_IDS = Constants.PARAM_IDS;
	public static final String PARAM_COLUMN = Constants.PARAM_COLUMN;
	public static final String PARAM_SORT = Constants.PARAM_SORT;
	public static final String PARAM_FLAG = Constants.PARAM_FLAG;
	public static final String PARAM_TYPE = Constants.PARAM_TYPE;
	public static final String PARAM_WHERE = Constants.PARAM_WHERE;
	public static final String SUCCESS = Constants.FLAG_SUCCESS;
	public static final String NULL = Constants.NULL;
	public static final String FIND_BY_PAGE = "findByPage";
	public static final String LIST = "list";
	public static final String LIST_UI = "listUI";
	public static final String ADD_UI = "addUI";
	public static final String ADD_ENTITY = "addEntity";
	public static final String DELETE_ENT = "deleteEntity";
	public static final String EDIT_UI = "editUI";
	public static final String EDIT_ENTITY = "editEntity";
	
}