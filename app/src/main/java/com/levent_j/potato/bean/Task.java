package com.levent_j.potato.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;


/**
 * Created by levent_j on 16-5-19.
 */
public class Task extends SugarRecord implements Parcelable{

    private String title;
    private String content;
    private int color;
    private double Study;
    private double Review;
    private double Rest;

    public Task(){};

    public Task(String title, String content, int color, double study, double review, double rest) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.Study = study;
        this.Review = review;
        this.Rest = rest;
    }

    protected Task(Parcel in) {
        title = in.readString();
        content = in.readString();
        color = in.readInt();
        Study = in.readDouble();
        Review = in.readDouble();
        Rest = in.readDouble();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getStudy() {
        return Study;
    }

    public void setStudy(double study) {
        Study = study;
    }

    public double getReview() {
        return Review;
    }

    public void setReview(double review) {
        Review = review;
    }

    public double getRest() {
        return Rest;
    }

    public void setRest(double rest) {
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
        dest.writeDouble(Study);
        dest.writeDouble(Review);
        dest.writeDouble(Rest);
    }
}
