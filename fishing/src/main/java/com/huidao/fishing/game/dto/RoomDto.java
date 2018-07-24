package com.huidao.fishing.game.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huidao.common.dto.GetOutGoldDto;
import com.huidao.common.enums.GameType;
import com.huidao.fishing.game.enums.RoomType;

/**
 * 捕鱼房间类
 * @author lenovo
 *
 */
public class RoomDto {
	
	
	
	/**
	 * 是否是鱼阵时间
	 */
	private boolean groupTime=false;
	/**
	 * 房间id
	 */
	private String roomId;
	
	/**
	 * 鱼阵触发时间记录
	 */
	private Date groupStartDate=new Date();
	/**
	 * 房间类型
	 */
	private RoomType roomType;
	
	/**
	 * 房间用户列表
	 */
	private List<String> userList=new ArrayList<>();
	
	
	/**
	 * 用户开炮级别
	 */
	private Map<String, Integer> userFireLevelMap=new HashMap<>();
	
	/**
	 * 用户gold或体验币
	 */
	private Map<String, Long> userGoldMap=new HashMap<>();
	
	/**
	 * 用户是否为Ai
	 */
	private Set<String> userIsAi=new HashSet<>();
	
	/**
	 * 鱼列表
	 */
	private Map<String,FishDto> fishMap=new HashMap<>();
	
	private Collection<FishDto> fishList ;
	
	
	private GameType gameType;

	public Collection<FishDto> getFishList() {
		fishList=fishMap.values();
		return fishList;
	}


	public void setFishList(Collection<FishDto> fishList) {
		this.fishList = fishList;
	}


	/**
	 * 炮
	 */
	private Map<String,FireDto> fireMap=new HashMap<String, FireDto>();
	
	
	private Map<String, GetOutGoldDto> getOutGoldMap=new HashMap<>();
	
	
	/**
	 * 房间金币收入
	 */
	private long goldGet;
	
	
	/**
	 * 房间金币输出
	 */
	private long goldOut;
	
	
	/**
	 * 房间最大人数
	 */
	private int roomMaxCount;
	
	
	/**
	 * 主机id
	 */
	private String userId;
	
	
	/**
	 * 用户开炮一级别多少金币
	 */
	private Integer fireNum; 
	
	/**
	 * 房间捕获鱼倍数
	 */
	
	private Integer multiple;
	/**
	 * 房间最大鱼数量
	 */
	private Integer fishMaxCount;
	
	/**
	 * 房间最小鱼数量
	 */
	private Integer fishMinCount;
	
	/**
	 * 鱼出现概率系数
	 */
	private Integer fishProbability;
	
	/**
	 * 区域id
	 */
	private String areaId;


	public String getRoomId() {
		return roomId;
	}


	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}


	public RoomType getRoomType() {
		return roomType;
	}


	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}


	public List<String> getUserList() {
		return userList;
	}


	public void setUserList(List<String> userList) {
		this.userList = userList;
	}




	


	public Map<String, FishDto> getFishMap() {
		return fishMap;
	}


	public void setFishMap(Map<String, FishDto> fishMap) {
		this.fishMap = fishMap;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getRoomMaxCount() {
		return roomMaxCount;
	}


	public void setRoomMaxCount(int roomMaxCount) {
		this.roomMaxCount = roomMaxCount;
	}
	
	
	
	public Set<String> getUserIsAi() {
		return userIsAi;
	}


	public void setUserIsAi(Set<String> userIsAi) {
		this.userIsAi = userIsAi;
	}


	/**
	 * 获取房间实际人数
	 * @return
	 */
	public int getRoomFreeBit() {
		int count=0;
		for (int i = 0; i < this.getUserList().size(); i++) {
			if(this.getUserList().get(i)!=null&&!userIsAi.contains(this.getUserList().get(i))) {
				count++;
			}
		}
		return count;
	}
	
	
	public int addRoom(String userId) {
		for (int i = 0; i < this.getUserList().size(); i++) {
			if(this.getUserList().get(i)==null) {
				this.getUserList().set(i, userId);
				return i;
			}
		}
		if(this.getUserList().size()<roomMaxCount) {
			int result=this.getUserList().size();
			this.getUserList().add(userId);
			return result;
		}
		throw new RuntimeException("程序异常");
	}
	
	/**
	 * 退出房间
	 * @param userId
	 */
	public void exitRoom(String userId) {
		for (int i = 0; i < this.getUserList().size(); i++) {
			if(this.getUserList().get(i).equals(userId)) {
				this.getUserList().set(i, null);
				return;
			}
		}
	}


	public Map<String, Integer> getUserFireLevelMap() {
		return userFireLevelMap;
	}


	public void setUserFireLevelMap(Map<String, Integer> userFireLevelMap) {
		this.userFireLevelMap = userFireLevelMap;
	}


	public Integer getFireNum() {
		return fireNum;
	}


	public void setFireNum(Integer fireNum) {
		this.fireNum = fireNum;
	}


	public Map<String, Long> getUserGoldMap() {
		return userGoldMap;
	}


	public void setUserGoldMap(Map<String, Long> userGoldMap) {
		this.userGoldMap = userGoldMap;
	}


	public Map<String, FireDto> getFireMap() {
		return fireMap;
	}


	public void setFireMap(Map<String, FireDto> fireMap) {
		this.fireMap = fireMap;
	}


	public Map<String, GetOutGoldDto> getGetOutGoldMap() {
		return getOutGoldMap;
	}


	public void setGetOutGoldMap(Map<String, GetOutGoldDto> getOutGoldMap) {
		this.getOutGoldMap = getOutGoldMap;
	}


	public Integer getMultiple() {
		return multiple;
	}


	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}


	public Integer getFishMaxCount() {
		return fishMaxCount;
	}


	public void setFishMaxCount(Integer fishMaxCount) {
		this.fishMaxCount = fishMaxCount;
	}


	public Integer getFishMinCount() {
		return fishMinCount;
	}


	public void setFishMinCount(Integer fishMinCount) {
		this.fishMinCount = fishMinCount;
	}


	public Integer getFishProbability() {
		return fishProbability;
	}


	public void setFishProbability(Integer fishProbability) {
		this.fishProbability = fishProbability;
	}


	public String getAreaId() {
		return areaId;
	}


	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}


	public Date getGroupStartDate() {
		return groupStartDate;
	}


	public void setGroupStartDate(Date groupStartDate) {
		this.groupStartDate = groupStartDate;
	}


	public boolean isGroupTime() {
		return groupTime;
	}


	public void setGroupTime(boolean groupTime) {
		this.groupTime = groupTime;
	}


	public long getGoldGet() {
		return goldGet;
	}


	public void setGoldGet(long goldGet) {
		this.goldGet = goldGet;
	}


	public long getGoldOut() {
		return goldOut;
	}


	public void setGoldOut(long goldOut) {
		this.goldOut = goldOut;
	}


	public GameType getGameType() {
		return gameType;
	}


	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}


	
	

	
	
}
