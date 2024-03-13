package pl.wsei.pam.lab02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import pl.wsei.pam.lab01.R

class Lab02Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab02)

        fun onClickMainBtnRunLab01(v: View){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }
        fun onClickMainBtnRunLab02(v: View){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }


}