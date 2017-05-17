package com.apass.esp.domain.entity;

import java.util.Date;

public class FeedBack {
    private Long id;

    private String feedbackType;

    private String comments;

    private String mobile;

    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}