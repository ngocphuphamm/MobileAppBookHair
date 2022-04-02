package com.example.bookhair.Class;

public class Salon {
    public String name, address;
    public int id;
    public String image;
    public float rating;

//    public Salon(String name, String address, int image, float rating) {
//        this.name = name;
//        this.address = address;
//        this.image = image;
//        this.rating = rating;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public float getRating() {
        return rating;
    }
}
