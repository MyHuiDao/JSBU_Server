package com.huidao.fishing.game.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.FishTypeSetting;
import com.huidao.common.entity.GameFish;
import com.huidao.common.interfaces.admin.IFishTypeSettingService;
import com.huidao.common.interfaces.admin.IGameFishService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.fishing.game.dto.FishDto;

/**
 * 鱼类生产
 * 
 * @author lenovo
 *
 */
@Component
public class FishManager {

	public static Map<Integer, FishTypeSetting> map = new HashMap<>();

	private static long mapGetTime;

	@Reference
	private IFishTypeSettingService fishTypeSettingService;

	@Reference
	private IGameFishService gameFishService;

	@Reference
	private IGameSettingService gameSettingService;

	public List<FishTypeSetting> getFishTypeSettingList() {
		return fishTypeSettingService.getFishTypeSetting();
	}

	public Map<Integer, FishTypeSetting> getFishTypeSettingMap() {
		if (map.size() > 0 && (System.currentTimeMillis() - mapGetTime) <= 60 * 5 * 1000) {
			return map;
		}
		List<FishTypeSetting> list = getFishTypeSettingList();
		for (FishTypeSetting fnt : list) {
			map.put(fnt.getLevel(), fnt);
		}
		mapGetTime = System.currentTimeMillis();
		return map;
	}

	public int getSumFishPower(List<GameFish> data) {
		int fishPower = 0;
		for (GameFish gf : data) {
			fishPower += gf.getPower();
		}
		return fishPower;
	}

