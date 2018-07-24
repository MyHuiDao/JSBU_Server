package com.huidao.common.entity;

import java.io.Serializable;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * id 生成器
 */
@Entity("id_auto")
public class IdAuto implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * id
	 **/
	@Id(IdType.none)
	@Column
	private Integer id;

	/**
	 * id
	 **/
	public Integer getId() {
		return this.id;
	}

	/**
	 * id
	 **/
	public void setId(Integer id) {
		this.id = id;
	}
}