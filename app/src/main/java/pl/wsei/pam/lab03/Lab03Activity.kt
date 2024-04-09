package pl.wsei.pam.lab03

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.wsei.pam.lab01.R
import java.util.Timer
import kotlin.concurrent.schedule


class Lab03Activity : AppCompatActivity() {
    private lateinit var mBoardModel: MemoryBoardView
    lateinit var completionPlayer: MediaPlayer
    lateinit var negativePLayer: MediaPlayer
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
                            mBoardModel.animatePairedButton(tile.button, Runnable { })
                        }

                    }

                    GameStates.NoMatch -> {
                        e.tiles.forEach { tile ->
                            tile.revealed = true
                            mBoardModel.animateMismatchedPair(this@Lab03Activity, tile)
                        }

                        Timer().schedule(700) {
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override protected fun onResume() {
        super.onResume()
        completionPlayer = MediaPlayer.create(applicationContext, R.raw.completion)
        negativePLayer = MediaPlayer.create(applicationContext, R.raw.negative_guitar)
    }


    override protected fun onPause() {
        super.onPause();
        completionPlayer.release()
        negativePLayer.release()
    }

}
