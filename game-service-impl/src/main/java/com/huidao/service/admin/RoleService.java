package com.huidao.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.SysRole;
import com.huidao.common.entity.SysRolePermission;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IRoleService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.ICopyProperties;
import com.yehebl.orm.data.common.dto.Page;

@Component
@Service
public class RoleService implements IRoleService {

	@Autowired
	private DBDao dbDao;

	@Override
	@Transactional
	public MsgDto<String> add(SysRole role) {
		if (role == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(role.getName())) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(role);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, SysRole.class);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> update(SysRole role) {
		if (role == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(role.getName())) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(role, new ICopyProperties<SysRole>() {
			@Override
			public void copy(SysRole oldEntity, SysRole newEntity) {
				newEntity.setName(oldEntity.getName());
				newEntity.setSeq(oldEntity.getSeq());
			}
		}, SysRole.class);
		return null;
	}

	@Override
	@Transactional
	public MsgDto<String> setRolePermissions(String roleId, List<String> permissions) {
		if (StringUtils.isBlank(roleId)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteByExpression("EQ_roleId", roleId, SysRolePermission.class);
		if (permissions == null) {
			return MsgFactory.success();
		}
		List<SysRolePermission> srpList = new ArrayList<>();
		for (String permission : permissions) {
			if (StringUtils.isBlank(permission)) {
				continue;
			}
			SysRolePermission srp = new SysRolePermission();
			srp.setPermissionId(permission);
			srp.setRoleId(roleId);
			srpList.add(srp);
		}
		dbDao.save(srpList);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<Page<SysRole>> findPage(Integer page, Integer size, Map<String, Object> parms) {
		if (page == null) {
			throw ParamException.param_not_exception;
		}
		if (size == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.findPageByMap(page, size, parms, SysRole.class));
	}

	@Override
	public MsgDto<SysRole> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, SysRole.class));
	}

	@Override
	public MsgDto<String> checkName(String id, String name) {
		if (StringUtils.isBlank(name)) {
			throw ParamException.param_not_exception;
		}
		if (dbDao.checkUnique(id, "name", name, SysRole.class)) {
			return MsgFactory.failMsg("名称已存在");
		}
		return MsgFactory.success();
	}

}
