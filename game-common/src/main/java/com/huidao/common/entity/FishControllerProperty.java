package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 捕鱼可控数值
 */
@Entity("fish_controller_property")
public class FishControllerProperty implements Serializable {
	
	private static final double [] playerDw= {1,1,5,6,6.1,6.1};
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 炮弹等级
	 **/
	@Column
	private Integer fireLevel;
	/**
	 * 发射数量
	 **/
	@Column
	private Integer fireSendNum;
	/**
	 * 鱼等级
	 **/
	@Column
	private Integer fishLevel;
	/**
	 * 房间倍数
	 **/
	@Column
	private Integer roomMultiple;
	/**
	 * 整体档位
	 **/
	@Column
	private Double wholeDangWei;
	/**
	 * 玩家档位
	 **/
	@Column
	private Double playerDangWei;
	/**
	 * 鱼阵档位
	 **/
	@Column
	private Double fishDangWei;
	/**
	 * 幅度档位
	 **/
	@Column
	private Double fuDuDangWei;
	/**
	 * 类型(0:公共,1:区域,2:个人)
	 **/
	@Column
	private Integer type;
	/**
	 * 关联id
	 **/
	@Column
	private String joinId;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 房间名称
	 */
	private String name;

	/**
	 * 主键
	 **/
	public String getId() {
		return this.id;
	}

	/**
	 * 主键
	 **/
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 炮弹等级
	 **/
	public Integer getFireLevel() {
		return this.fireLevel;
	}

	/**
	 * 炮弹等级
	 **/
	public void setFireLevel(Integer fireLevel) {
		this.fireLevel = fireLevel;
	}

	/**
	 * 发射数量
	 **/
	public Integer getFireSendNum() {
		return this.fireSendNum;
	}

	/**
	 * 发射数量
	 **/
	public void setFireSendNum(Integer fireSendNum) {
		this.fireSendNum = fireSendNum;
	}

	/**
	 * 鱼等级
	 **/
	public Integer getFishLevel() {
		return this.fishLevel;
	}

	/**
	 * 鱼等级
	 **/
	public void setFishLevel(Integer fishLevel) {
		this.fishLevel = fishLevel;
	}

	/**
	 * 房间倍数
	 **/
	public Integer getRoomMultiple() {
		return this.roomMultiple;
	}

	/**
	 * 房间倍数
	 **/
	public void setRoomMultiple(Integer roomMultiple) {
		this.roomMultiple = roomMultiple;
	}

	/**
	 * 整体档位
	 **/
	public Double getWholeDangWei() {
		return this.wholeDangWei;
	}

	/**
	 * 整体档位
	 **/
	public void setWholeDangWei(Double wholeDangWei) {
		this.wholeDangWei = wholeDangWei;
	}

	/**
	 * 玩家档位
	 **/
	public Double getPlayerDangWei1() {
		return playerDw[this.playerDangWei.intValue()];
	}
	
	/**
	 * 玩家档位
	 **/
	public Double getPlayerDangWei() {
		return this.playerDangWei;
	}

	/**
	 * 玩家档位
	 **/
	public void setPlayerDangWei(Double playerDangWei) {
		this.playerDangWei = playerDangWei;
	}

	/**
	 * 鱼阵档位
	 **/
	public Double getFishDangWei() {
		return this.fishDangWei;
	}

	/**
	 * 鱼阵档位
	 **/
	public void setFishDangWei(Double fishDangWei) {
		this.fishDangWei = fishDangWei;
	}

	/**
	 * 幅度档位
	 **/
	public Double getFuDuDangWei() {
		return this.fuDuDangWei;
	}

	/**
	 * 幅度档位
	 **/
	public void setFuDuDangWei(Double fuDuDangWei) {
		this.fuDuDangWei = fuDuDangWei;
	}

	/**
	 * 类型(0:公共,1:区域,2:个人)
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 类型(0:公共,1:区域,2:个人)
	 **/
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 关联id
	 **/
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * 关联id
	 **/
	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}
