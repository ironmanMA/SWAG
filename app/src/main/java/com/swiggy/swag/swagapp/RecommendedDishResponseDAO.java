package com.swiggy.swag.swagapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav on 7/16/17.
 */

/**
 * Created by gaurav on 7/16/17.
 */
public class RecommendedDishResponseDAO implements Serializable, Cloneable, Parcelable {
    private static final Gson GSON = new Gson();

    @SerializedName("restaurantName")
    String restaurant;

    @SerializedName("foodImgSrc")
    String imageUrl;

    @SerializedName("foodTitle")
    String dishName;

    @SerializedName("restaurantRating")
    String restaurantRating;

    @SerializedName("restaurantReviewCount")
    String restaurantReviewCount;

    @SerializedName("restaurantDeliveryTime")
    String hotelEstimateDeliveryTime;

    @SerializedName("foodPrice")
    String dishPrice;

    @SerializedName("likenessScore")
    Double likenessScore;

    public Double getLikenessScore() {
        return likenessScore;
    }

    public void setLikenessScore(Double likenessScore) {
        this.likenessScore = likenessScore;
    }

    public RecommendedDishResponseDAO() {
    }

    protected RecommendedDishResponseDAO(Parcel in) {
        restaurant = in.readString();
        imageUrl = in.readString();
        dishName = in.readString();
        restaurantRating = in.readString();
        restaurantReviewCount = in.readString();
        hotelEstimateDeliveryTime = in.readString();
        dishPrice = in.readString();
        likenessScore=Double.parseDouble(in.readString());
    }

    public static final Creator<RecommendedDishResponseDAO> CREATOR = new Creator<RecommendedDishResponseDAO>() {
        @Override
        public RecommendedDishResponseDAO createFromParcel(Parcel in) {
            return new RecommendedDishResponseDAO(in);
        }

        @Override
        public RecommendedDishResponseDAO[] newArray(int size) {
            return new RecommendedDishResponseDAO[size];
        }
    };

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(String restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantReviewCount() {
        return restaurantReviewCount;
    }

    public void setRestaurantReviewCount(String restaurantReviewCount) {
        this.restaurantReviewCount = restaurantReviewCount;
    }

    public String getHotelEstimateDeliveryTime() {
        return hotelEstimateDeliveryTime;
    }

    public void setHotelEstimateDeliveryTime(String hotelEstimateDeliveryTime) {
        this.hotelEstimateDeliveryTime = hotelEstimateDeliveryTime;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurant);
        dest.writeString(imageUrl);
        dest.writeString(dishName);
        dest.writeString(restaurantRating);
        dest.writeString(restaurantReviewCount);
        dest.writeString(hotelEstimateDeliveryTime);
        dest.writeString(dishPrice);
        dest.writeString(likenessScore.toString());
    }
}

