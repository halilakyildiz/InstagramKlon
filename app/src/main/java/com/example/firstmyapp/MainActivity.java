package com.example.firstmyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.firstmyapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding design;
    Database database;
    PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        design = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(design.getRoot());
        database = new Database(this);
        design.toolbar.setTitle("MyFirstApp");
        design.toolbar.setLogo(R.drawable.android_icon);
        setSupportActionBar(design.toolbar);

        List<Post> posts = database.GetPosts();
        HeaderAdapter adapter = new HeaderAdapter(this);
        design.rvHeader.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        design.rvHeader.setLayoutManager(layoutManager);
        //design.rvHeader.setLayoutManager(new GridLayoutManager(this,1));
        design.rvHeader.setAdapter(adapter);

        design.rvBody.setLayoutManager(new LinearLayoutManager(this));
        postAdapter=new PostAdapter(this,posts);
        design.rvBody.setAdapter(postAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add_post:
                Intent intent = new Intent(this,AddPost.class);
                startActivity(intent);
                finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
}