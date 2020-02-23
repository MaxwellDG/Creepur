package com.portfolio.creepur.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.adapters.BookmarkRecycler


object Firebase {

    private val fireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val fireBaseAccounts: DatabaseReference = fireBaseDatabase.getReference("accounts")

    private var activeUserAccountSignedIn: MutableLiveData<UserAccountSignedIn> = MutableLiveData()
    private var activeUsersBookmarks: MutableLiveData<ArrayList<Bookmark>> = MutableLiveData()


    fun getUserAccountSignedIn(): LiveData<UserAccountSignedIn> = activeUserAccountSignedIn

    fun getAccountByAccountId(accountID: String) {
        val newRef: DatabaseReference = fireBaseDatabase.getReference("accounts/$accountID")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // retrieving their account from database
                Log.d("TAG", "Called for account $accountID")
                val list: ArrayList<UserAccountSignedIn> = arrayListOf()
                list.add(dataSnapshot.getValue(UserAccountSignedIn::class.java)!!)
                activeUserAccountSignedIn.postValue(list[0])
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message)
            }
        }
        newRef.addListenerForSingleValueEvent(postListener)
    }

    // creates child node based off of accountId
    fun inputToDatabase(account: UserAccountSignedIn) {
        fireBaseAccounts.child(account.accountId!!).setValue(account)
            .addOnCompleteListener { Log.d("TAG","Firebase updated.") }
            .addOnFailureListener { Log.d("TAG", "Firebase failed to be updated") }
    }

    // updates a specified node with a specified value, without rewriting the entire object
    fun updateFirebaseAccount(account: UserAccountSignedIn, propertyToBeUpdated: String, newPropertyValue: String){
        val personalAccountRef: DatabaseReference = fireBaseAccounts.child(account.accountId!!)
        val theUpdate = mapOf(propertyToBeUpdated to newPropertyValue)
        personalAccountRef.updateChildren(theUpdate)
    }

    // deletes the entire account from database
    fun deleteFirebaseAccount(account: UserAccountSignedIn){
        fireBaseAccounts.child(account.accountId.toString()).removeValue()
    }









    // Bookmark functions

    fun getActiveUsersBookmarks(): LiveData<ArrayList<Bookmark>> = activeUsersBookmarks

    fun getBookMarksFromDatabase(accountId: String, adapter: BookmarkRecycler){
        val bookmarks: ArrayList<Bookmark> = arrayListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // retrieving their bookmarks from database
                bookmarks.clear()
                for(snapshot: DataSnapshot in dataSnapshot.children){
                    bookmarks.add(snapshot.getValue(Bookmark::class.java)!!)
                }
                Log.d("TAG", "doot doot")
                activeUsersBookmarks.postValue(bookmarks)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message)
            }
        }
        val firebaseAccountBookmarksRef = fireBaseAccounts.child(accountId).child("bookmarks")
        firebaseAccountBookmarksRef.addValueEventListener(postListener)
    }

    fun inputBookmarkToDatabase(bookmark: Bookmark, account: UserAccountSignedIn){
        val personalAccountRef: DatabaseReference = fireBaseAccounts.child("${account.accountId}")
        personalAccountRef.child("bookmarks").child(bookmark.username).setValue(bookmark)
    }

    fun deleteBookmarkFromFirebase(bookmark: Bookmark, account: UserAccountSignedIn){
        val personalAccountRef: DatabaseReference = fireBaseAccounts.child("${account.accountId}/bookmarks")
        personalAccountRef.child(bookmark.username).removeValue()
    }
}

