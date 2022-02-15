package com.yuan.dyhdm.entity;



public class PieSerie {
	private double num;
	private String des;
	private boolean isClicked;
	private float startAngle;
	private float sweepAngle;
	private boolean isChild = false;
	private QuotePrice quotePrice;

	public QuotePrice getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(QuotePrice quotePrice) {
		this.quotePrice = quotePrice;
	}

	public PieSerie(double number, String description) {
		this.num = number;
		this.des = description;
	}

	public PieSerie(double number, String description, boolean isChild) {
		this.num = number;
		this.des = description;
		this.isChild = isChild;
	}

	public boolean isChild() {
		return isChild;
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public float getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(float startAngle) {
		this.startAngle = startAngle;
	}

	public float getSweepAngle() {
		return sweepAngle;
	}

	public void setSweepAngle(float sweepAngle) {
		this.sweepAngle = sweepAngle;
	}

}
