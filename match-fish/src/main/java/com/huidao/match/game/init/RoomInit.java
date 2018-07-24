package com.huidao.match.game.init;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.huidao.common.cache.redis.RedisCache;
import com.huidao.match.game.dto.FreeRoomDto;

@Component
public class RoomInit {
	private static final String raceFish_free_room = "raceFish_free_room_id";

	@PostConstruct
	public void initRoom() {
		FreeRoomDto frd = RedisCache.get(raceFish_free_room, FreeRoomDto.class);
		if (frd != null) {
			return;
		}
		frd = new FreeRoomDto();
		RedisCache.set(raceFish_free_room, frd);
	}
}
