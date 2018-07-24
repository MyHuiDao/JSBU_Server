package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.entity.User;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 用户操作接口
 * 
 * @author Administrator
 *
 */
public interface IUserService {
	/**
	 * id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(String id);

	/**
	 * 添加金币
	 * 
	 * @param userId
	 *            用户id
	 * @param gold
	 *            金币数
	 * @param desc
	 *            描述
	 * @return
	 */
	public MsgDto<String> addGold(String userId, long gold, GoldSourceType goldSourceType, GameType gameType,
			String sysUserId);

	/**
	 * 添加金币
	 * 
	 * @param userId
	 *            用户id
	 * @param gold
	 *            金币数
	 * @param desc
	 *            描述
	 * @return
	 */
	public MsgDto<String> addGold(String userId, long gold, GoldSourceType goldSourceType, String desc,
			GameType gameType, String sysUserId);

	/**
	 * 减少金币
	 * 
	 * @param userId
	 * @param gold
	 * @param desc
	 * @return
	 */
	public MsgDto<String> reduceGold(String userId, long gold, GoldSourceType goldSourceType, GameType gameType,
			String sysUserId);

	/**
	 * 减少金币
	 * 
	 * @param userId
	 * @param gold
	 * @param desc
	 * @return
	 */
	public MsgDto<String> reduceGold(String userId, long gold, GoldSourceType goldSourceType, String desc,
			GameType gameType, String sysUserId);

	/**
	 * 锁定所有金币
	 * 
	 * @param userId
	 * @return
	 */
	public Long lockAllGold(String userId);

	/**
	 * 解锁金币
	 * 
	 * @param userId
	 * @return
	 */
	public MsgDto<String> unLockGold(String userId, Long gold, GameType gameType);

	/**
	 * 设备id登录
	 * 
	 * @param deviceId
	 * @return token
	 */
	public MsgDto<String> loginByDeviceId(String deviceId, String ip);

	/**
	 * 账号密码登录
	 * 
	 * @return
	 */
	public MsgDto<String> loginByUser(String account, String password, String ip);

	/**
	 * 微信登录
	 * 
	 * @param code
	 * @return
	 */
	public MsgDto<String> loginByWeixinId(String code, String ip);

	/**
	 * 刷新登录超时
	 * 
	 * @return
	 */
	public void refreshTimeOut(String token);

	/**
	 * 使用token 获取用户
	 * 
	 * @param token
	 * @return
	 */
	public User getUserByToken(String token);

	/**
	 * 用户注册
	 * 
	 * @param ip
	 * @param account
	 * @param password
	 * @return
	 */
	public MsgDto<String> userRegistration(String ip, String account, String password);

	/**
	 * 更新用户头像
	 * 
	 * @param id
	 * @param headImg
	 * @return
	 */
	public MsgDto<String> updateHeadImg(String id, String headImg);

	/**
	 * 绑定手机
	 * 
	 * @param mobile
	 * @param userId
	 */
	public MsgDto<String> bindMobile(String userId, String mobile);

	/**
	 * 解绑手机
	 * 
	 * @param id
	 * @param mobile
	 */
	public MsgDto<String> unBindMobile(String id, String mobile);

	/**
	 * 添加微信用户
	 * 
	 * @param code
	 * @param state
	 * @param ip
	 * @return
	 */
	public MsgDto<String> addUserByWeixinId(String code, String state, String ip);

	/**
	 * 后台显示用户所有信息
	 * 
	 * @param page
	 * @param size
	 * @param code
	 * @param nickName
	 * @param account
	 * @return
	 */
	public MsgDto<Page<User>> findUserAll(Integer page, Integer size, String code, String nickName, String account,
			String type, String id);

	/**
	 * 获得用户的推荐人数
	 * 
	 * @return
	 */
	public List<User> getRecommendUserNumber(String userId);

	/**
	 * 设置体验币
	 * 
	 * @param userId
	 * @param experience
	 * @return
	 */
	public MsgDto<String> setExperience(String userId, Long experience);

	/**
	 * 通过用户code获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	public User getUserCode(String code);

	/**
	 * 后台手动充值金币
	 * 
	 * @param userId
	 * @param gold
	 * @param money
	 * @param freeGold
	 * @param desc
	 * @param admin
	 * @param id
	 * @return
	 */
	public MsgDto<String> payGold(String userId, Integer gold, Integer freeGold, Double money, String desc, String id,
			Integer type);

}
