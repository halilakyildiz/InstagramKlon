package com.example.firstmyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstmyapp.databinding.ActivityMainBinding;
import com.example.firstmyapp.databinding.HistoryCellBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.MyViewHolder> {
    private HistoryCellBinding design;
    private Context context;
    private List<History> histories = new ArrayList<History>();
    public HeaderAdapter(Context context){
        this.context=context;

        histories.add(new History(R.drawable.img1,"Hikaye 1"));
        histories.add(new History(R.drawable.img2,"Hikaye 2"));
        histories.add(new History(R.drawable.img3,"Hikaye 3"));
        histories.add(new History(R.drawable.img4,"Hikaye 4"));
        histories.add(new History(R.drawable.img5,"Hikaye 5"));
        histories.add(new History(R.drawable.img6,"Hikaye 6"));
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        design = HistoryCellBinding.inflate(li,parent,false);
        return new MyViewHolder(design);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img.setImageResource(histories.get(position).getImg());
        holder.text.setText(histories.get(position).getInfo());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Tıklandı :"+histories.get(position).getInfo(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView text;
        public MyViewHolder(HistoryCellBinding binding){
            super(binding.getRoot());
            img = binding.imageView;
            text=binding.historyText;
        }
    }
}
