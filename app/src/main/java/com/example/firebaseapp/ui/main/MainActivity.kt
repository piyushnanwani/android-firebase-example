package com.example.firebaseapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.model.News
import com.example.firebaseapp.R
import com.example.firebaseapp.ui.news.AddNewsActivity
import com.example.firebaseapp.ui.news.NewsAdapter
import com.example.firebaseapp.ui.news.NewsDetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<News>()
    private lateinit var addNewsButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
//
//
//        addTodoButton.setOnClickListener {
//            addTodo()
//        }
        addNewsButton = findViewById(R.id.floatingActionButton)
        newsRecyclerView = findViewById(R.id.newsRecyclerView)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)

        newsAdapter = NewsAdapter(newsList) { news ->
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra("NEWS_TITLE", news.title)
            intent.putExtra("NEWS_CONTENT", news.content)
            startActivity(intent)
        }
        newsRecyclerView.adapter = newsAdapter

        loadNews()
        fetchNewsFromFirebase()

        addNewsButton.setOnClickListener {
            val intent = Intent(this, AddNewsActivity::class.java)
            startActivity(intent)
        }

    }
    private fun loadNews() {
        // Sample news data
//        newsList.add(News("News Title 1", "News content 1"))
//        newsList.add(News("News Title 2", "News content 2"))
//        newsList.add(News("News Title 3", "News content 3"))



//        newsAdapter.notifyDataSetChanged()
    }

    // write function to fetch news from firebase
    private fun fetchNewsFromFirebase()
    {
        val database = Firebase.database
        val ref = database.getReference("news")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                newsList.clear()
                for (postSnapshot in snapshot.children) {
                    Log.d("MainActivity", "fetchNewsFromFirebase: $postSnapshot ${postSnapshot.value}")
                    val news = postSnapshot.value
                    news?.let {
                        val title = postSnapshot.child("title").value.toString()
                        val content = postSnapshot.child("content").value.toString()
                        newsList.add(News(title, content))
                    }
                }
                newsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to read value", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
