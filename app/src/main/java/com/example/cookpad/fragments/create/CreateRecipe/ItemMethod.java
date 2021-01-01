package com.example.cookpad.fragments.create.CreateRecipe;

public class ItemMethod {
    public String step;
    public String number;

    public int getImage() {
        return image;
    }


    public void setImage(int image) {
        this.image = image;
    }

    public int image;
    public ItemMethod(String step,String number,int image)
    {
        this.step = step;
        this.number = number;
        this.image = image;
    }


    public void setStep(String step) {
        this.step = step;
    }

    public String getStep() {
        return step;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

}
