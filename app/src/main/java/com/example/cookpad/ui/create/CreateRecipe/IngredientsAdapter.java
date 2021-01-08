package com.example.cookpad.ui.create.CreateRecipe;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookpad.R;
import com.example.cookpad.ui.create.AdapterItem.ItemIngredients;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {

    List<ItemIngredients> items;
    private Context context;

    public void add(ItemIngredients itemIngredients) {
        items.add(itemIngredients);
        notifyItemInserted(items.size() - 1);
    }

    public JSONArray getIngredientsName()
    {
        JSONArray ret= new JSONArray();
        for(int i =0;i<items.size();i++)
        {
            ret.put(items.get(i).name);
        }
        return ret;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public EditText name;
        public ImageButton button;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            name = (EditText) view.findViewById(R.id.tv_name);
            this.myCustomEditTextListener = myCustomEditTextListener;
            name.addTextChangedListener(myCustomEditTextListener);
        }
    }

    public IngredientsAdapter(List<ItemIngredients> items,Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredients, parent, false);
        return new MyViewHolder(itemView, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.name.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            items.get(position).setName(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
