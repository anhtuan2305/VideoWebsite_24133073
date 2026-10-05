package vn.trananhtuan.video.model;

import java.io.Serializable;

public class CartItem_24133073 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String videoId;
    private String title;
    private String poster;
    private double price;
    private int quantity;

    public CartItem_24133073() {
    }

    public CartItem_24133073(String videoId, String title, String poster, double price, int quantity) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.price = price;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return this.price * this.quantity;
    }

    @Override
    public String toString() {
        return "CartItem_24133073{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}