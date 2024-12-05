package com.example.vokrugsveta

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class QuizActivity : AppCompatActivity() {

    private var correctAnswers = 0
    private var totalQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val questionText: TextView = findViewById(R.id.question_text)
        val btnA: Button = findViewById(R.id.btnA)
        val btnB: Button = findViewById(R.id.btnB)
        val btnC: Button = findViewById(R.id.btnC)
        val btnD: Button = findViewById(R.id.btnD)
        val resultsButton: Button = findViewById(R.id.results_button)

        // Получение вопроса
        val question = intent.getStringExtra("question") ?: "Город неизвестен"
        val (quizQuestion, correctOption) = getQuestionForCity(question)

        questionText.text = quizQuestion

        // Настройка кнопок с ответами
        btnA.text = "Ответ A"
        btnB.text = "Ответ B"
        btnC.text = "Ответ C"
        btnD.text = "Ответ D"

        // Устанавливаем правильный ответ для города
        btnA.setOnClickListener { checkAnswer(btnA, correctOption == "A") }
        btnB.setOnClickListener { checkAnswer(btnB, correctOption == "B") }
        btnC.setOnClickListener { checkAnswer(btnC, correctOption == "C") }
        btnD.setOnClickListener { checkAnswer(btnD, correctOption == "D") }

        resultsButton.isVisible = false // Скрываем кнопку результатов изначально

        // Показываем кнопку "Результаты" после ответа
        resultsButton.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("correctAnswers", correctAnswers)
            intent.putExtra("totalQuestions", totalQuestions)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAnswer(button: Button, isCorrect: Boolean) {
        button.setBackgroundColor(if (isCorrect) Color.GREEN else Color.RED)

        if (isCorrect) correctAnswers++
        totalQuestions++

        // Ожидание перед завершением активности
        button.postDelayed({
            // Делаем кнопку результатов видимой
            val resultsButton: Button = findViewById(R.id.results_button)
            resultsButton.isVisible = true
        }, 1000)
    }

    private fun getQuestionForCity(city: String): Pair<String, String> {
        return when (city) {
            "Москва" -> "Столица России?" to "A"
            "Париж" -> "Где находится Эйфелева башня?" to "B"
            "Рио-де-Жанейро" -> "Где проводится карнавал?" to "C"
            else -> "Город неизвестен" to "D"
        }
    }
}
