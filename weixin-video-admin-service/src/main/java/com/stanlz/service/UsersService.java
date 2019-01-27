package com.stanlz.service;

import com.stanlz.entity.Users;
import com.stanlz.utils.PagedResult;

public interface UsersService {
	// 查询用户列表
	public PagedResult queryUsers(Users user, Integer page, Integer pageSize);
}
