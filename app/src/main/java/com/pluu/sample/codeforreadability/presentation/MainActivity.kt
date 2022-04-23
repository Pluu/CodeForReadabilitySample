package com.pluu.sample.codeforreadability.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pluu.sample.codeforreadability.data.SavingRepositoryImpl
import com.pluu.sample.codeforreadability.databinding.ActivityMainBinding
import com.pluu.sample.codeforreadability.provider.SampleItemGeneratorImpl
import com.pluu.sample.codeforreadability.utils.dp

class MainActivity : AppCompatActivity() {

    private lateinit var sampleAdapter: SampleAdapter

    private lateinit var binding: ActivityMainBinding

    // FIXED 3. use ViewModel
    private val viewModel by lazy {
        // FIXED 6. provide generator
        // FIXED 7. provide saver
        SearchViewModel(
            generator = SampleItemGeneratorImpl(),
            savingRepository = SavingRepositoryImpl(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setUpObservers()
    }

    private fun setUpViews() {
        sampleAdapter = SampleAdapter(
            // FIXED 7. handle of ViewModel
            onFavorite = viewModel::updateFavorite
        )

        binding.btnGenerate.setOnClickListener {
            viewModel.generate()
        }
        binding.btnClear.setOnClickListener {
            viewModel.reset()
        }
        with(binding.recyclerView) {
            adapter = sampleAdapter
            addItemDecoration(SampleItemDecoration(4.dp))
        }
    }

    // FIXED 4. observe ViewModel
    private fun setUpObservers() {
        viewModel.items.observe(this) { list ->
            sampleAdapter.submitList(list)
            binding.recyclerView.scrollToPosition(0)
        }
        viewModel.messageEvent.observe(this) { msg ->
            Toast.makeText(this, "Duplicate item : $msg", Toast.LENGTH_SHORT).show()
        }
    }
}