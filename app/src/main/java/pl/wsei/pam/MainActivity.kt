package pl.wsei.pam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import pl.wsei.pam.lab01.Lab01Activity
import pl.wsei.pam.lab01.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickMainBtnRunLab01(View: View){
        val intent = Intent(this, Lab01Activity::class.java)
        startActivity(intent)
    }
    fun onClickMainBtnRunLab02(View: View){
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
    }
}