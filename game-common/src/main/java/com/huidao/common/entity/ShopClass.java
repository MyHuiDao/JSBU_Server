package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 商场分类
 */
@Entity("shop_class")
public class ShopClass implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 名称
	 **/
	@Column
	private String name;
	/**
	 * 排序
	 **/
	@Column
	private Integer seq;

	/**
	 * 主键
	 **/
	public String getId() {
		return this.id;
	}

	/**
	 * 主键
	 **/
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 排序
	 **/
	public Integer getSeq() {
		return this.seq;
	}

	/**
	 * 排序
	 **/
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
