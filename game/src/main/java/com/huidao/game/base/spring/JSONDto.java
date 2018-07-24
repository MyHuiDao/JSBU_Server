package com.huidao.game.base.spring;

public class JSONDto {
	private Class<?>[] clazz={};

	private String [] property={};

	private String [] filterProperty={};

	public Class<?>[] getClazz() {
		return clazz;
	}

	public void setClazz(Class<?>[] clazz) {
		this.clazz = clazz;
	}

	public String[] getProperty() {
		return property;
	}

	public void setProperty(String[] property) {
		this.property = property;
	}

	public String[] getFilterProperty() {
		return filterProperty;
	}

	public void setFilterProperty(String[] filterProperty) {
		this.filterProperty = filterProperty;
	}

	
	
}
