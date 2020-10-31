package com.anifichadia.sampleapp.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anifichadia.sampleapp.databinding.ActivityMainBinding
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizActivity


/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBtnMultipleChoiceQuiz.setOnClickListener {
            startActivity(Intent(this, MultipleChoiceQuizActivity::class.java))
        }
    }
}
