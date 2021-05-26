package com.geocdias.loadingbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.geocdias.loadingbutton.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.loadingBtn.setOnClickListener {
      binding.loadingBtn.loading()

      lifecycleScope.launch {
        delay(2_000)
        binding.loadingBtn.complete()
      }
    }
  }
}
