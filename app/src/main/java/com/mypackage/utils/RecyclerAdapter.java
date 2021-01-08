package com.mypackage.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookpad.NetWork;
import com.example.cookpad.R;
import com.example.cookpad.ui.home.RecipeCard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList recipes;
    RecyclerOnItemClickListener onItemClickListenerHolder;
    public RecyclerAdapter(Context context, ArrayList recipes) {
        this.context = context;
        this.recipes = recipes;
        this.onItemClickListenerHolder = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        RecyclerAdapter.ViewHolder viewHolder = new RecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeCard card = (RecipeCard) recipes.get(position);
        holder.username.setText(card.getUsername());
        holder.recipeName.setText(card.getRecipe_name());
        holder.like.setText(String.valueOf(card.getLike_counter()));
        holder.recipeId = card.getIdRecipe();
        holder.overflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.card_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
//        Picasso.get().load("http://192.168.1.124:8012/44340?id=" + card.getIdRecipe()).fit().into(holder.recipeImage);
//        Picasso.get().load("http://192.168.1.124:8012/44341?id=" + card.getIdUser()).fit().into(holder.avatar);
        Picasso.get().load("http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44340?id=" + card.getIdRecipe()).fit().into(holder.recipeImage);
        Picasso.get().load("http://" + NetWork.getNetworkInfoHolder().getSERVER() + "/44341?id=" + card.getIdUser()).fit().into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username;
        SquareImageView recipeImage;
        TextView recipeName;
        ImageView avatar;
        ImageButton overflowButton;
        TextView like;
        String recipeId=null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeName = itemView.findViewById(R.id.recipeName);
            avatar = itemView.findViewById((R.id.avatar));
            overflowButton = itemView.findViewById((R.id.overflow_button));
            like = itemView.findViewById(R.id.likeCounter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListenerHolder != null){
                onItemClickListenerHolder.onItemClick(recipeId);
            }
        }
    }
    public void setOnItemClickListener(RecyclerOnItemClickListener onClickListener){
        this.onItemClickListenerHolder = onClickListener;
    }
    public interface RecyclerOnItemClickListener{
        public void onItemClick(String recipeId);
    }
}
