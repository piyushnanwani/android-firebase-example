package com.example.firebaseapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.News
import com.example.newsapp.NewsAdapter
import com.example.newsapp.NewsDetailActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth
//    private lateinit var emailEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var loginButton: Button
//    private lateinit var signupButton: Button
//    private lateinit var addTodoButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<News>()
    private lateinit var addNewsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
//
//        // Initialize UI elements
//        emailEditText = findViewById(R.id.emailEditText)
//        passwordEditText = findViewById(R.id.passwordEditText)
//        loginButton = findViewById(R.id.loginButton)
//        signupButton = findViewById(R.id.signupButton)
//        addTodoButton = findViewById(R.id.addTodoButton)
//
//        // Set up click listeners
//        loginButton.setOnClickListener {
//            loginUser()
//        }
//
//        signupButton.setOnClickListener {
//            signupUser()
//        }
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

        addNewsButton.setOnClickListener {
            val intent = Intent(this, AddNewsActivity::class.java)
            startActivity(intent)
        }

    }
//
//    private fun loginUser() {
//        val email = emailEditText.text.toString()
//        val password = passwordEditText.text.toString()
//
//        if (email.isNotEmpty() && password.isNotEmpty()) {
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Login success
//                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
//                        // Navigate to another activity or do something else
//                    } else {
//                        // Login failed
//                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        } else {
//            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun signupUser() {
//        val email = emailEditText.text.toString()
//        val password = passwordEditText.text.toString()
//
//        if (email.isNotEmpty() && password.isNotEmpty()) {
//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Signup success
//                        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
//                        // Navigate to another activity or do something else
//                    } else {
//                        // Signup failed
//                        Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        } else {
//            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    private fun addTodo() {
//        val database = Firebase.database
//        val ref = database.getReference("todos")
//        val todo = Todo("Buy groceries", false)
//        ref.push().setValue(todo)
//    }

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
                    val news = postSnapshot.getValue(News::class.java)
                    news?.let {
                        newsList.add(it)
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
