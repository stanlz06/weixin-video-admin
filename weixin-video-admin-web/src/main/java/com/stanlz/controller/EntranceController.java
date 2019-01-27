package com.stanlz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 入口controller 跳转到center页面
 */

@Controller
public class EntranceController {

	@GetMapping("center")
	public String center() {
		return "center";
	}
}
