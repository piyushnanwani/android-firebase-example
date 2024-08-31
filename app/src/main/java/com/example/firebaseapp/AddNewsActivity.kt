package com.example.firebaseapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.News
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddNewsActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_news)

        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val content = contentEditText.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                addNewsToFirebase(title, content)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun addNewsToFirebase(title: String, content: String) {
        val database = Firebase.database
        val ref = database.getReference("news")
        val news = News(title, content)
        ref.push().setValue(news)
            .addOnSuccessListener {
                Toast.makeText(this, "News added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add news", Toast.LENGTH_SHORT).show()
            }
    }
}
