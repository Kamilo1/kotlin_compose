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

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.board_activity_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.board_activity_sound -> Toast.makeText(this, "Sound on", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
}