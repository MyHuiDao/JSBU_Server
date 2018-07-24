package com.huidao.config;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.IDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;
import com.yehebl.orm.exception.ParamsException;

public class BaseService<T, PK> {

	@SuppressWarnings("unchecked")
	public BaseService() {
		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	private IDao dao;

	private Class<T> clazz;

	@Transactional
	public MsgDto<String> save(T t) {
		int result = dao.save(t);
		if (result == 0) {
			return MsgFactory.failMsg("保存失败");
		}
		return MsgFactory.successMsg("保存成功");
	}

	@RequestMapping("/delete")
	@Transactional
	public MsgDto<String> delete(PK id) {
		if (id == null) {
			throw new ParamsException(clazz.getName() + "删除失败,主键参数不能为空");
		}
		int result = dao.deleteById(id, clazz);
		if (result == 0) {
			return MsgFactory.failMsg("删除失败");
		}
		return MsgFactory.successMsg("删除成功");
	}

	public MsgDto<T> get(PK id) {
		if (id == null) {
			throw new ParamsException(clazz.getName() + "获取数据失败,主键参数不能为空");
		}
		T t = dao.get(id, clazz);
		if (t == null) {
			return MsgFactory.failMsg("获取数据失败");
		}
		return MsgFactory.success(t);
	}

	public MsgDto<List<T>> findAll() {
		return MsgFactory.success(dao.findAll(clazz));
	}

	public MsgDto<Page<T>> findPage(Map<String, Object> parms) {
		return MsgFactory
				.success(dao.findPageByMap(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), parms, clazz));
	}

	public MsgDto<List<T>> find(Map<String, Object> parms) {
		return MsgFactory.success(dao.findByMap(parms, clazz));
	}

	@Transactional
	public MsgDto<String> update(T t) {
		int result = dao.update(t);
		if (result == 0) {
			return MsgFactory.failMsg("更新失败");
		}
		return MsgFactory.successMsg("更新成功");
	}
}
