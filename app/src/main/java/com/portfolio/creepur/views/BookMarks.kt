package com.portfolio.creepur.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.creepur.R
import com.portfolio.creepur.repos.Firebase
import com.portfolio.creepur.viewmodels.BookmarksViewModel
import com.portfolio.creepur.viewmodels.adapters.BookmarkRecycler
import kotlinx.android.synthetic.main.activity_book_marks.*

class BookMarks : AppCompatActivity() {

    private lateinit var adapter: BookmarkRecycler
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_marks)

        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(BookmarksViewModel::class.java)

        initRecycler()
        addDataset(adapter)
    }

    private fun addDataset(adapterParam: BookmarkRecycler){

    }

    private fun initRecycler(){
        bookmarkRecycler.layoutManager = LinearLayoutManager(this)
        adapter = BookmarkRecycler()
        bookmarkRecycler.adapter = this.adapter
    }
}
