package com.example.sozlukuygulamasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sozlukuygulamasi.databinding.ActivityMainBinding
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper
import androidx.appcompat.widget.SearchView as SearchView

class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {
    private lateinit var tasarim:ActivityMainBinding
    private lateinit var kelimelerListe:ArrayList<Kelimeler>
    private lateinit var adapter:KelimelerAdapter
    private lateinit var vt:VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = ActivityMainBinding.inflate(layoutInflater)
        setContentView(tasarim.root)

        veritabaniKopyala()

        tasarim.toolbar.title = "Sözlük Uygulaması"
        setSupportActionBar(tasarim.toolbar)

        tasarim.rv.setHasFixedSize(true)
        tasarim.rv.layoutManager = LinearLayoutManager(this)

        vt = VeritabaniYardimcisi(this)

       kelimelerListe = Kelimelerdao().tumKelimeler(vt)

        adapter = KelimelerAdapter(this,kelimelerListe)

        tasarim.rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)

        val item = menu?.findItem(R.id.action_ara)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this@MainActivity)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {

        arama(query)

        Log.e("Gönderilen arama", query.toString())

        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {

        arama(newText)
        Log.e("Harf girdikçe",newText.toString())

        return true
    }

    fun veritabaniKopyala(){
        val copyHelper = DatabaseCopyHelper(this)

        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun arama(aramaKelime:String){

        kelimelerListe = Kelimelerdao().aramaYap(vt,aramaKelime)

        adapter = KelimelerAdapter(this,kelimelerListe)

        tasarim.rv.adapter = adapter

    }
}