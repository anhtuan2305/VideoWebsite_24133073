package vn.trananhtuan.video.model;

import java.io.Serializable;
import java.sql.Date;

public class Favorite_24133073 implements Serializable {

    private static final long serialVersionUID = 1L;

    private int favoriteId;
    private Date likedDate;
    private String videoId;
    private String username;

    public Favorite_24133073() {
    }

    public Favorite_24133073(
            int favoriteId,
            Date likedDate,
            String videoId,
            String username) {

        this.favoriteId = favoriteId;
        this.likedDate = likedDate;
        this.videoId = videoId;
        this.username = username;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Date getLikedDate() {
        return likedDate;
    }

    public void setLikedDate(Date likedDate) {
        this.likedDate = likedDate;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Favorite_24133073{" +
                "favoriteId=" + favoriteId +
                ", likedDate=" + likedDate +
                ", videoId='" + videoId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}