package com.levent_j.potato.bean;

/**
 * Created by levent_j on 16-5-19.
 */
public class Task {
    private String title;
    private String message;
    private int color;
    private int Study;
    private int Review;
    private int Rest;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStudy() {
        return Study;
    }

    public void setStudy(int study) {
        Study = study;
    }

    public int getReview() {
        return Review;
    }

    public void setReview(int review) {
        Review = review;
    }

    public int getRest() {
        return Rest;
    }

    public void setRest(int rest) {
        Rest = rest;
    }
}
