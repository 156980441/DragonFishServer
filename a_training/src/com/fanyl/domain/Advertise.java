package com.fanyl.domain;

import java.io.Serializable;

public class Advertise implements Serializable {

	private static final long serialVersionUID = -1651028280172377550L;

	private String advertiesNo;
	private String pic_url1;
	private String pic_url2;
	private String pic_url3;

	public String getAdvertiesNo() {
		return advertiesNo;
	}

	public void setAdvertiesNo(String advertiesNo) {
		this.advertiesNo = advertiesNo;
	}

	public String getPic_url1() {
		return pic_url1;
	}

	public void setPic_url1(String pic_url1) {
		this.pic_url1 = pic_url1;
	}

	public String getPic_url2() {
		return pic_url2;
	}

	public void setPic_url2(String pic_url2) {
		this.pic_url2 = pic_url2;
	}

	public String getPic_url3() {
		return pic_url3;
	}

	public void setPic_url3(String pic_url3) {
		this.pic_url3 = pic_url3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
