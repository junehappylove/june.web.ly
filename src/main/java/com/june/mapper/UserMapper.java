package com.june.mapper;

import java.util.List;

import com.june.entity.UserFormMap;
import com.june.mapper.base.BaseMapper;


public interface UserMapper extends BaseMapper{

	public List<UserFormMap> findUserPage(UserFormMap userFormMap);
	
}
