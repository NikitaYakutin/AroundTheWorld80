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

        // Получаем все результаты викторин из SharedPreferences
        val sharedPreferences = getSharedPreferences("quiz_results", MODE_PRIVATE)
        val quizHistory = sharedPreferences.getString("quizHistory", "Нет результатов") ?: "Нет результатов"

        // Инициализация TextView и отображение всех результатов
        val resultsText: TextView = findViewById(R.id.results_text)
        resultsText.text = quizHistory
        val clearHistoryButton: Button = findViewById(R.id.clear_history_button)
        clearHistoryButton.setOnClickListener {
            // Очистить историю викторин в SharedPreferences
            val editor = sharedPreferences.edit()
            editor.remove("quizHistory")  // Удаляем сохраненные результаты
            editor.apply()

            // Обновить текст в TextView, чтобы отобразить пустую историю
            resultsText.text = "История очищена"
        }
        val backToMapButton: Button = findViewById(R.id.back_to_map_button)
        backToMapButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
