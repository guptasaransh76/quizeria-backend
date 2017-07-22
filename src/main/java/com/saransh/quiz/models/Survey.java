package com.saransh.quiz.models;

import java.util.List;

public class Survey {
	
	private Register user;
	
	private List<Question> questions;

	public Register getUser() {
		return user;
	}

	public void setUser(Register user) {
		this.user = user;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	
}
