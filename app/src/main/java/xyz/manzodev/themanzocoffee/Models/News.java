package xyz.manzodev.themanzocoffee.Models;

public class News {
    private String title,imgUrl,postUrl;

    public News(String title, String imgUrl, String postUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.postUrl = postUrl;
    }

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }
}
