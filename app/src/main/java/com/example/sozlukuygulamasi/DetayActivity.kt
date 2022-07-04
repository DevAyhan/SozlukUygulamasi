package com.example.sozlukuygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sozlukuygulamasi.databinding.ActivityDetayBinding

class DetayActivity : AppCompatActivity() {
    private lateinit var tasarimDetay:ActivityDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarimDetay = ActivityDetayBinding.inflate(layoutInflater)

        setContentView(tasarimDetay.root)

        val kelime = intent.getSerializableExtra("nesne") as Kelimeler

        tasarimDetay.textViewingilizce.text = kelime.ingilizce
        tasarimDetay.textViewturkce.text = kelime.turkce
    }
}