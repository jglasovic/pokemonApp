package com.example.pokemonapp.objects;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PokemonListResponse {

    @SerializedName("results")
    public List<PokemonUrl> pokemonUrlList;

    public List<PokemonUrl> getPokemonUrlList() {
        return pokemonUrlList;
    }

    public void setPokemonUrlList(List<PokemonUrl> pokemonUrlList) {
        this.pokemonUrlList = pokemonUrlList;
    }

    public static class PokemonUrl {

        @SerializedName("name")
        String pokemonName;

        @SerializedName("url")
        String url;

        String id;

        public String getPokemonName() {
            return pokemonName;
        }

        public void setPokemonName(String pokemonName) {
            this.pokemonName = pokemonName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
