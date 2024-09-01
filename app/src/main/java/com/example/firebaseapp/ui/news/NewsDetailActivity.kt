package com.example.firebaseapp.ui.news

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapp.R

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var newsImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        titleTextView = findViewById(R.id.newsDetailTitle)
        contentTextView = findViewById(R.id.newsDetailContent)
        newsImage = findViewById(R.id.newsDetailImage)

        val title = intent.getStringExtra("NEWS_TITLE")
        val content = intent.getStringExtra("NEWS_CONTENT")

        titleTextView.text = title
        contentTextView.text = content

        val random = (1..3).random()
        if (random == 1) newsImage.setImageResource(R.drawable.cricket)
        else if (random == 2) newsImage.setImageResource(R.drawable.reporter)
        else newsImage.setImageResource(R.drawable.sports)

    }
}
