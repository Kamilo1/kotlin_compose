package pl.wsei.pam.lab02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import pl.wsei.pam.lab01.R
import pl.wsei.pam.lab03.Lab03Activity

class Lab02Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab02)
        val favoritesGrid: GridLayout = findViewById(R.id.favorites_grid)
        favoritesGrid.forEachChildButton { button ->
            button.setOnClickListener {
                val boardSize = button.text.toString()
                goToBoard(boardSize)
            }
        }
    }

    private fun goToBoard(boardSize: String) {
        val (rows, columns) = boardSize.split("x")
        println(rows)
        println(columns)
        Toast.makeText(this, "Creating a $rows x $columns board", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Lab03Activity::class.java)
        intent.putExtra("columns", columns)
        intent.putExtra("rows",rows)
        startActivity(intent)

    }

    private fun GridLayout.forEachChildButton(action: (Button) -> Unit) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is Button) {
                action(child)
            }
        }
    }
}
