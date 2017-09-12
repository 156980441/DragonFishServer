package com.fanyl.domain;

import java.sql.Date;

/**
 * 分页操作的pojo
 * @author quanwei
 * @date 2017年1月13日
 */
public class Page {
	// 页码，默认第一页
    public int pageNum = 1;
    // 页面个数 默认10
    public int numPerPage = 10;
    // 总条目
    public int totalCount = 0;
    // 排序字段
    public String orderField;
    // 排序顺序
    public String orderDirection;
    // 关键字
    public String keywords;
    // 查询状态
    public String status;
    // 查询类型
    public String type;
    // 查询开始时间
    public Date startDate;
    // 查询结束时间
    public Date endDate;
    
    /**
     * 获取开始的索引位置
     * @return
     */
    public int getStartIndex() {
        int pageNum = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
        return pageNum * this.getNumPerPage();
    }

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
