package com.june.mapper;

import java.util.List;

import com.june.entity.RoleFormMap;
import com.june.mapper.base.BaseMapper;

public interface RoleMapper extends BaseMapper{
	public List<RoleFormMap> seletUserRole(RoleFormMap map);
	
	public void deleteById(RoleFormMap map);
}
