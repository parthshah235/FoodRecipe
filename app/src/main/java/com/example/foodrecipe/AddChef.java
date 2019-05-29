package com.example.foodrecipe;

public class AddChef
{
    String chefName,chefMail,chefPass ;
    String chefDob, chefId , chefGender;

    public AddChef()
    {

    }

    public AddChef(String chefId, String chefName, String chefMail, String chefPass,  String chefDob, String chefGender)
    {
        this.chefId = chefId;
        this.chefName = chefName;
        this.chefMail = chefMail;
        this.chefPass = chefPass;
        this.chefGender = chefGender;
        this.chefDob = chefDob;
    }

    public String getChefName() {
        return chefName;
    }

    public String getChefMail() {
        return chefMail;
    }

    public String getChefPass() {
        return chefPass;
    }

    public String getChefId() {
        return chefId;
    }
public String getChefGender() {
        return chefGender;
    }

    public String getChefDob() {
        return chefDob;
    }
}
