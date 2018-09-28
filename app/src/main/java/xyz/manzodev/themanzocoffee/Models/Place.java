package xyz.manzodev.themanzocoffee.Models;

public class Place {
    String name,address,district,imgUrl;

    public Place(String name, String address, String district, String imgUrl) {
        this.name = name;
        this.address = address;
        this.district = district;
        this.imgUrl = imgUrl;
    }

    public Place() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistrict() {
        return district;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
