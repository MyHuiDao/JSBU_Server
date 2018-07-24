package com.huidao.common.enums;


/**
 * 鱼类型
 * @author lenovo
 *
 */
public enum FishType {
	/**
	 * 小鱼
	 */
	xiaoyu("xiaoyu","小鱼",2,2,5,"1,2,3",62.0),
	/**
	 * 小丑鱼
	 */
	xiaochouyu("xiaochouyu","小丑鱼",4,4,10,"1,2,3",230.0),
	
	/**
	 * 金鱼
	 */
	jinyu("jinyu","金鱼",6,6,15,"1,2,3",350.0),
	
	/**
	 * 河豚
	 */
	hetun("hetun","河豚",10,10,20,"1,2,3",590.0),
	
	/**
	 * 乌贼
	 */
	wuzei("wuzei","乌贼",20,20,35,"1,2,3",1300.0),
	
	/**
	 * 灯笼鱼
	 */
	denglongyu("denglongyu","灯笼鱼",20,20,25,"1,2,3",550.0),
	
	/**
	 * 金枪鱼
	 */
	jinqiangyu("jinqiangyu","金枪鱼",40,40,30,"1,2,3",1750.0),
	
	/**
	 * 三文鱼
	 */
	sanwenyu("sanwenyu","三文鱼",60,60,35,"1,2,3",2050.0),
	
	/**
	 * 乌龟
	 */
	wugui("wugui","乌龟",80,80,40,"1,2,3",2500.0),
	
	/**
	 * 螃蟹
	 */
	pangxie("pangxie","螃蟹",100,100,45,"1,2,3",2870.0),
	
	/**
	 * 电鳐
	 */
	dianyao("dianyao","电鳐",120,120,50,"1,2,3",3280.0),
	
	/**
	 * 水母
	 */
	shuimu("shuimu","水母",140,140,55,"1,2,3",3650.0),
	
	
	/**
	 * 金蟾蜍
	 */
	jinchanchu("jinchanchu","金蟾蜍",160,160,70,"1,2,3",4690.0),
	
	/**
	 * 金乌龟
	 */
	jinwugui("jinwugui","金乌龟",180,180,80,"1,2,3",5000.0),
	
	/**
	 * 金电鳐
	 */
	jindianyao("jindianyao","金电鳐",200,200,90,"1,2,3",6100.0),
	
	
	/**
	 * 大鲨鱼
	 */
	qicaishayu("qicaishayu","七彩鲨鱼",400,400,95,"1,2,3",6550.0);
	
	
	private FishType(String type,String name,int goldMin,int goldMax,int power,String speed,Double contrast) {
		this.type=type;
		this.name=name;
		this.goldMin=goldMin;
		this.goldMax=goldMax;
		this.power=power;
		this.speed=speed;
		this.contrast=contrast;
	}
	
	
	
	/**
	 * 速度
	 */
	private String speed;
	
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 获取金币开始区间
	 */
	private int goldMin;
	
	/**
	 * 获取金币结束区间
	 */
	private int goldMax;
	
	/**
	 * 鱼权值
	 */
	private int power;
	
	
	/**
	 * 捕获对比参数
	 */
	private Double contrast;
	
	/**
	 * 类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 鱼权值
	 */
	public int getPower() {
		return power;
	}
	/**
	 * 鱼权值
	 */
	public void setPower(int power) {
		this.power = power;
	}
	
	/**
	 * 最小金币值区间
	 */
	public int getGoldMin() {
		return goldMin;
	}
	
	
	/**
	 * 最小金币值区间
	 */
	public void setGoldMin(int goldMin) {
		this.goldMin = goldMin;
	}
	
	
	/**
	 * 最大金币值区间
	 */
	public int getGoldMax() {
		return goldMax;
	}
	
	/**
	 * 最大金币值区间
	 */
	public void setGoldMax(int goldMax) {
		this.goldMax = goldMax;
	}
	
	/**
	 * 速度
	 * @return
	 */
	public String getSpeed() {
		return speed;
	}
	
	/**
	 * 速度
	 * @param speed
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	
	/**
	 * 捕获对比参数
	 * @return
	 */
	public Double getContrast() {
		return contrast;
	}
	
	/**
	 * 捕获对比参数
	 * @param contrast
	 */
	public void setContrast(Double contrast) {
		this.contrast = contrast;
	}
	
	
	
	
	
	
	
	
}
