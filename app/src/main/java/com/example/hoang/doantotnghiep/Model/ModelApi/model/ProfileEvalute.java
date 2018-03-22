package com.example.hoang.doantotnghiep.Model.ModelApi.model;

/**
 * Created by Admin on 1/30/2018.
 */

public class ProfileEvalute {
    private String address;
    private int age;
    private int gender;
    private int isMarrige;
    private int careChildAlone;
    private float finaceHealthy;

    public ProfileEvalute(String address, int age, int gender, int isMarrige, int careChildAlone, float finaceHealthy) {
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.isMarrige = isMarrige;
        this.careChildAlone = careChildAlone;
        this.finaceHealthy = finaceHealthy;
    }

    public ProfileEvalute(float finaceHealthy) {
        this.finaceHealthy = finaceHealthy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getIsMarrige() {
        return isMarrige;
    }

    public void setIsMarrige(int isMarrige) {
        this.isMarrige = isMarrige;
    }

    public int getCareChildAlone() {
        return careChildAlone;
    }

    public void setCareChildAlone(int careChildAlone) {
        this.careChildAlone = careChildAlone;
    }

    public float getFinaceHealthy() {
        return finaceHealthy;
    }

    public void setFinaceHealthy(float finaceHealthy) {
        this.finaceHealthy = finaceHealthy;
    }
}
