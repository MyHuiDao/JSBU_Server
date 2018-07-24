package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.EmailUser;
import com.huidao.common.entity.GameEmail;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 邮件管理
 * 
 * @author lenovo
 *
 */
public interface IGameEmailService {

	/**
	 * 获取所有邮件
	 * 
	 * @return
	 */
	public MsgDto<Page<GameEmail>> findGameEmailAll(Integer page, Integer size, String title, String status);

	/**
	 * 添加邮件
	 * 
	 * @param gameEmail
	 * @return
	 */
	public MsgDto<Integer> add(GameEmail gameEmail);

	/**
	 * 修改邮件
	 * 
	 * @param gameEmail
	 * @return
	 */
	public MsgDto<Integer> update(GameEmail gameEmail);

	/**
	 * 删除邮件
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> delete(String id);

	/**
	 * 获取邮件
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<GameEmail> get(String id);

	/**
	 * 发送邮件
	 * 
	 * @return
	 */
	public MsgDto<Integer> sendEmail(String emailId, String[] userIds);

	/**
	 * 将邮件与用户关联
	 * 
	 * @param emailId
	 * @param userId
	 * @return
	 */
	public Integer emailRelated(EmailUser emailUser);

	/**
	 * 用户邮件数据
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public MsgDto<Page<GameEmail>> getUserGameEmail(Integer page, Integer size, String userId);

	/**
	 * 获取用户没有关联的邮件
	 * 
	 * @param id
	 * @return
	 */
	public List<GameEmail> getUserNotGameEmail(String id);

}
