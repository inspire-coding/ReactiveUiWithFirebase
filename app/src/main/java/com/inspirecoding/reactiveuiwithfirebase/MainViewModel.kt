package com.inspirecoding.reactiveuiwithfirebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val repository = TestRepository()

    private val _testEntries = MutableLiveData<Resource<List<TestClass>>>()
    val testEntries : LiveData<Resource<List<TestClass>>> = _testEntries

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users : LiveData<Resource<List<User>>> = _users

    fun getTestEntries() {

        viewModelScope.launch {
            repository.getTestDoc()
                .collect { _result ->
                    _testEntries.postValue(_result)

            }
            repository.getUserDoc()
                .collect{ _result ->
                    _users.postValue(_result)
                }
        }

    }

}