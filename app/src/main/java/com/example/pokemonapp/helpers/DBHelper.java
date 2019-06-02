package com.example.pokemonapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.pokemonapp.objects.Pokemon;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pokemon.db";

    public static final String TABLE_NAME = "favourite_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POKEMON_ID = "pokemonId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMG_URL= "imgUrl";
    public static final String COLUMN_TYPE = "type";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_POKEMON_ID + " TEXT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_IMG_URL + " TEXT," +
                    COLUMN_TYPE +" TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SQL_SELECT_ALL_POKEMON = "SELECT * FROM " + TABLE_NAME;

    private static final String SQL_SELECT_POKEMON_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_POKEMON_ID + " = ?";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean addPokemon(Pokemon pokemon){

        if(isPokemonInDB(pokemon))
            return true;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_POKEMON_ID, pokemon.id);
        values.put(COLUMN_NAME, pokemon.name);
        values.put(COLUMN_IMG_URL, pokemon.imgUrl);
        values.put(COLUMN_TYPE, pokemon.type);

        return db.insert(TABLE_NAME, null, values) > 0;
    }

    public boolean isPokemonInDB(Pokemon pokemon){

        long isInDB = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = {String.valueOf(pokemon.id)};

        Cursor c = db.rawQuery(SQL_SELECT_POKEMON_BY_ID, whereArgs);

        if(c.moveToFirst())
            isInDB =  c.getInt(c.getColumnIndex(COLUMN_ID));

        c.close();

        return isInDB > 0;
    }

    public List<Pokemon> getAllPokemons(){

        List<Pokemon> pokemonList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(SQL_SELECT_ALL_POKEMON, null);

        if(c.moveToFirst()){
            do{
                pokemonList.add(getPokemonFromCursor(c));
            } while(c.moveToNext());
        }

        c.close();

        return pokemonList;
    }

    private Pokemon getPokemonFromCursor(Cursor c) {
        Pokemon newPokemon = new Pokemon();
        newPokemon.id = c.getString(c.getColumnIndex(COLUMN_POKEMON_ID));
        newPokemon.imgUrl = c.getString(c.getColumnIndex(COLUMN_IMG_URL));
        newPokemon.name = c.getString(c.getColumnIndex(COLUMN_NAME));
        newPokemon.type = c.getString(c.getColumnIndex(COLUMN_TYPE));

        return newPokemon;
    }

    public boolean deletePokemon(Pokemon p){

        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = {String.valueOf(p.id)};

        return db.delete(TABLE_NAME, COLUMN_POKEMON_ID + " = ?", whereArgs) > 0;
    }
}
