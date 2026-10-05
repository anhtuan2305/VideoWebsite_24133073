package vn.trananhtuan.video.model;

import java.io.Serializable;
import java.sql.Date;

public class Share_24133073 implements Serializable {

    private static final long serialVersionUID = 1L;

    private int shareId;
    private String emails;
    private Date sharedDate;
    private String username;
    private String videoId;

    public Share_24133073() {
    }

    public Share_24133073(
            int shareId,
            String emails,
            Date sharedDate,
            String username,
            String videoId) {

        this.shareId = shareId;
        this.emails = emails;
        this.sharedDate = sharedDate;
        this.username = username;
        this.videoId = videoId;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Date getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "Share_24133073{" +
                "shareId=" + shareId +
                ", emails='" + emails + '\'' +
                ", sharedDate=" + sharedDate +
                ", username='" + username + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}