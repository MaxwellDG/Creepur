package com.portfolio.creepur.views

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.portfolio.creepur.R
import com.portfolio.creepur.models.Bookmark
import com.portfolio.creepur.models.UserAccountSignedIn
import com.portfolio.creepur.viewmodels.BookmarkViewModelFactory
import com.portfolio.creepur.viewmodels.BookmarksViewModel
import com.portfolio.creepur.viewmodels.adapters.BookmarkRecycler
import com.portfolio.creepur.viewmodels.adapters.BookmarkRecyclerItemTouch
import com.portfolio.creepur.viewmodels.listeners.NavigationItemSelected
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_book_marks.*
import kotlinx.android.synthetic.main.activity_book_marks.bottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.segment_bookmark.*

class BookMarks : AppCompatActivity() {

    private val adapter by lazy { BookmarkRecycler() }
    private val viewModel by lazy {val user: UserAccountSignedIn? = intent.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?
        ViewModelProvider(this, BookmarkViewModelFactory(user, adapter)).get(BookmarksViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_marks)

        init()

        viewModel.getBookmarks().observe(this, Observer<ArrayList<Bookmark>> { adapter.setNewData(it) })

        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelected
            (bottomNavigationView,this, intent?.getSerializableExtra("ACCOUNT") as UserAccountSignedIn?))
    }

    private fun init(){
        bookmarkRecycler.layoutManager = LinearLayoutManager(this)
        bookmarkRecycler.adapter = this.adapter
        val item = ItemTouchHelper(BookmarkRecyclerItemTouch(adapter))
        item.attachToRecyclerView(bookmarkRecycler)
        viewModel.loadDataSet()
        bottomNavigationView.menu.getItem(2).isChecked = true

        val animations: AnimationDrawable = conLayBookmark.background as AnimationDrawable
        animations.setEnterFadeDuration(4000)
        animations.setExitFadeDuration(4000)
        animations.start()

    }
}



