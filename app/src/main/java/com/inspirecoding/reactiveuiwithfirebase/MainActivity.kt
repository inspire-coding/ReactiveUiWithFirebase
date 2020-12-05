package com.inspirecoding.reactiveuiwithfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.inspirecoding.reactiveuiwithfirebase.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var binding : ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getTestEntries()
        setupTestFirebaseObserver()
    }

    private fun setupTestFirebaseObserver() {

        viewModel.testEntries.observe(this, Observer { _result ->

            when (_result.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    var text = ""
                    _result._data?.forEach {
                        it.testField?.let {
                            Log.d(TAG, it)
                            text += "$it\n"
                        }
                    }

                    binding.tvResult.text = text
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    _result.message?.let { message ->
                        Snackbar.make(
                            binding.rootView,
                            message, Snackbar.LENGTH_LONG
                        ).show()
                        Log.e(TAG, message)
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }

        })

    }

}