	/**
	 * 鱼阵
	 * 
	 * @return
	 */
	public List<FishDto> fishArray() {
		List<FishDto> fishDtos = new ArrayList<FishDto>();
		Integer number = Integer.valueOf(gameSettingService.getValue(GameSettingContants.fish_array_number));
		Integer random = ThreadLocalRandom.current().nextInt(number) + 1;
		// Integer random = 1;
		switch (random) {
		case 1:
			// 鱼阵1

			// 级别18
			FishTypeSetting level1 = getFishTypeSettingMap().get(18);
			for (int i = 0; i < 2; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level1.getGold());
				fd.setGetGoldEnd(level1.getGold());
				String[] speeds = level1.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level1.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别20
			FishTypeSetting level2 = getFishTypeSettingMap().get(20);
			for (int i = 0; i < 1; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level2.getGold());
				fd.setGetGoldEnd(level2.getGold());
				String[] speeds = level2.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level2.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别5
			FishTypeSetting level3 = getFishTypeSettingMap().get(5);
			for (int i = 0; i < 3; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level3.getGold());
				fd.setGetGoldEnd(level3.getGold());
				String[] speeds = level3.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level3.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别10
			FishTypeSetting level5 = getFishTypeSettingMap().get(10);
			for (int i = 0; i < 4; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level5.getGold());
				fd.setGetGoldEnd(level5.getGold());
				String[] speeds = level5.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level5.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别8
			FishTypeSetting level8 = getFishTypeSettingMap().get(8);
			for (int i = 0; i < 4; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level8.getGold());
				fd.setGetGoldEnd(level8.getGold());
				String[] speeds = level8.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level8.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别1
			FishTypeSetting level10 = getFishTypeSettingMap().get(1);
			for (int i = 0; i < 37; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level10.getGold());
				fd.setGetGoldEnd(level10.getGold());
				String[] speeds = level10.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level10.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别2
			FishTypeSetting level18 = getFishTypeSettingMap().get(2);
			for (int i = 0; i < 24; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level18.getGold());
				fd.setGetGoldEnd(level18.getGold());
				String[] speeds = level18.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level18.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 级别3
			FishTypeSetting level20 = getFishTypeSettingMap().get(3);
			for (int i = 0; i < 31; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(level20.getGold());
				fd.setGetGoldEnd(level20.getGold());
				String[] speeds = level20.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("1");
				fd.setLevel(level20.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			break;
		case 2:
			// 鱼阵2
			// level1
			FishTypeSetting lvhetun = getFishTypeSettingMap().get(1);
			for (int i = 0; i < 100; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhetun.getGold());
				fd.setGetGoldEnd(lvhetun.getGold());
				String[] speeds = lvhetun.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("2");
				fd.setLevel(lvhetun.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// level4
			FishTypeSetting honghaigui = getFishTypeSettingMap().get(4);
			for (int i = 0; i < 28; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(honghaigui.getGold());
				fd.setGetGoldEnd(honghaigui.getGold());
				String[] speeds = honghaigui.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("2");
				fd.setLevel(honghaigui.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// level19
			FishTypeSetting shayu = getFishTypeSettingMap().get(19);
			for (int i = 0; i < 1; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(shayu.getGold());
				fd.setGetGoldEnd(shayu.getGold());
				String[] speeds = shayu.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("2");
				fd.setLevel(shayu.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			break;
		case 3:
			// 鱼阵3
			// level1
			FishTypeSetting lvhetun3 = getFishTypeSettingMap().get(1);
			for (int i = 0; i < 40; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhetun3.getGold());
				fd.setGetGoldEnd(lvhetun3.getGold());
				String[] speeds = lvhetun3.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("3");
				fd.setLevel(lvhetun3.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// leve9
			FishTypeSetting honghaigui3 = getFishTypeSettingMap().get(9);
			for (int i = 0; i < 10; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(honghaigui3.getGold());
				fd.setGetGoldEnd(honghaigui3.getGold());
				String[] speeds = honghaigui3.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("3");
				fd.setLevel(honghaigui3.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// level11
			FishTypeSetting shenxianyu9 = getFishTypeSettingMap().get(11);
			for (int i = 0; i < 5; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(shenxianyu9.getGold());
				fd.setGetGoldEnd(shenxianyu9.getGold());
				String[] speeds = shenxianyu9.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("3");
				fd.setLevel(shenxianyu9.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// level4
			FishTypeSetting lvhaigui11 = getFishTypeSettingMap().get(4);
			for (int i = 0; i < 22; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui11.getGold());
				fd.setGetGoldEnd(lvhaigui11.getGold());
				String[] speeds = lvhaigui11.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("3");
				fd.setLevel(lvhaigui11.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}
			break;
		case 4:
			// 24
			FishTypeSetting lvhaigui24 = getFishTypeSettingMap().get(20);
			for (int i = 0; i < 1; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui24.getGold());
				fd.setGetGoldEnd(lvhaigui24.getGold());
				String[] speeds = lvhaigui24.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("4");
				fd.setLevel(lvhaigui24.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 2
			FishTypeSetting lvhaigui2 = getFishTypeSettingMap().get(2);
			for (int i = 0; i < 12; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui2.getGold());
				fd.setGetGoldEnd(lvhaigui2.getGold());
				String[] speeds = lvhaigui2.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("4");
				fd.setLevel(lvhaigui2.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 4
			FishTypeSetting lvhaigui4 = getFishTypeSettingMap().get(4);
			for (int i = 0; i < 9; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui4.getGold());
				fd.setGetGoldEnd(lvhaigui4.getGold());
				String[] speeds = lvhaigui4.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("4");
				fd.setLevel(lvhaigui4.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 8
			FishTypeSetting lvhaigui8 = getFishTypeSettingMap().get(8);
			for (int i = 0; i < 10; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui8.getGold());
				fd.setGetGoldEnd(lvhaigui8.getGold());
				String[] speeds = lvhaigui8.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("4");
				fd.setLevel(lvhaigui8.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 15
			FishTypeSetting lvhaigui15 = getFishTypeSettingMap().get(15);
			for (int i = 0; i < 6; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui15.getGold());
				fd.setGetGoldEnd(lvhaigui15.getGold());
				String[] speeds = lvhaigui15.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("4");
				fd.setLevel(lvhaigui15.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}

			// 18
			FishTypeSetting lvhaigui18 = getFishTypeSettingMap().get(18);
			for (int i = 0; i < 4; i++) {
				FishDto fd = new FishDto();
				// 1.鱼的类型
				fd.setGetGoldStrart(lvhaigui18.getGold());
				fd.setGetGoldEnd(lvhaigui18.getGold());
				String[] speeds = lvhaigui18.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setGroup("4");
				fd.setLevel(lvhaigui18.getLevel());
				fd.setId(UUID.randomUUID().toString());
				fishDtos.add(fd);
			}
			break;
		default:
			break;
		}

		return fishDtos;
	}

	public List<FishDto> init() {
		List<FishDto> list1 = new ArrayList<>();
		List<FishTypeSetting> list = getFishTypeSettingList();
		for (FishTypeSetting fnt : list) {
			for (int i = 0; i < fnt.getInitCount(); i++) {
				FishDto fd = new FishDto();
				fd.setGetGoldStrart(fnt.getGold());
				fd.setGetGoldEnd(fnt.getGold());
				fd.setLevel(fnt.getLevel());
				String[] speeds = fnt.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setId(UUID.randomUUID().toString());
				fd.setRunPoint("gj" + (ThreadLocalRandom.current().nextInt(30) + 1));
				list1.add(fd);
			}
		}
		return list1;
	}

	public void addFish(List<FishTypeSetting> fishTypeList, List<FishDto> addList, Map<String, FishDto> roomFish,
			Integer roomMaxFish) {
		if (roomFish.size() >= roomMaxFish) {
			return;
		}
		if (fishTypeList == null) {
			fishTypeList = getFishTypeSettingList();
		}
		Map<Integer, Integer> map = new HashMap<>();
		for (FishDto fd : roomFish.values()) {
			Integer resultIndex = map.get(fd.getLevel());
			if (resultIndex == null) {
				resultIndex = 0;
			}
			resultIndex++;
			map.put(fd.getLevel(), resultIndex);
		}
		double currentSum = 0D;
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (int i = fishTypeList.size() - 1; i >= 0; i--) {
			FishTypeSetting fishNewType = fishTypeList.get(i);
			Integer num = map.get(fishNewType.getLevel());
			if (num == null) {
				num = 0;
			}
			int needAddCount = fishNewType.getMaxCount() - num;
			if (needAddCount <= 0) {
				continue;
			}
			Map<String, Object> result = new HashMap<>();
			result.put("start", currentSum);
			result.put("fishType", fishNewType);
			result.put("end", currentSum + needAddCount * fishNewType.getRefresh());
			currentSum += needAddCount * fishNewType.getRefresh();
			resultList.add(result);
			if (currentSum > 100) {
				break;
			}
		}
		Double random = ThreadLocalRandom.current().nextDouble(100);
		int before = roomFish.size();
		for (Map<String, Object> resultMap : resultList) {
			Double start = (Double) resultMap.get("start");
			Double end = (Double) resultMap.get("end");
			if (random > start && random <= end) {
				FishTypeSetting fnt = (FishTypeSetting) resultMap.get("fishType");
				FishDto fd = new FishDto();
				fd.setLevel(fnt.getLevel());
				fd.setGetGoldStrart(fnt.getGold());
				fd.setGetGoldEnd(fnt.getGold());
				String[] speeds = fnt.getSpeed().split(",");
				fd.setSpeed(Double.valueOf(speeds[ThreadLocalRandom.current().nextInt(speeds.length)]));
				fd.setId(UUID.randomUUID().toString());
				fd.setRunPoint("gj" + (ThreadLocalRandom.current().nextInt(30) + 1));
				roomFish.put(fd.getId(), fd);
				addList.add(fd);
				break;
			}
		}
		if (before == roomFish.size()) {
			return;
		}
		addFish(fishTypeList, addList, roomFish, roomMaxFish);
	}
}
