package ru.samirkad.kadrecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.samirkad.kadrecipe.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)
    }
}