package com.example.cookpad.fragments.create.AdapterItem;

import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemMethod {
    public String step;
    public String number;
    public List<String> imagePaths;
    public List<Bitmap> bitmaps;

    public ItemMethod(String step, String number, int i) {

    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public ItemMethod(String step, String number, List<String> imagePaths) {
        this.step = step;
        this.number = number;
        this.imagePaths = imagePaths;
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
