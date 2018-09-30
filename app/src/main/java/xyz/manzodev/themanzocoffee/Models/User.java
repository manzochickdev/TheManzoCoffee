package xyz.manzodev.themanzocoffee.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import java.io.Serializable;

public class User extends BaseObservable implements Serializable {
    public String name, img ,dob,phone;
    public Boolean sex;

    public User() {
    }

    public User(String name, String img, String dob, String phone, Boolean sex) {
        this.name = name;
        this.img = img;
        this.dob = dob;
        this.phone = phone;
        this.sex = sex;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getSex() {
        return sex;
    }


    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }
}
