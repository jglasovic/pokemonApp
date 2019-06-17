package com.example.pokemonapp.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pokemonapp.R;
import com.example.pokemonapp.activities.PokenomActivity;
import com.example.pokemonapp.objects.PokemonListResponse;

import java.util.List;


public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    List<PokemonListResponse.PokemonUrl> pokemonList;

    public PokemonAdapter(List<PokemonListResponse.PokemonUrl> pokemonUrlList) {
        this.pokemonList = pokemonUrlList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PokemonViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pokemon_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PokemonViewHolder viewHolder, final int i) {
        viewHolder.setupPokemon(pokemonList.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = pokemonList.get(i).getUrl();

                if (url != null) {
                    url = url.substring(0, url.length()-1);
                    int index = url.lastIndexOf("/");

                    url = url.substring(index+1);
                } else
                    url = pokemonList.get(i).getId();

                Intent intent = new Intent(viewHolder.itemView.getContext(), PokenomActivity.class);
                intent.putExtra("id", url);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {

        private final TextView pokemonName;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonName = itemView.findViewById(R.id.pokenomName);
        }

        void setupPokemon(PokemonListResponse.PokemonUrl pokemon) {
            pokemonName.setText(pokemon.getPokemonName());
        }
    }
}
