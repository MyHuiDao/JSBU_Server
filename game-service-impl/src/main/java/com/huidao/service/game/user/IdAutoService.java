package com.huidao.service.game.user;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huidao.common.entity.IdAuto;
import com.huidao.config.SystemConfig;
import com.yehebl.orm.DBDao;

@Service
public class IdAutoService {
	@PostConstruct
	private void init() {
		autoIdThread();
	}
	
	@Autowired	
	private DBDao dbDao;
	
	@Autowired
	private SystemConfig systemConfig;
	
	@Transactional
	public Integer getId() {
		Integer count = dbDao.getCount(IdAuto.class);
		if(systemConfig.getIdMin()>count) {
			autoIdThread();
		}
		int random=ThreadLocalRandom.current().nextInt(count);
		 IdAuto ia = dbDao.getBySql("select id from id_auto limit "+random+",1", IdAuto.class);
		 dbDao.delete(ia);
		 return ia.getId();
	}
	
	public synchronized  void autoIdThread() {
		Integer count = dbDao.getCount(IdAuto.class);
		if(systemConfig.getIdMin()<count) {
			return;
		}
		new Thread(new Runnable() {
			@Override
			public  void run() {
				autoId();
			}
		}).start();
		
	}
	
	@Transactional
	public void  autoId() {
		for (int i = 0; i < systemConfig.getIdSum(); i++) {
			IdAuto ia= new IdAuto();
			 dbDao.save(ia);
		}
	}

}
