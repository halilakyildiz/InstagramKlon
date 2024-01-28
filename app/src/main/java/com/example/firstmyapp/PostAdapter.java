package com.example.firstmyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstmyapp.databinding.HistoryCellBinding;
import com.example.firstmyapp.databinding.PostDesignBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyPostViewHolder> {
    private Context context;
    private PostDesignBinding design;
    private List<Post> posts=new ArrayList<Post>();

    public PostAdapter(Context context,List<Post> fromSQLite) {
        this.context = context;
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.post1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.post2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(),R.drawable.post3);
        Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(),R.drawable.post4);
        Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(),R.drawable.post2);

        posts.add(new Post("User1",R.drawable.img1,bitmap1));
        posts.add(new Post("User2",R.drawable.img2,bitmap2));
        posts.add(new Post("User3",R.drawable.img3,bitmap3));
        posts.add(new Post("User4",R.drawable.img4,bitmap4));
        posts.add(new Post("User5",R.drawable.img5,bitmap5));
        for(Post post:fromSQLite ) {
            posts.add(post);
        }
    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        design = PostDesignBinding.inflate(li,parent,false);
        return new MyPostViewHolder(design);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {
        holder.profile_img.setImageResource(posts.get(position).getProfile_img());
        holder.user_text.setText(posts.get(position).getUser_text());
        holder.post_img.setImageBitmap(posts.get(position).getPost_img());

        holder.more_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(context,view);
                MenuInflater inflater=popupMenu.getMenuInflater();
                inflater.inflate(R.menu.post_popup_menu,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_sikayet:
                                Toast.makeText(context,posts.get(position).getUser_text()+" şikayet edildi",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_engelle:
                                Toast.makeText(context,posts.get(position).getUser_text()+" engellendi",Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyPostViewHolder extends RecyclerView.ViewHolder{
        public TextView user_text;
        public ImageView profile_img;
        public ImageView post_img;
        public ImageView more_icon;
        public MyPostViewHolder(PostDesignBinding binding) {
            super(binding.getRoot());
            user_text=binding.postUserText;
            post_img=binding.postImage;
            profile_img=binding.postProfile;
            more_icon=binding.postMoreIcon;
        }
    }
    public void AddNewPost(Bitmap bitmap){
        posts.add(new Post("User6",R.drawable.img4,bitmap));
    }
}
