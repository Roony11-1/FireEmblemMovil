package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.PullResponse
import com.patitofeliz.fireemblem.databinding.ActivityPullBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.PrincipalViewModel
import com.patitofeliz.fireemblem.presentation.viewmodel.PullViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityPullBinding;
    private val viewModel: PullViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPullBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPullBanner.setOnClickListener {

            viewModel.pull(this, 1)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}