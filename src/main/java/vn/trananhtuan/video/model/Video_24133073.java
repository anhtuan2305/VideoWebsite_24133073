package vn.trananhtuan.video.model;

import java.io.Serializable;

public class Video_24133073 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String videoId;
    private String title;
    private String poster;
    private int views;
    private String description;
    private boolean active;
    private int categoryId;

    // Dùng để hiển thị tên thể loại khi JOIN database
    private String categoryName;

    public Video_24133073() {
    }

    public Video_24133073(
            String videoId,
            String title,
            String poster,
            int views,
            String description,
            boolean active,
            int categoryId) {

        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.categoryId = categoryId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Video_24133073{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", views=" + views +
                ", active=" + active +
                ", categoryId=" + categoryId +
                '}';
    }
}