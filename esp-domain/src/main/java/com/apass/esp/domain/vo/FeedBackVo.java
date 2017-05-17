package com.apass.esp.domain.vo;

import java.util.Date;

public class FeedBackVo {
	private Long id;

    private String feedbackType;

    private String comments;

    private String mobile;

    private String createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
    
}
