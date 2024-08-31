package com.example.newsapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapp.R

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        titleTextView = findViewById(R.id.newsDetailTitle)
        contentTextView = findViewById(R.id.newsDetailContent)

        val title = intent.getStringExtra("NEWS_TITLE")
        val content = intent.getStringExtra("NEWS_CONTENT")

        titleTextView.text = title
        contentTextView.text = content
    }
}
