package com.example.pokemonapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.R;
import com.example.pokemonapp.adapter.PokemonAdapter;
import com.example.pokemonapp.objects.Pokemon;
import com.example.pokemonapp.objects.PokemonListResponse;
import com.example.pokemonapp.rest.ApiGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokenomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name;
    private TextView weight;
    private TextView height;
    private ImageView image;
    private CheckBox favourite;
    private String id;
    private Pokemon currentPokemon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        name = findViewById(R.id.name);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        image = findViewById(R.id.image);

        favourite = findViewById(R.id.favourite);

        mPreferences = getBaseContext().getSharedPreferences("pokemon", 0);

        id = getIntent().getStringExtra("id");

        getPokemon(id);

        favourite.setChecked(isFavourite());

        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                List<PokemonListResponse.PokemonUrl> list = getPokemonList();

                List<PokemonListResponse.PokemonUrl> tempList = new ArrayList<>();

                if (b) {
                    tempList = list;

                    PokemonListResponse.PokemonUrl pokemon = new PokemonListResponse.PokemonUrl();
                    pokemon.setId(id);
                    pokemon.setPokemonName(currentPokemon.getName());

                    tempList.add(pokemon);
                } else {
                    for (PokemonListResponse.PokemonUrl p : list) {
                        if (!p.getId().equals(id)) {
                            tempList.add(p);
                        }
                    }
                }

                storePokemonList(tempList);
            }
        });
    }

    private boolean isFavourite() {
        List<PokemonListResponse.PokemonUrl> list = getPokemonList();

        for (PokemonListResponse.PokemonUrl p : list) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void getPokemon(String id) {
        ApiGenerator.buildService(this).getPokemon(id).enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.body() != null) {
                    currentPokemon = response.body();

                    name.setText(response.body().name);
                    weight.setText("Weight: " + response.body().weight);
                    height.setText("Height: " + response.body().height);

                    Glide.with(getBaseContext())
                            .load(response.body().getSprites().getFrontDefault())
                            .into(image);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private SharedPreferences mPreferences;
    public List<PokemonListResponse.PokemonUrl> getPokemonList() {
        String objectString = mPreferences.getString("favourite", "");
        if (TextUtils.isEmpty(objectString)) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(objectString, new TypeToken<List<PokemonListResponse.PokemonUrl>>() {
        }.getType());
    }

    public void storePokemonList(List<PokemonListResponse.PokemonUrl> pokemonList) {
        if (pokemonList != null) {
            String objectJson = new Gson().toJson(pokemonList);
            mPreferences.edit().putString("favourite", objectJson).apply();
        }
    }
}
