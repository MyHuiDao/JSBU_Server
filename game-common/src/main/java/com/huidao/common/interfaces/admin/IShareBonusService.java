package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.ShareBonus;
import com.huidao.common.msg.MsgDto;

public interface IShareBonusService {
	/**
	 * 获取所有分享奖励
	 * 
	 * @return
	 */
	public List<ShareBonus> findShareBonusAll();

	/**
	 * 添加分享奖励
	 * 
	 * @param shareBouns
	 * @return
	 */
	public MsgDto<String> add(ShareBonus shareBouns);

	/**
	 * 修改分享奖励
	 * 
	 * @param shareBouns
	 * @return
	 */
	public MsgDto<String> update(ShareBonus shareBouns);

	/**
	 * 删除分享奖励
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取分享奖励
	 * 
	 * @param id
	 * @return
	 */
	public ShareBonus get(String id);

}
