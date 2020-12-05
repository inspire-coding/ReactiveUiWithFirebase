package com.inspirecoding.reactiveuiwithfirebase

import androidx.lifecycle.asLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class TestRepository {

    val documentReference = FirebaseFirestore.getInstance().collection("testCollection")


    /**
     * Returns Flow of [Resource] which retrieves all test entries from cloud firestore collection.
     */
    fun getTestDoc() = flow<Resource<List<TestClass>>> {

        emit(Resource.Loading(true))

        val snapshot = documentReference.get().await()
        val testClass = snapshot.toObjects(TestClass::class.java)

        emit(Resource.Success(testClass))
    }.catch { exception ->

        exception.message?.let { message ->
            emit(Resource.Error(message))
        }

    }.flowOn(Dispatchers.IO)

}