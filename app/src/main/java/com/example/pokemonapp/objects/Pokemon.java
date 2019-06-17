package com.example.pokemonapp.objects;

import com.google.gson.annotations.SerializedName;

public class Pokemon {

    @SerializedName("height")
    public int height;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("weight")
    public int weight;

    @SerializedName("sprites")
    public Sprites sprites;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public class Sprites {

        @SerializedName("front_default")
        public String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }

        public void setFrontDefault(String frontDefault) {
            this.frontDefault = frontDefault;
        }
    }
}
