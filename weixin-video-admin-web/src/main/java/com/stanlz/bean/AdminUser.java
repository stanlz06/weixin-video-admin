package com.stanlz.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员用户对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser {
	private String usertoken;
	private String username;
	private String password;
}
