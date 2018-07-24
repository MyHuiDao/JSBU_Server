package com.huidao.fishing.game.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.huidao.common.dto.GetOutGoldDto;
import com.huidao.common.entity.FishBaseProperty;
import com.huidao.common.entity.FishControllerProperty;
import com.huidao.common.entity.FishTypeSetting;
import com.huidao.fishing.game.dto.RoomDto;

/**
 * 捕鱼计算数值
 * 
 * @author Administrator
 *
 */
public class FishComputeUtil {
	/**
	 * 炮弹消耗 炮弹消耗=炮弹基数消耗×炮弹等级×房间倍数
	 * 
	 * @param room
	 * @param level
	 * @return
	 */
	public static long getFireGold(FishBaseProperty fpd, FishControllerProperty fcpd) {
		return (long) (fcpd.getRoomMultiple() * fcpd.getFireLevel() * fpd.getFireConsume());
	}

	/**
	 * 炮弹修正 炮弹修正= 炮弹等级/250
	 */
	public static double getFireXiezheng(FishControllerProperty fcpd) {
		return BigDecimal.valueOf(fcpd.getFireLevel()).divide(BigDecimal.valueOf(500), 10, RoundingMode.DOWN)
				.doubleValue();
	}

	/**
	 * 房间修正 房间修正= 房间倍数/50000
	 * 
	 * @return
	 */
	public static double getRoomXiuZheng(FishControllerProperty fcpd) {
		return BigDecimal.valueOf(fcpd.getRoomMultiple()).divide(BigDecimal.valueOf(6500), 10, RoundingMode.DOWN)
				.doubleValue();
	}

	/**
	 * 房间的收支比
	 * 
	 * @return
	 */
	public static double getRoomGetOut(RoomDto room) {
		if (room.getGoldGet() < room.getMultiple() * 5000) {
			return 0D;
		} else if (room.getGoldGet() >= 3 * room.getGoldOut()) {
			return 0.5D;
		} else if (room.getGoldGet() < 2 * room.getGoldOut()) {
			return 0D;
		} else {
			return (room.getGoldGet() - 2 * room.getGoldOut()) / (2 * room.getGoldOut());
		}
	}

	/**
	 * 幅度修正
	 * 
	 * @return
	 */
	public static double getFuDuXiuZheng(FishBaseProperty fpd, FishControllerProperty fcpd, RoomDto room) {
		return fpd.getControllerFuDuBaseNum() * (fcpd.getFuDuDangWei() - 7) * (fcpd.getFuDuDangWei() - 6) / 2;
	}

	/**
	 * 基础捕获率
	 * 
	 * @return
	 */
	public static double getBaseCatch(FishBaseProperty fpd, FishTypeSetting fnt) {
		return BigDecimal.valueOf(fpd.getFireAttack()).divide(BigDecimal.valueOf(fnt.getHp()), 10, RoundingMode.DOWN)
				.doubleValue();
	}

	/**
	 * 玩家修正 玩家修正=玩家基数- 玩家档位
	 * 
	 * @return
	 */
	public static double getPlayerXiuZheng(FishBaseProperty fpd, FishControllerProperty fcpd) {
		return fpd.getControllerPlayerBaseNum() - fcpd.getPlayerDangWei1();
	}

	/**
	 * 捕获率 捕获率：向下取整到万分位 捕获率=基础捕获率×控制捕获率-基础捕获率×房间修正-基础捕获率×炮弹修正
	 * 
	 * @return
	 */
	public static double getCatch(FishBaseProperty fpd, FishControllerProperty fcpd, FishTypeSetting fnt,
			GetOutGoldDto getOutGoldDto, RoomDto room) {
		return Math.floor((getBaseCatch(fpd, fnt) * getControllerCatch(fpd, fcpd, getOutGoldDto, room)
				- getBaseCatch(fpd, fnt) * getRoomXiuZheng(fcpd) - getBaseCatch(fpd, fnt) * getFireXiezheng(fcpd))
				* 10000) / 100;
	}

	/**
	 * 控制捕获率=1/(玩家修正-控制修正)
	 */
	public static double getControllerCatch(FishBaseProperty fpd, FishControllerProperty fcpd,
			GetOutGoldDto getOutGoldDto, RoomDto room) {
		return 1 / (getPlayerXiuZheng(fpd, fcpd) - getControllerXiuZheng(fpd, fcpd, getOutGoldDto, room));
	}

	/**
	 * 控制修正=整体修正+(捕获基数-收支比)×幅度修正
	 */
	public static double getControllerXiuZheng(FishBaseProperty fpd, FishControllerProperty fcpd,
			GetOutGoldDto getOutGoldDto, RoomDto room) {
		return getWholeXiuZheng(fpd, fcpd)
				+ (fpd.getControllerCatchBaseNum() - getPlayerGetOut(getOutGoldDto)) * getFuDuXiuZheng(fpd, fcpd, room);
	}

	/**
	 * 整体修正=整体基数+ 整体档位/10+(鱼阵档位-1)/10
	 * 
	 * @return
	 */
	public static double getWholeXiuZheng(FishBaseProperty fpd, FishControllerProperty fcpd) {
		return fpd.getControllerBodyBaseNum() + fcpd.getWholeDangWei() / 10 + (fcpd.getFishDangWei() - 1) / 10;
	}

	/**
	 * 获取玩家收支比
	 * 
	 * @param getOutGoldDto
	 * @return
	 */
	public static double getPlayerGetOut(GetOutGoldDto getOutGoldDto) {
		double doubleValue = getOutGoldDto.getCurrentGold()
				.divide(getOutGoldDto.getCostGold().multiply(BigDecimal.valueOf(0.75)), 10, RoundingMode.DOWN)
				.doubleValue();
		if (doubleValue <= 1) {
			return 1;
		}
		return doubleValue;
	}

}
