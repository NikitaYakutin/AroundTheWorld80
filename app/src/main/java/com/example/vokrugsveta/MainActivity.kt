package com.example.vokrugsveta

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настройка osmdroid
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_main)

        // Проверка разрешений для геолокации
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        // Инициализация карты
        mapView = findViewById(R.id.map)
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Отключение повторения тайлов
        mapView.setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
        mapView.isHorizontalMapRepetitionEnabled = false
        mapView.isVerticalMapRepetitionEnabled = false

        // Установка начальных параметров карты
        mapView.controller.setZoom(5.0)
        mapView.controller.setCenter(GeoPoint(55.7558, 37.6173)) // Москва

        // Добавляем маркеры
        addMarker(GeoPoint(55.7558, 37.6173), "Москва", "Вопрос о Москве")
        addMarker(GeoPoint(48.8566, 2.3522), "Париж", "Вопрос о Париже")
        addMarker(GeoPoint(-22.9068, -43.1729), "Рио-де-Жанейро", "Вопрос о Рио-де-Жанейро")
       addMarker(GeoPoint(34.0522, -118.2437), "Лос-Анджелес", "Вопрос о Лос-Анджелесе")


        // Добавляем кнопку Личный кабинет
        val profileButton: Button = findViewById(R.id.profile_button)
        profileButton.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addMarker(position: GeoPoint, title: String, description: String) {
        val marker = Marker(mapView)
        marker.position = position
        marker.title = title
        marker.subDescription = description

        marker.setOnMarkerClickListener { _, _ ->
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("city", title) // Передаем название города
            startActivity(intent)
            true
        }

        mapView.overlays.add(marker)
    }

    // Обработка разрешений
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Разрешение получено
        }
    }
}
