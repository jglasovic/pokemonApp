package com.example.pokemonapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.PokemonAdapter;
import com.example.pokemonapp.objects.PokemonListResponse;
import com.example.pokemonapp.rest.ApiGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private PokemonAdapter mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPokemons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favourite) {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void getPokemons() {
        ApiGenerator.buildService(this).getPokemonList().enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                assert response.body() != null;
                mAdapter = new PokemonAdapter(response.body().getPokemonUrlList());

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {

            }
        });
    }


}
