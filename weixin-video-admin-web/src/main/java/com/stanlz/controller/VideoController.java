package com.stanlz.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.stanlz.entity.Bgm;
import com.stanlz.enums.VideoStatusEnum;
import com.stanlz.service.VideoService;
import com.stanlz.utils.JSONResult;
import com.stanlz.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("video")
public class VideoController {
	
	@Autowired
	private VideoService videoService;

	// 跳转到reportList页面
	@GetMapping("/showReportList")
	public String showReportList() {
		return "video/reportList";
	}

	// 获取被举报的视频列表
	@PostMapping("/reportList")
	@ResponseBody
	public PagedResult reportList(Integer page) {
		PagedResult result = videoService.queryReportList(page, 10);
		return result;
	}

	// 禁播视频
	@PostMapping("/forbidVideo")
	@ResponseBody
	public JSONResult forbidVideo(String videoId) {
		// 只是把视频的状态设置成2（禁播）
		videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
		return JSONResult.ok();
	}

	// 跳转到bgmList页面
	@GetMapping("/showBgmList")
	public String showBgmList() {
		return "video/bgmList";
	}

	// 查询bgm列表
	@PostMapping("/queryBgmList")
	@ResponseBody
	public PagedResult queryBgmList(Integer page) {
		return videoService.queryBgmList(page, 10);
	}

	//跳转到addBgm页面
	@GetMapping("/showAddBgm")
	public String login() {
		return "video/addBgm";
	}

	// 添加bgm
	@PostMapping("/addBgm")
	@ResponseBody
	public JSONResult addBgm(Bgm bgm) {
		videoService.addBgm(bgm);
		return JSONResult.ok();
	}

	// 删除bgm
	@PostMapping("/delBgm")
	@ResponseBody
	public JSONResult delBgm(String bgmId) {
		videoService.deleteBgm(bgmId);
		return JSONResult.ok();
	}

	// 上传bgm
	@PostMapping("/bgmUpload")
	@ResponseBody
	public JSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {
		// 文件保存的命名空间
		String fileSpace = "E:" + File.separator + "savepath" + File.separator +
				"userinfo-path"+ File.separator + "mvc-bgm";

		// 保存到数据库中的相对路径
		String uploadPathDB = File.separator + "bgm";
		
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (files != null && files.length > 0) {
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalPath = fileSpace + uploadPathDB + File.separator + fileName;
					// 设置数据库保存的路径
					uploadPathDB += (File.separator + fileName);
					
					File outFile = new File(finalPath);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						// 创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}
			} else {
				return JSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.errorMsg("上传出错...");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		return JSONResult.ok(uploadPathDB);
	}
}
