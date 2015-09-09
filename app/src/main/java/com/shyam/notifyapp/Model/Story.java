package com.shyam.notifyapp.Model;

/**
 * Created by shyam on 2/7/15.
 */

public class Story {
    private String title, link, username;
    private int id, linuxTime, noOfUpVotes, noOfComments;
    boolean notify;

    public Story() {
    }

    public Story(String title,int id, String link, String username, int linuxTime, int noOfUpVotes, int noOfComments,
                 boolean notify) {
        this.id  = id;
        this.title = title;
        this.link = link;
        this.username = username;
        this.linuxTime = linuxTime;
        this.noOfUpVotes = noOfUpVotes;
        this.noOfComments = noOfComments;
        this.notify = notify;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLinuxTime() {
        return linuxTime;
    }

    public void setLinuxTime(int linuxTime) {
        this.linuxTime = linuxTime;
    }

    public int getNoOfUpVotes() {
        return noOfUpVotes;
    }

    public void setNoOfUpVotes(int noOfUpVotes) {
        this.noOfUpVotes = noOfUpVotes;
    }
    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public boolean getNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }


}