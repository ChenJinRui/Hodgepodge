package com.soul.hodgepodge.bean.crime;

import java.util.Date;
import java.util.UUID;

public
/**
 * Created by Chjr on 2020/8/13
 *
 */
class CrimeBean {

    private String mTitle = "def";
    private Date mDate;
    private UUID mID;
    private boolean mSolved;
    private String mSuspect;

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public CrimeBean() {
        this(UUID.randomUUID());
        //        mID = UUID.randomUUID();
//        date = new Date();
    }
    public CrimeBean (UUID id){
        mID = id;
        mDate = new Date();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

//    public String getID() {
//        return mID.toString();
//    }


    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public UUID getID() {
        return mID;
    }

    public String getPhotoFilename(){
        return "IMG_" + getID().toString() + ".jpg";
    }

    @Override
    public String toString() {
        return "CrimeBean{" +
                "mTitle='" + mTitle + '\'' +
                ", date=" + mDate +
                ", mID=" + mID +
                ", mSolved=" + mSolved +
                ", mSuspect=" + mSuspect +
                '}';
    }
}
