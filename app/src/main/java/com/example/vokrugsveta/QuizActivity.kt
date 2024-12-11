package com.example.vokrugsveta

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorSpace.Rgb
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class QuizActivity : AppCompatActivity() {

    private var correctAnswers = 0
    private var currentQuestionIndex = 0
 // Добавим свойство для города
    private lateinit var questionText: TextView
    private lateinit var btnA: Button
    private lateinit var btnB: Button
    private lateinit var btnC: Button
    private lateinit var btnD: Button
    private lateinit var backToMapButton: Button

    private val questions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Инициализация представлений
        initViews()

        // Получение данных из Intent
        val city = intent.getStringExtra("city") ?: "Неизвестный город"

        // Настройка вопросов для города
        setupQuestionsForCity(city)

        // Отображение первого вопроса
        displayQuestion()
    }

    override fun onDestroy() {
        super.onDestroy()
        val city = intent.getStringExtra("city") ?: "Неизвестный город"
        finishQuiz(city)
    }
    private fun initViews() {
        questionText = findViewById(R.id.question_text)
        btnA = findViewById(R.id.btnA)
        btnB = findViewById(R.id.btnB)
        btnC = findViewById(R.id.btnC)
        btnD = findViewById(R.id.btnD)
        backToMapButton = findViewById(R.id.back_to_map_button)

        backToMapButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupQuestionsForCity(city: String) {
        questions.clear()
        questions.addAll(getQuestionsForCity(city))
    }

    private fun displayQuestion() {
        if (currentQuestionIndex >= questions.size) {
            // Показать кнопку возврата, если вопросы закончились
            questionText.text = "Вы завершили викторину! Правильных ответов: $correctAnswers"
            btnA.isVisible = false
            btnB.isVisible = false
            btnC.isVisible = false
            btnD.isVisible = false
            backToMapButton.isVisible = true

            return
        }

        val currentQuestion = questions[currentQuestionIndex]
        questionText.text = currentQuestion.text

        btnA.text = currentQuestion.options["A"]
        btnB.text = currentQuestion.options["B"]
        btnC.text = currentQuestion.options["C"]
        btnD.text = currentQuestion.options["D"]

        btnA.setOnClickListener { checkAnswer("A") }
        btnB.setOnClickListener { checkAnswer("B") }
        btnC.setOnClickListener { checkAnswer("C") }
        btnD.setOnClickListener { checkAnswer("D") }
    }

    private fun checkAnswer(selectedOption: String) {
        val currentQuestion = questions[currentQuestionIndex]
        val isCorrect = currentQuestion.correctOption == selectedOption

        val selectedButton = when (selectedOption) {
            "A" -> btnA
            "B" -> btnB
            "C" -> btnC
            else -> btnD
        }

        selectedButton.setBackgroundColor(if (isCorrect) Color.GREEN else Color.RED)

        if (isCorrect) correctAnswers++

        currentQuestionIndex++

        selectedButton.postDelayed({
            resetButtonColors()
            displayQuestion()
        }, 1000)
    }

    private fun resetButtonColors() {
        btnA.setBackgroundColor(Color.parseColor("#FF6200EE"))
        btnB.setBackgroundColor(Color.parseColor("#FF6200EE"))
        btnC.setBackgroundColor(Color.parseColor("#FF6200EE"))
        btnD.setBackgroundColor(Color.parseColor("#FF6200EE"))
    }

    private fun getQuestionsForCity(city: String): List<Question> {
        return when (city) {
            "Москва" -> listOf(
                Question("Столица России?", mapOf("A" to "Москва", "B" to "Париж", "C" to "Лондон", "D" to "Берлин"), "A"),
                Question("Какой город известен Красной площадью?", mapOf("A" to "Киев", "B" to "Москва", "C" to "Минск", "D" to "Варшава"), "B"),
                Question("Какой собор находится в Москве?", mapOf("A" to "Кельнский собор", "B" to "Собор Святого Петра", "C" to "Собор Василия Блаженного", "D" to "Собор Парижской Богоматери"), "C"),
                Question("Где находится Кремль?", mapOf("A" to "Москва", "B" to "Новгород", "C" to "Казань", "D" to "Сочи"), "A")
            )
            "Париж" -> listOf(
                Question("Где находится Эйфелева башня?", mapOf("A" to "Лондон", "B" to "Париж", "C" to "Берлин", "D" to "Мадрид"), "B"),
                Question("Какой музей находится в Париже?", mapOf("A" to "Лувр", "B" to "Метрополитен", "C" to "Тейт", "D" to "Эрмитаж"), "A"),
                Question("Как называется река в Париже?", mapOf("A" to "Темза", "B" to "Сена", "C" to "Рейн", "D" to "Дунай"), "B"),
                Question("Как называется известный собор в Париже?", mapOf("A" to "Собор Василия Блаженного", "B" to "Собор Парижской Богоматери", "C" to "Собор Святого Павла", "D" to "Кельнский собор"), "B")
            )
            "Рио-де-Жанейро" -> listOf(
                Question("Как называется знаменитая статуя в Рио?", mapOf("A" to "Христос-Искупитель", "B" to "Статуя Свободы", "C" to "Колосс Родосский", "D" to "Статуя Будды"), "A"),
                Question("Какой карнавал известен во всём мире?", mapOf("A" to "Московский", "B" to "Венецианский", "C" to "Рио-де-Жанейро", "D" to "Токийский"), "C"),
                Question("Какая гора известна в Рио?", mapOf("A" to "Фудзи", "B" to "Сахарная голова", "C" to "Эверест", "D" to "Монблан"), "B"),
                Question("Какая известная река находится в Бразилии?", mapOf("A" to "Амазонка", "B" to "Нил", "C" to "Миссисипи", "D" to "Ганг"), "A")
            )
            "Лос-Анджелес" -> listOf(
                Question("Как называется знаменитый район киноиндустрии?", mapOf("A" to "Бродвей", "B" to "Голливуд", "C" to "Сильверлейк", "D" to "Долина Напа"), "B"),
                Question("Какой океан омывает Лос-Анджелес?", mapOf("A" to "Атлантический", "B" to "Индийский", "C" to "Северный Ледовитый", "D" to "Тихий"), "D"),
                Question("Какая аллея известна в Лос-Анджелесе?", mapOf("A" to "Аллея Славы", "B" to "Аллея Любви", "C" to "Аллея Героев", "D" to "Аллея Победы"), "A"),
                Question("Какой парк является крупнейшим городским парком?", mapOf("A" to "Центральный", "B" to "Гриффит", "C" to "Гайд", "D" to "Станли"), "B")
            )
            else -> listOf(
                Question("Город неизвестен", mapOf("A" to "A", "B" to "B", "C" to "C", "D" to "D"), "D")
            )
        }
    }
    private fun finishQuiz(city: String) {
        // Сохраняем результаты и название города в SharedPreferences
        val sharedPreferences = getSharedPreferences("quiz_results", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Получаем текущие данные (если есть)
        val existingResults = sharedPreferences.getString("quizHistory", "") ?: ""

        // Формируем новый результат викторины
        val newResult = "Город: $city, Правильных ответов: $correctAnswers"

        // Обновляем историю, добавляя новый результат
        val updatedResults = if (existingResults.isEmpty()) {
            newResult
        } else {
            "$existingResults\n$newResult"
        }

        // Сохраняем обновленную историю викторин
        editor.putString("quizHistory", updatedResults)
        editor.apply()
    }



}

data class Question(
    val text: String,
    val options: Map<String, String>,
    val correctOption: String
)

