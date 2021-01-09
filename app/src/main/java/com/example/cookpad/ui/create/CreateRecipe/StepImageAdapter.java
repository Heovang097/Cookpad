package com.example.cookpad.ui.create.CreateRecipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookpad.R;
import com.example.cookpad.utils.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StepImageAdapter extends RecyclerView.Adapter<StepImageAdapter.MyViewHolder> {

    private Context context;

    public StepImageAdapter(List<String> imagePaths,Context context) {
        this.imagePaths = imagePaths;
        this.context = context;
    }
    public StepImageAdapter(View.OnClickListener onClickListener, Context context)
    {
        this.context = context;
        this.mOnClickListener = onClickListener;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    List<Bitmap> bitmaps;
    List<String> imagePaths;
    List<SquareImageView> squareImageViews = new ArrayList<>();

    public List<Bitmap> getBitmaps() {
        bitmaps = new ArrayList<>();
        for (SquareImageView siv : squareImageViews)
        {
            bitmaps.add(((BitmapDrawable)siv.getDrawable()).getBitmap());
        }
        return bitmaps;
    }

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
            squareImageViews.add(holder.squareImageView);
            holder.squareImageView.setImageBitmap(bitmaps.get(position));
            holder.squareImageView.setOnClickListener(mOnClickListener);
        }
        else if (imagePaths.get(position).length() == 36) {
            holder.squareImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_step_image));
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
    protected  View.OnClickListener mOnClickListener;

    public void setOnClickListner(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
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
