package com.example.pokemonapp.rest;

import com.example.pokemonapp.objects.Pokemon;
import com.example.pokemonapp.objects.PokemonListResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {

    @GET("pokemon/")
    Call<PokemonListResponse> getPokemonList() ;

    @GET("pokemon/{id}/")
    Call<Pokemon> getPokemon(@Path("id") String id);
}
