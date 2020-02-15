package com.portfolio.creepur.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.Data
import com.portfolio.creepur.models.UserAccountSignedIn


object Firebase {

    private val fireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val fireBaseAccounts: DatabaseReference = fireBaseDatabase.getReference("accounts")
    private var activeUserAccountSignedIn: MutableLiveData<UserAccountSignedIn> = MutableLiveData()


    // this is the only way to retrieve data from Firebase so this function doubles as
    // initializing the listener as well as retrieving the user's account
    fun initFirebaseListener(accountID: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // retrieving their account from database
                val activeAccount: UserAccountSignedIn = dataSnapshot.child(accountID).getValue(UserAccountSignedIn::class.java)!!
                activeUserAccountSignedIn.value = (activeAccount)
                Log.d("TAG", "Database noted a change of: ${activeAccount?.userName}")
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TAG", databaseError.message)
            }
        }
        fireBaseAccounts.addValueEventListener(postListener)
    }

    fun getUserAccountSignedIn(): LiveData<UserAccountSignedIn> = this.activeUserAccountSignedIn // might use this later on a personal settings page

    fun inputToDatabase(account: UserAccountSignedIn) {
        //locates proper child node by unique accountID and updates accordingly
        val userHashMap: HashMap<String, UserAccountSignedIn> = HashMap()
        userHashMap.put(account.accountId!!, account)
        fireBaseAccounts.setValue(userHashMap)
            .addOnCompleteListener { Log.d("TAG","Firebase updated.") }
            .addOnFailureListener { Log.d("TAG", "Firebase failed to be updated") }
        activeUserAccountSignedIn.value = account
    }

    // updates a specified node with a specified value, without rewriting the entire object
    fun updateFirebaseAccount(account: UserAccountSignedIn, propertyToBeUpdated: String, newPropertyValue: String){
        val personalAccountRef: DatabaseReference = fireBaseAccounts.child(account.accountId!!)
        val theUpdate: MutableMap<String, Any> = HashMap()
        theUpdate[propertyToBeUpdated] = newPropertyValue
        personalAccountRef.updateChildren(theUpdate)
    }

    // deletes the entire account from database
    fun deleteFirebaseAccount(account: UserAccountSignedIn){
        fireBaseAccounts.child(account.accountId.toString()).removeValue()
    }

    fun getBookMarks(): ArrayList<Bookmark>?{
        return null
    }

    fun inputBookmarkToDatabase(bookmark: Bookmark, account: UserAccountSignedIn){
        val personalAccountRef: DatabaseReference = fireBaseAccounts.child("${account.accountId.toString()}/bookmarks")
    }

    fun postFirebaseAccounts(account: MutableLiveData<List<Data>>) {
        // TODO: post this list of data to the recyclerView's LiveData property (make sure that it gets fed all the way down the line to here first)
    }
}

