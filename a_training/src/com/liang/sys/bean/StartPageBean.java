package com.liang.sys.bean;

import java.io.Serializable;

public class StartPageBean implements Serializable{

	private static final long serialVersionUID = -3433216348485808819L;
	private String startPageNo;
	private String pic_url;

	public String getStartPageNo() {
		return startPageNo;
	}

	public void setStartPageNo(String startPageNo) {
		this.startPageNo = startPageNo;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}