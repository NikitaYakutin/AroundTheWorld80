package com.example.vokrugsveta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val correctAnswers = intent.getIntExtra("correctAnswers", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)

        val resultsText: TextView = findViewById(R.id.results_text)
        resultsText.text = "Вы ответили правильно на $correctAnswers из $totalQuestions вопросов."

        val backToMapButton: Button = findViewById(R.id.back_to_map_button)
        backToMapButton.setOnClickListener {
            // Возвращение к карте
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
