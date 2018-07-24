package com.huidao.common.entity;

import java.io.Serializable;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 商品
 */
@Entity("goods")
public class Goods implements Serializable {
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
	 * 图片资源
	 **/
	@Column
	private String img;
	/**
	 * 商品分类id
	 **/
	@Column
	private String shopClassId;
	/**
	 * 价格
	 **/
	@Column
	private Integer price;
	/**
	 * 库存
	 **/
	@Column
	private Integer stock;
	/**
	 * 详情
	 **/
	@Column
	private String desc;
	/**
	 * 排序
	 **/
	@Column
	private Integer seq;

	/**
	 * 商品分类名称
	 */
	private String shopClassName;

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
	 * 图片资源
	 **/
	public String getImg() {
		return this.img;
	}

	/**
	 * 图片资源
	 **/
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 商品分类id
	 **/
	public String getShopClassId() {
		return this.shopClassId;
	}

	/**
	 * 商品分类id
	 **/
	public void setShopClassId(String shopClassId) {
		this.shopClassId = shopClassId;
	}

	/**
	 * 价格
	 **/
	public Integer getPrice() {
		return this.price;
	}

	/**
	 * 价格
	 **/
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * 库存
	 **/
	public Integer getStock() {
		return this.stock;
	}

	/**
	 * 库存
	 **/
	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * 详情
	 **/
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 详情
	 **/
	public void setDesc(String desc) {
		this.desc = desc;
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

	/**
	 * 商品分类名称
	 */
	public String getShopClassName() {
		return shopClassName;
	}

	/**
	 * 商品分类名称
	 */
	public void setShopClassName(String shopClassName) {
		this.shopClassName = shopClassName;
	}

}
