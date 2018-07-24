package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.FishOdds;
import com.huidao.common.msg.MsgDto;

/**
 * 捕鱼赔率设置接口
 * 
 * @author lenovo
 *
 */
public interface IFishOddsService {
	/**
	 * 查询所有赔率
	 * 
	 * @return
	 */
	public List<FishOdds> getFishOddsAll();

	/**
	 * 添加捕鱼赔率
	 * 
	 * @param fishOdds
	 * @return
	 */
	public MsgDto<String> add(FishOdds fishOdds);

	/**
	 * 修改捕鱼赔率
	 * 
	 * @param fishOdds
	 * @return
	 */
	public MsgDto<String> update(FishOdds fishOdds);

	/**
	 * 删除捕鱼赔率
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取捕鱼赔率对象
	 * 
	 * @param id
	 * @return
	 */
	public FishOdds get(String id);

}
