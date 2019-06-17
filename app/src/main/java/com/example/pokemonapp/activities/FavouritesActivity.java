package com.example.pokemonapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.PokemonAdapter;
import com.example.pokemonapp.objects.PokemonListResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private PokemonAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void getPokemons() {
        List<PokemonListResponse.PokemonUrl> list = getList();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mAdapter = new PokemonAdapter(list);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<PokemonListResponse.PokemonUrl> getList() {
        SharedPreferences mPreferences = getBaseContext().getSharedPreferences("pokemon", 0);
        String objectString = mPreferences.getString("favourite", "");
        if (TextUtils.isEmpty(objectString)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(objectString, new TypeToken<List<PokemonListResponse.PokemonUrl>>() {
        }.getType());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getPokemons();
    }
}
