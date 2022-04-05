package com.example.mylist.db

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mylist.MainActivity
import com.example.mylist.R
import java.util.*

class StartActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        progressBar = findViewById(R.id.progressBar)
        val my_list = findViewById<ConstraintLayout>(R.id.my_list)

        ObjectAnimator.ofInt(progressBar,"progress", 100).setDuration(2000).start()

        //my_list.alpha = 0f
        my_list.animate().setDuration(2000).alpha(1F).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }


    }
}