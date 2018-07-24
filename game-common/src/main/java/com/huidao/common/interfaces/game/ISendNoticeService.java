package com.huidao.common.interfaces.game;



/**
 * 发送消息通知
 * 
 * @author lenovo
 *
 */
public interface ISendNoticeService {

	/**
	 * 发送邮件通知
	 * 
	 * @return
	 */
	public void sendEmailNotice();

	/**
	 * 给指定用户发送邮件通知
	 */

	public void sendUserEmialNotice(String userId);

	/**
	 * 提示用户充值情况
	 * 
	 * @param gold
	 */
	public void noticeRechargeInfo(String userId,Integer gold);

}
