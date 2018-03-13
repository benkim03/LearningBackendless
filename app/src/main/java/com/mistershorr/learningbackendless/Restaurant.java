package com.mistershorr.learningbackendless;

/**
 * Created by per6 on 2/22/18.
 */

public class Restaurant {
    private String name, genre, address, ownerId, objectId;
    private int rating, price;

    //need getters and setters for backendless to work
    //need an empty default constructor for backendless to work


    public Restaurant(String name, String genre, String address, int rating, int price) {
        this.name = name;
        this.genre = genre;
        this.address = address;
        this.rating = rating;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", address='" + address + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", objectId='" + objectId + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }

    public Restaurant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
