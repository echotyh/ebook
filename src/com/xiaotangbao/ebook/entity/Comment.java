package com.xiaotangbao.ebook.entity;

import java.util.Date;

public class Comment {
	private String bookname;
	private String username;
	private String content;
	private String time;
	private int grade;
	

	public Comment() {
		// TODO Auto-generated constructor stub
	}
	public Comment(String bookname,String username,String content,String time,int grade){
		this.bookname = bookname;
		this.username = username;
		this.time = time;
		this.content = content;
		this.grade = grade;
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Comment [bookname=" + bookname + ", username=" + username
				+ ", content=" + content + ", time=" + time + ", grade="
				+ grade + "]";
	}

	

}
