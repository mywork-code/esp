package com.apass.esp.third.party.jd.entity.base;

import java.io.Serializable;

/**
 * @author xianzhi.wang
 * @time
 */
public class Assist implements Serializable {

	private int id;
	private String question;
	private String answer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
