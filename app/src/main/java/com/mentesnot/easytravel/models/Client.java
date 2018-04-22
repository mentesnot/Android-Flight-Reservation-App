/*************************************************************************************************
 * JANUARY 8, 2018
 * Mentesnot Aboset
  ************************************************************************************************/
package com.mentesnot.easytravel.models;


import android.graphics.Bitmap;

public class Client {

    private String clientID;
    private String firstName;
    private String lastName;
    private String phone;
    private String creditCard;
    private Bitmap image;

    public Client(){

    }

    public Client(String firstName, String lastName, String phone, String creditCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.creditCard = creditCard;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


}
