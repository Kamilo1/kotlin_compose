package pl.wsei.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import pl.wsei.pam.lab01.Lab01Activity
import pl.wsei.pam.lab01.R
import pl.wsei.pam.lab02.Lab02Activity
import pl.wsei.pam.lab06.Lab06Activity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickMainBtnRunLab01(View: View){
        val intent = Intent(this, Lab01Activity::class.java)
        Toast.makeText(this, "Lab1", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
    fun onClickMainBtnRunLab02(View: View){
        val intent = Intent(this,Lab02Activity::class.java)
        Toast.makeText(this, "Lab2", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    fun onClickMainBtnRunLab06(View: View) {
        val intent = Intent(this, Lab06Activity::class.java)
        Toast.makeText(this, "Lab6", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }


}