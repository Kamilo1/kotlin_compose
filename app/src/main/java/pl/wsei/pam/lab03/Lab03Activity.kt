package pl.wsei.pam.lab03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import pl.wsei.pam.lab01.R
import java.util.Timer
import kotlin.concurrent.schedule


class Lab03Activity : AppCompatActivity() {
    private lateinit var mBoardModel: MemoryBoardView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab03)
        val columns = intent.getIntExtra("columns", 2)
        val rows = intent.getIntExtra("rows", 3)
        println(rows)
        println(columns)
        var mBoard: GridLayout = findViewById(R.id.main_layout_3)
        mBoard.columnCount = columns
        mBoard.rowCount = rows

        mBoardModel = MemoryBoardView(mBoard, columns, rows)
        if (savedInstanceState != null) {
            val gameStateString = savedInstanceState.getString("gameState")
            val gameState = gameStateString?.split(",")?.map { it.toInt() } ?: listOf()
            mBoardModel.setState(gameState)
        }

        mBoardModel.setOnGameChangeListener { e ->
            runOnUiThread {
                when (e.state) {
                    GameStates.Matching -> {
                        e.tiles.forEach { tile ->
                            tile.revealed = true
                        }
                    }

                    GameStates.Match -> {
                        e.tiles.forEach { tile ->
                            tile.revealed = true
                        }
                    }

                    GameStates.NoMatch -> {
                        e.tiles.forEach { tile ->
                            tile.revealed = true
                        }
                        Timer().schedule(1000) {
                            runOnUiThread {
                                e.tiles.forEach { tile ->
                                    tile.revealed = false
                                }
                            }
                        }
                    }

                    GameStates.Finished -> {
                        Toast.makeText(this@Lab03Activity, "Game finished", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val gameState = mBoardModel.getState().joinToString(",")
        outState.putString("gameState", gameState)
    }
}
