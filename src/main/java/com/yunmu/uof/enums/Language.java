package com.yunmu.uof.enums;
/*
 * 语言枚举
 */
public enum Language {

	EN("en"),//英文
	ZH("zh"),//中文简体
	TC("tc");//中繁体

	Language(String language) {
		this.language = language;
	}

	private String language;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
}
