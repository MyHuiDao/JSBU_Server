package com.huidao.admin.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.admin.web.config.SystemConfig;
import com.huidao.admin.web.service.FileUploadService;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.interfaces.admin.ISysGameGoldExchangeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/gameGoldExchange")
public class GameGoldExchangeController {
	@Reference
	private ISysGameGoldExchangeService gameGoldExchangeService;
	@Autowired
	private SystemConfig systemConfig;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 分页查询比例
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findGameGoldExchange", method = RequestMethod.GET)
	@Permission("game_setting_gold_exchange")
	@JSON
	public MsgDto<Page<GameGoldExchange>> findGameGoldExchange(Integer type) {

		return gameGoldExchangeService.findGameGoldExchangeAll(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize(),type);
	}

	/**
	 * 添加比例
	 * 
	 * @param gameGoldExchange
	 * @return
	 */
	@RequestMapping(value = "/addGameGoldEchange", method = RequestMethod.POST)
	@Permission("game_gold_exchange_new")
	@JSON
	public MsgDto<String> addGameGoldEchange(GameGoldExchange gameGoldExchange) {
		return gameGoldExchangeService.add(gameGoldExchange);
	}

	/**
	 * 修改比例
	 * 
	 * @param gameGoldExchange
	 * @return
	 */
	@RequestMapping(value = "/updateGameGoldEchange", method = RequestMethod.POST)
	@Permission("game_gold_exchange_update")
	@JSON
	public MsgDto<String> updateGameGoldEchange(GameGoldExchange gameGoldExchange) {
		return gameGoldExchangeService.update(gameGoldExchange);
	}

	/**
	 * 删除比例
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteGameGoldEchange", method = RequestMethod.GET)
	@Permission("game_gold_exchange_delete")
	@JSON
	public MsgDto<String> deleteGameGoldEchange(String id) {
		return gameGoldExchangeService.delete(id);
	}

	/**
	 * 获取比例信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getGameGoldEchange", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<GameGoldExchange> getGameGoldEchange(String id) {
		return gameGoldExchangeService.getGameGoldExchange(id);
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadGameGoldEchangeImg", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<List<String>> uploadGameGoldEchangeImg(HttpServletRequest request) {
		String fileUrl = systemConfig.getUploadUrl() + File.separator + sdf.format(new Date());
		List<String> imgs = FileUploadService.uploadManyFile(request, fileUrl, null);
		return MsgFactory.success(imgs);
	}
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> refresh() {
		gameGoldExchangeService.refresh();
		return MsgFactory.success();
	}
	@RequestMapping(value = "/getGameGoldExchangeType", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<List<GameGoldExchange>> getGameGoldExchangeType(Integer type){
		
		return gameGoldExchangeService.getGameGoldExchangeType(type);
	}

}
