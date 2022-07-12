package com.trakclok.android.database

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.trakclok.android.utils.PAGE_SIZE
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class RealtimeGeneric {
    private val database: DatabaseReference by lazy {
        Firebase.database.reference
    }

    /**
     * check if value exists on given path
     * @param path
     */
    suspend fun exists(path: String) = suspendCoroutine<Boolean> { continuation ->
        database.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.exists()
                continuation.resume(value)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(Exception(error.message))
            }
        })
    }

    /**
     * actively listen to data change at path
     * @param path
     * @param callback
     */
    fun change(path: String, callback: (error: Exception?, snapshot: DataSnapshot?) -> Unit) {
        database.child(path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(null, snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(error.toException(), null)
            }
        })
    }

    /**
     * actively listen to data change at given location
     * sorted by value at path
     * @param path
     * @param callback
     */
    fun changeByValue(
        path: String,
        callback: (error: Exception?, snapshot: DataSnapshot?) -> Unit
    ) {
        database.child(path)
            .orderByValue()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    callback(null, snapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(error.toException(), null)
                }
            })
    }

    /**
     * get data only once at given path
     * @param path
     */
    suspend fun dataOnce(path: String) = suspendCoroutine<DataSnapshot> { continuation ->
        database.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(Exception(error.message))
            }
        })
    }

    /**
     * insert value on given path
     * @param path
     * @param value
     */
    suspend fun dataCreate(path: String, value: Any) = suspendCoroutine<Boolean> { continuation ->
        database.child(path).setValue(value)
            .addOnSuccessListener { continuation.resume(true) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }

    /**
     * delete value on given path
     * @param path
     */
    suspend fun deleteValue(path: String) = suspendCoroutine<Boolean> { continuation ->
        database.child(path).removeValue()
            .addOnSuccessListener { continuation.resume(true) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }

    /**
     * increment value at path
     * @param path
     * @param increase - factor to increase by
     */
    suspend fun increment(path: String, increase: Long = 1.toLong()) =
        suspendCoroutine<Boolean> { continuation ->
            database.child(path)
                .setValue(ServerValue.increment(increase))
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }

    /**
     * decrement value at path
     * @param path
     * @param decrease - factor to decrease by
     */
    suspend fun decrement(path: String, decrease: Long = 1.toLong()) =
        suspendCoroutine<Boolean> { continuation ->
            database.child(path)
                .setValue(ServerValue.increment(-decrease))
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }

    /**
     * get list of data at path
     * sorted by key
     * get data only once
     * @param path
     * @param startAfter - start list after given key
     * @param listSize - size of list
     */
    suspend fun listByKey(path: String, startAfter: String? = null, listSize: Int = PAGE_SIZE, ) =
        suspendCoroutine<DataSnapshot> { continuation ->
            database.child(path)
                .orderByKey()
                .startAfter(startAfter)
                .limitToFirst(listSize)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        continuation.resume(snapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(Exception(error.message))
                    }
                })
        }

    /**
     * get list of data at path
     * sorted by value at path
     * get data only once
     * @param path
     * @param startAfter - start list after given key
     * @param listSize - size of list
     */
    suspend fun listByValue(path: String,startAfter: String? = null,  listSize: Int = PAGE_SIZE, ) =
        suspendCoroutine<DataSnapshot> { continuation ->
            database.child(path)
                .orderByValue()
                .startAfter(startAfter)
                .limitToFirst(listSize)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        continuation.resume(snapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(Exception(error.message))
                    }
                })
        }

    /**
     * get list of data at path
     * sorted by child
     * get data only once
     * @param path
     * @param listSize - size of list
     * @param startAfter - start list after given child value
     */
    suspend fun listByChild(
        path: String,
        child: String,
        startAfter: String? = null,
        listSize: Int = PAGE_SIZE
    ) =
        suspendCoroutine<DataSnapshot> { continuation ->
            database.child(path)
                .orderByChild(child)
                .startAfter(startAfter)
                .limitToFirst(listSize)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        continuation.resume(snapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(Exception(error.message))
                    }
                })
        }

    /**
     * get list of data at path
     * sorted by child using double startAfter
     * get data only once
     * @param path
     * @param listSize - size of list
     * @param startAfter - start list after given double child value
     */
    suspend fun listByChildDouble(
        path: String,
        child: String,
        startAfter: Double,
        listSize: Int = PAGE_SIZE
    ) =
        suspendCoroutine<DataSnapshot> { continuation ->
            database.child(path)
                .orderByChild(child)
                .startAfter(startAfter)
                .limitToFirst(listSize)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        continuation.resume(snapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(Exception(error.message))
                    }
                })
        }

    /**
     * list of items sort by child (once & limitless)
     * @param path
     * @param child
     * @param startAfter - start list after child value
     */
    suspend fun listByChildAll(path: String, child: String, startAfter: String) =
        suspendCoroutine<DataSnapshot> { continuation ->
            database.child(path)
                .orderByChild(child)
                .startAfter(startAfter)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        continuation.resume(snapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(Exception(error.message))
                    }
                })
        }

    /**
     * update children
     * @param map
     */
    suspend fun update(map: Map<String, Any?>) = suspendCoroutine<Boolean> { continuation ->
        database.updateChildren(map)
            .addOnCompleteListener { continuation.resume(true) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }
}
