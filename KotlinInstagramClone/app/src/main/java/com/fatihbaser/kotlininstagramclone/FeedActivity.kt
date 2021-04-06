package com.fatihbaser.kotlininstagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class FeedActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater =menuInflater
        menuInflater.inflate(R.menu.options_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {




        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


    }
}