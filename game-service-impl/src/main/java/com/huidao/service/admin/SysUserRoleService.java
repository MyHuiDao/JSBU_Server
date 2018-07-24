package com.huidao.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.SysUserRole;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.ISysUserRoleService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class SysUserRoleService implements ISysUserRoleService {

	@Autowired
	private DBDao dbDao;

	/**
	 * 查询用户角色信息
	 */
	@Override
	public List<SysUserRole> getSysUserRole(String sysUserId) {
		if (StringUtils.isBlank(sysUserId)) {
			throw ParamException.param_not_exception;
		}
		QueryXmlSql qx = new QueryXmlSql();
		qx.addParams("sysUserId", sysUserId);
		return dbDao.findBySqlName("findSysUserRole", qx, SysUserRole.class);
	}

	/**
	 * 保存用户id和角色id
	 */
	@Override
	@Transactional
	public MsgDto<String> add(SysUserRole sysUserRole) {
		// 1.判断相关参数是否为空
		if (sysUserRole == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sysUserRole.getRoleId())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sysUserRole.getSysUserId())) {
			throw ParamException.param_not_exception;
		}
		// 2.校验该用户是否已有相关角色
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("EQ_sysUserId", sysUserRole.getSysUserId());
		maps.put("EQ_roleId", sysUserRole.getRoleId());
		SysUserRole sysUserRole2 = dbDao.getByMap(maps, SysUserRole.class);
		if (sysUserRole2 != null) {
			return MsgFactory.failMsg("已配置相关角色");
		}
		dbDao.save(sysUserRole);
		SysUser sysUser = dbDao.get(sysUserRole.getSysUserId(), SysUser.class);
		if (sysUserRole.getName().equals("agen")) {
			sysUser.setType(sysUserRole.getName());
		} else {
			sysUser.setType("admin");
		}
		dbDao.update(sysUser);
		return MsgFactory.success();
	}

	/**
	 * 删除用户角色信息
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, SysUserRole.class);
		return MsgFactory.success();
	}

}
