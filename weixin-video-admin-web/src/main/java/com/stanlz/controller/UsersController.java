package com.stanlz.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stanlz.bean.AdminUser;
import com.stanlz.entity.Users;
import com.stanlz.service.UsersService;
import com.stanlz.utils.JSONResult;
import com.stanlz.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("users")
public class UsersController {
	
	@Autowired
	private UsersService usersService;

	// 跳转到usersList页面
	@GetMapping("/showList")
	public String showList() {
		return "users/usersList";
	}

	// 分页展示用户列表
	@PostMapping("/list")
	@ResponseBody
	public PagedResult list(Users user , Integer page) {
		PagedResult result = usersService.queryUsers(user, page == null ? 1 : page, 10);
		return result;
	}
	
	// 跳转到login页面
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 登录/把user对象设置到Session 也可以用redis实现 这里比较随意
	@PostMapping("login")
	@ResponseBody
	public JSONResult userLogin(String username, String password,
								HttpServletRequest request, HttpServletResponse response) {
		// TODO 模拟登陆
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return JSONResult.errorMap("用户名和密码不能为空");
		} else if (username.equals("linz") && password.equals("linz")) {
			// UUID生成token值
			String token = UUID.randomUUID().toString();
			AdminUser user = new AdminUser(username, password, token);
			request.getSession().setAttribute("sessionUser", user);
			return JSONResult.ok();
		}
		return JSONResult.errorMsg("登陆失败，请重试...");
	}

	// 注销/清除Session值 也可以用redis实现
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("sessionUser");
		return "login";
	}
}
