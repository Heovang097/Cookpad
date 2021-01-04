package com.example.cookpad.ui.create.CreateRecipe;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookpad.R;
import com.example.cookpad.ui.create.AdapterItem.ItemMethod;

import java.util.List;

public class MethodAdapter extends RecyclerView.Adapter<MethodAdapter.MyViewHolder> {

    public List<ItemMethod> items;
    public Context context;

    public MethodAdapter(List<ItemMethod> items,Context context) {
        this.items = items;
        this.context = context;
    }

    public void add(ItemMethod itemMethod)
    {
        items.add(itemMethod);
        notifyItemInserted(items.size() - 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_method, parent, false);
        return new MyViewHolder(itemView, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.number.setText(items.get(position).getNumber());
        holder.step.setText(items.get(position).getStep());
        holder.recyclerImage = new RecyclerView(context);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerImage.setLayoutManager(mLayoutManager);
        holder.recyclerImage.setItemAnimator(new DefaultItemAnimator());
        StepImageAdapter stepImageAdapter = new StepImageAdapter(items.get(position).getImagePaths());
        holder.recyclerImage.setAdapter(stepImageAdapter);
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
            items.get(position).setStep(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView number;
        public EditText step;
        RecyclerView recyclerImage;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            number = (TextView) view.findViewById(R.id.tv_number);
            step = (EditText) view.findViewById(R.id.tv_step);
            this.myCustomEditTextListener = myCustomEditTextListener;
            step.addTextChangedListener(myCustomEditTextListener);
            recyclerImage = view.findViewById(R.id.rv_step_image);
        }
    }
}
