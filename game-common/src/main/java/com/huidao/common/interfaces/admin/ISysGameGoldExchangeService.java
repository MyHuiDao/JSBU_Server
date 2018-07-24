package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 人民币兑换金币接口
 * 
 * @author lenovo
 *
 */
public interface ISysGameGoldExchangeService {
	/**
	 * 查询所有比例
	 * 
	 * @return
	 */
	public MsgDto<Page<GameGoldExchange>> findGameGoldExchangeAll(Integer page, Integer size, Integer type);

	/**
	 * 添加比例
	 * 
	 * @param gameGoldExchange
	 * @return
	 */
	public MsgDto<String> add(GameGoldExchange gameGoldExchange);

	/**
	 * 删除比例
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 修改比例
	 * 
	 * @param gameGoldExchange
	 * @return
	 */
	public MsgDto<String> update(GameGoldExchange gameGoldExchange);

	/**
	 * 获取比例信息
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<GameGoldExchange> getGameGoldExchange(String id);

	/**
	 * 刷新
	 * 
	 * @return
	 */
	public MsgDto<String> refresh();

	/**
	 * 通过支付类型获取兑换比例
	 * 
	 * @param type
	 * @return
	 */
	public MsgDto<List<GameGoldExchange>> getGameGoldExchangeType(Integer type);

}
