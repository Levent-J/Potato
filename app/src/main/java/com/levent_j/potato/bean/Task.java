package com.levent_j.potato.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by levent_j on 16-5-19.
 */
public class Task implements Parcelable{

    private String title;
    private String content;
    private int color;
    private int Study;
    private int Review;
    private int Rest;

    public Task(){};

    protected Task(Parcel in) {
        title = in.readString();
        content = in.readString();
        color = in.readInt();
        Study = in.readInt();
        Review = in.readInt();
        Rest = in.readInt();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return content;
    }

    public void setMessage(String message) {
        this.content = message;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(color);
        dest.writeInt(Study);
        dest.writeInt(Review);
        dest.writeInt(Rest);
    }
}
