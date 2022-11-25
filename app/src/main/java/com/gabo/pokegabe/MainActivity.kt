package com.gabo.pokegabe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabo.pokegabe.adapter.PokeAdapter
import com.gabo.pokegabe.model.Pokemon
import com.gabo.pokegabe.network.PokeApi
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {

    private var pokemonList: List<Pokemon>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar()?.hide();


        val recyclerView : RecyclerView = findViewById(R.id.poke_recycler)

        val inputTextSearchPokemon : TextInputEditText = findViewById(R.id.text_input_search_pokemon)

        inputTextSearchPokemon.addTextChangedListener(
            object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(Objects.nonNull(pokemonList)){
                        val filteredList = pokemonList!!
                            .filter { pokemon->
                            pokemon.name.contains(value!!)
                        }
                        recyclerView.adapter = PokeAdapter(filteredList)
                        { pokemon ->
                            callPokemonDetail(pokemon)
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        recyclerView.layoutManager = GridLayoutManager(this, 3)

        PokeApi().getPokemons{ pokemons->
            pokemonList = pokemons
            recyclerView.adapter = PokeAdapter(pokemons!!)
            { pokemon ->
                callPokemonDetail(pokemon)
            }
        }
    }

    fun callPokemonDetail(pokemon: Pokemon){
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra("pokemonUrl", pokemon.url)
        startActivity(intent)
    }
}