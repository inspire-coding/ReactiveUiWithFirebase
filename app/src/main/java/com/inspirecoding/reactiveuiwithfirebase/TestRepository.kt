package com.inspirecoding.reactiveuiwithfirebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class TestRepository {

    val testCollectionReference = FirebaseFirestore.getInstance().collection("testCollection")
    val usersReference = FirebaseFirestore.getInstance().collection("users")


    /**
     * Returns Flow of [Resource] which retrieves all test entries from cloud firestore collection.
     */
    fun getTestDoc() = flow<Resource<List<TestClass>>> {

        emit(Resource.Loading(true))

        val snapshot = testCollectionReference.get().await()
        val testClass = snapshot.toObjects(TestClass::class.java)

        emit(Resource.Success(testClass))
    }.catch { exception ->

        exception.message?.let { message ->
            emit(Resource.Error(message))
        }

    }.flowOn(Dispatchers.IO)

    /**
     * Returns Flow of [Resource] which retrieves all user entries from cloud firestore collection.
     */
    fun getUserDoc() = flow<Resource<List<User>>> {

        emit(Resource.Loading(true))

        val snapshot = usersReference.get().await()
        val testClass = snapshot.toObjects(User::class.java)

        emit(Resource.Success(testClass))
    }.catch { exception ->

        exception.message?.let { message ->
            emit(Resource.Error(message))
        }

    }.flowOn(Dispatchers.IO)

}