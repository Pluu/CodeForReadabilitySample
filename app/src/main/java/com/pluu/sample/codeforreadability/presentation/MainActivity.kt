package com.pluu.sample.codeforreadability.presentation

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.pluu.sample.codeforreadability.databinding.ActivityMainBinding
import com.pluu.sample.codeforreadability.model.SampleItem
import com.pluu.sample.codeforreadability.utils.dp

class MainActivity : AppCompatActivity() {

    private lateinit var sampleAdapter: SampleAdapter

    private lateinit var binding: ActivityMainBinding

    private val preferences: SharedPreferences by lazy {
        getSharedPreferences("sample", Context.MODE_PRIVATE)
    }

    // FIXED 3. use ViewModel
    private val viewModel by lazy { SearchViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }

    private fun setUpViews() {
        sampleAdapter = SampleAdapter(
            onFavorite = {
                preferences.edit {
                    putString("KEY", it)
                }
                sampleAdapter.updateFavorite(it)
                sampleAdapter.notifyDataSetChanged()
            },
            onDuplicate = { item ->
                Toast.makeText(this, "Duplicate item : ${item.text}", Toast.LENGTH_SHORT).show()
            }
        )

        binding.btnGenerate.setOnClickListener {
            generate()
        }
        binding.btnClear.setOnClickListener {
            reset()
        }
        with(binding.recyclerView) {
            adapter = sampleAdapter
            addItemDecoration(SampleItemDecoration(4.dp))
        }

        sampleAdapter.updateFavorite(preferences.getString("KEY", null).orEmpty())
    }

    private fun generate() {
        val item = SampleItem(
            text = ('a' + (0 until 26).random()).toString(),
            bgColor = Color.rgb(
                (0..255).random(),
                (0..255).random(),
                (0..255).random()
            )
        )

        sampleAdapter.addItem(item)
    }

    private fun reset() {
        // FIXED 3. move network
        viewModel.sendLog()
        sampleAdapter.reset()
        binding.recyclerView.scrollToPosition(0)
    }
}