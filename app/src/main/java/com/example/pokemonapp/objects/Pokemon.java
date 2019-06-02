package com.example.pokemonapp.objects;

public class Pokemon {
    public String id;
    public String name;
    public String imgUrl;
    public String type;
    public void constructor(String id, String name, String imgUrl, String type){
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.type = type;
    }
}
