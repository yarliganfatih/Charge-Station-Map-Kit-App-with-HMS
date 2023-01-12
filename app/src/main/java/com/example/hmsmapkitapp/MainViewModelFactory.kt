package com.example.hmsmapkitapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hmsmapkitapp.data.repository.Repository
import com.example.hmsmapkitapp.data.viewmodel.MainViewModel

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}