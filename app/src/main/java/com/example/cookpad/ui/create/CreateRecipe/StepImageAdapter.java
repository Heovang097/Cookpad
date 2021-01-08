package com.example.cookpad.ui.create.CreateRecipe;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookpad.R;
import com.example.cookpad.utils.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.BitSet;
import java.util.List;

public class StepImageAdapter extends RecyclerView.Adapter<StepImageAdapter.MyViewHolder> {

    public StepImageAdapter(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
    public StepImageAdapter()
    {

    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    List<Bitmap> bitmaps;
    List<String> imagePaths;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step_image, parent, false);
        return new MyViewHolder(itemView);
    }
    View v;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(imagePaths == null)
        {
            holder.squareImageView.setImageBitmap(bitmaps.get(position));
        }
        else
            Picasso.get().load(imagePaths.get(position)).fit().into(holder.squareImageView);
    }

    @Override
    public int getItemCount() {
        if(imagePaths != null)
            return imagePaths.size();
        return bitmaps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SquareImageView squareImageView;
        public MyViewHolder(@NonNull View view) {
            super(view);
            v = view;
            squareImageView = view.findViewById(R.id.siv_step);
        }
    }
}
