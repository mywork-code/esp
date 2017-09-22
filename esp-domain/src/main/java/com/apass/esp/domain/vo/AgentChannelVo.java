package com.apass.esp.domain.vo;

public class AgentChannelVo {

	private String agent;
	
	private Long userId;
	
	/**
	 * 渠道来源
	 */
	private String sceneDesc;

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSceneDesc() {
		return sceneDesc;
	}

	public void setSceneDesc(String sceneDesc) {
		this.sceneDesc = sceneDesc;
	}
}
