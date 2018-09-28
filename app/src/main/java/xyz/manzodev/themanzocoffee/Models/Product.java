package xyz.manzodev.themanzocoffee.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    public String category,name,imgUrl;
    long price;
    public ArrayList<Optional> size , additional;

    public Product(String category, String name, String imgUrl, long price, ArrayList<Optional> size, ArrayList<Optional> additional) {
        this.category = category;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.size = size;
        this.additional = additional;
    }

    public Product() {
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public long getPrice() {
        return price;
    }

    public ArrayList<Optional> getSize() {
        return size;
    }

    public ArrayList<Optional> getAdditional() {
        return additional;
    }

    public String priceToString(long price){
        String p = Long.toString(price) + " đ";
        return p;
    }

    public static class Optional implements Serializable{
        String name;
        long price;

        public Optional(String name, long price) {
            this.name = name;
            this.price = price;
        }

        public Optional() {
        }

        public String getName() {
            return name;
        }

        public long getPrice() {
            return price;
        }

        public String convertToString(long price){
            return Long.toString(price);
        }
    }
}
