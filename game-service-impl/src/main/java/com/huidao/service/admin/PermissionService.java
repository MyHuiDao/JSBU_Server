package com.huidao.service.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.SysPermission;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IPermissionService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.ICopyProperties;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class PermissionService implements IPermissionService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<List<SysPermission>> findAll() {
		return MsgFactory.success(dbDao.findByExpression("ORDER_seq", "asc", SysPermission.class));
	}

	@Override
	@Transactional
	public MsgDto<String> update(SysPermission sp) {
		if (sp == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sp.getName())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sp.getCode())) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(sp, new ICopyProperties<SysPermission>() {
			@Override
			public void copy(SysPermission oldEntity, SysPermission newEntity) {
				newEntity.setCode(oldEntity.getCode());
				newEntity.setName(oldEntity.getName());
				newEntity.setParentId(oldEntity.getParentId());
				newEntity.setSeq(oldEntity.getSeq());
			}
		}, SysPermission.class);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> add(SysPermission sp) {
		if (sp == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sp.getName())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sp.getCode())) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(sp);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, SysPermission.class);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<SysPermission> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, SysPermission.class));
	}

	/**
	 * 通过用户id获取权限Code
	 */
	@Override
	public List<SysPermission> getPermissionCode(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		QueryXmlSql qxl = new QueryXmlSql();
		qxl.addParams("userId", userId);
		return dbDao.findBySqlName("getPermissionCode", qxl, SysPermission.class);
	}

	@Override
	public MsgDto<List<SysPermission>> getPermissionCodeByRoleId(String roleId) {
		if (StringUtils.isBlank(roleId)) {
			throw ParamException.param_not_exception;
		}
		QueryXmlSql qxl = new QueryXmlSql();
		qxl.addParams("roleId", roleId);
		return MsgFactory.success(dbDao.findBySqlName("getPermissionCodeByRoleId", qxl, SysPermission.class));
	}

}
