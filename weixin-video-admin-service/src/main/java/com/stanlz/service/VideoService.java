package com.stanlz.service;

import com.stanlz.entity.Bgm;
import com.stanlz.utils.PagedResult;

public interface VideoService {

	// 增加bgm
	public void addBgm(Bgm bgm);

	// 查询bgm列表
	public PagedResult queryBgmList(Integer page, Integer pageSize);

	// 删除bgm
	public void deleteBgm(String id);

	// 查询被举报的视频列表
	public PagedResult queryReportList(Integer page, Integer pageSize);

	// 修改视频状态
	public void updateVideoStatus(String videoId, Integer status);
}
