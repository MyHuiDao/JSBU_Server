package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 游戏区类别
 */
@Entity("game_area")
public class GameArea implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 游戏类型(fishing)
	 **/
	@Column
	private String gameType;
	/**
	 * 图片
	 **/
	@Column
	private String img;
	/**
	 * 名称
	 **/
	@Column
	private String name;
	/**
	 * 状态(0:正常,1:未开放,2:不显示)
	 **/
	@Column
	private String status;
	/**
	 * 排序
	 **/
	@Column
	private Integer seq;
	/**
	 * 类型(gold:金币场,experience:体验场:)
	 **/
	@Column
	private String type;
	/**
	 * 最小进入数值
	 **/
	@Column
	private Integer minNum;
	/**
	 * 房间最大人数
	 **/
	@Column
	private Integer roomMax;
	/**
	 * 增加一级多少金币
	 **/
	@Column
	private Integer levelGold;
	/**
	 * 捕获倍数
	 **/
	@Column
	private Integer multiple;
	/**
	 * 鱼最大出现数量
	 **/
	@Column
	private Integer fishMaxCount;
	/**
	 * 鱼最小数量
	 **/
	@Column
	private Integer fishMinCount;
	/**
	 * 鱼出现概率系数
	 **/
	@Column
	private Integer fishProbability;

	/**
	 * 规则id
	 */
	private String frId;

	/**
	 * 规则名称
	 */
	private String frName;

	/**
	 * 关联表id
	 */
	private String areaRuleId;

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
	 * 游戏类型(fishing)
	 **/
	public String getGameType() {
		return this.gameType;
	}

	/**
	 * 游戏类型(fishing)
	 **/
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	/**
	 * 图片
	 **/
	public String getImg() {
		return this.img;
	}

	/**
	 * 图片
	 **/
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 状态(0:正常,1:未开放,2:不显示)
	 **/
	public String getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:正常,1:未开放,2:不显示)
	 **/
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 排序
	 **/
	public Integer getSeq() {
		return this.seq;
	}

	/**
	 * 排序
	 **/
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * 类型(gold:金币场,experience:体验场:)
	 **/
	public String getType() {
		return this.type;
	}

	/**
	 * 类型(gold:金币场,experience:体验场:)
	 **/
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 最小进入数值
	 **/
	public Integer getMinNum() {
		return this.minNum;
	}

	/**
	 * 最小进入数值
	 **/
	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	/**
	 * 房间最大人数
	 **/
	public Integer getRoomMax() {
		return this.roomMax;
	}

	/**
	 * 房间最大人数
	 **/
	public void setRoomMax(Integer roomMax) {
		this.roomMax = roomMax;
	}

	/**
	 * 增加一级多少金币
	 **/
	public Integer getLevelGold() {
		return this.levelGold;
	}

	/**
	 * 增加一级多少金币
	 **/
	public void setLevelGold(Integer levelGold) {
		this.levelGold = levelGold;
	}

	/**
	 * 捕获倍数
	 **/
	public Integer getMultiple() {
		return this.multiple;
	}

	/**
	 * 捕获倍数
	 **/
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * 鱼最大出现数量
	 **/
	public Integer getFishMaxCount() {
		return this.fishMaxCount;
	}

	/**
	 * 鱼最大出现数量
	 **/
	public void setFishMaxCount(Integer fishMaxCount) {
		this.fishMaxCount = fishMaxCount;
	}

	/**
	 * 鱼最小数量
	 **/
	public Integer getFishMinCount() {
		return this.fishMinCount;
	}

	/**
	 * 鱼最小数量
	 **/
	public void setFishMinCount(Integer fishMinCount) {
		this.fishMinCount = fishMinCount;
	}

	/**
	 * 鱼出现概率系数
	 **/
	public Integer getFishProbability() {
		return this.fishProbability;
	}

	/**
	 * 鱼出现概率系数
	 **/
	public void setFishProbability(Integer fishProbability) {
		this.fishProbability = fishProbability;
	}

	/**
	 * 规则id
	 * 
	 * @return
	 */
	public String getFrId() {
		return frId;
	}

	/**
	 * 规则id
	 * 
	 * @return
	 */
	public void setFrId(String frId) {
		this.frId = frId;
	}

	/**
	 * 规则名称
	 * 
	 * @return
	 */
	public String getFrName() {
		return frName;
	}

	/**
	 * 规则名称
	 * 
	 * @return
	 */
	public void setFrName(String frName) {
		this.frName = frName;
	}

	/**
	 * 关联表id
	 * 
	 * @return
	 */
	public String getAreaRuleId() {
		return areaRuleId;
	}

	/**
	 * 关联表id
	 * 
	 * @param areaRuleId
	 */
	public void setAreaRuleId(String areaRuleId) {
		this.areaRuleId = areaRuleId;
	}

}
