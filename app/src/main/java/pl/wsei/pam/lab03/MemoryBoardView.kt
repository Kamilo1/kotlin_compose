package pl.wsei.pam.lab03

import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import pl.wsei.pam.lab01.R
import java.util.Stack

class MemoryBoardView(
    private val gridLayout: GridLayout,
    private val columns: Int,
    private val rows: Int
) {
    private val tiles: MutableMap<String, Tile> = mutableMapOf()
    private val icons: List<Int> = listOf(
        R.drawable.baseline_rocket_launch_24,
        R.drawable.baseline_notifications_active_24,
        R.drawable.baseline_palette_24,
        R.drawable.baseline_nightlight_24,
        R.drawable.baseline_nearby_error_24,
        R.drawable.baseline_more_time_24,
        R.drawable.baseline_mic_24,
        R.drawable.baseline_markunread_24,
        R.drawable.baseline_layers_24,
        R.drawable.baseline_icecream_24,
        R.drawable.baseline_flight_24,
        R.drawable.baseline_gpp_good_24,
        R.drawable.baseline_female_24,
        R.drawable.baseline_circle_24,
        R.drawable.baseline_assist_walker_24,
        R.drawable.baseline_arrow_circle_up_24


    )
    init {
        val shuffledIcons: MutableList<Int> = mutableListOf<Int>().also {
            it.addAll(icons.subList(0, columns * rows / 2))
            it.addAll(icons.subList(0, columns * rows / 2))
            it.shuffle()
        }

        for(row in 0 until rows)
        {
            for(col in 0 until columns)
            {
                val tag = "${row}x${col}"
                val btn = ImageButton(gridLayout.context).also {
                    it.tag = tag
                    val layoutParams = GridLayout.LayoutParams()
                    it.setImageResource(R.drawable.baseline_audiotrack_24)
                    layoutParams.width = 0
                    layoutParams.height = 0
                    layoutParams.setGravity(Gravity.CENTER)
                    layoutParams.columnSpec = GridLayout.spec(col, 1, 1f)
                    layoutParams.rowSpec = GridLayout.spec(row, 1, 1f)
                    it.layoutParams = layoutParams
                    it.setImageResource(R.drawable.baseline_flight_24)
                    gridLayout.addView(it)
                }
                tiles[tag] = (Tile(btn, shuffledIcons.get(0), R.drawable.baseline_rocket_launch_24))
                addTile(btn, shuffledIcons.removeAt(0))
            }
        }
            // tu umieść kod pętli tworzący wszystkie karty, który jest obecnie
        // w aktywności Lab03Activity
    }
    private val deckResource: Int = R.drawable.baseline_flight_24
    private var onGameChangeStateListener: (MemoryGameEvent) -> Unit = { (e) -> }
    private val matchedPair: Stack<Tile> = Stack()
    private val logic: MemoryGameLogic = MemoryGameLogic(columns * rows / 2)

    private fun onClickTile(v: View) {
        val tile = tiles[v.tag]
        tile?.revealed = !(tile?.revealed ?: false)
        matchedPair.push(tile)
        val matchResult = logic.process {
            tile?.tileResource?:-1
        }
        onGameChangeStateListener(MemoryGameEvent(matchedPair.toList(), matchResult))
        if (matchResult != GameStates.Matching) {
            matchedPair.clear()
        }
    }
    fun getState(): List<Int> {
        return tiles.values.map { tile ->
            if (tile.revealed) tile.tileResource else -1
        }
    }


    fun setState(state: List<Int>) {
        tiles.values.forEachIndexed { index, tile ->
            val tileState = state[index]
            tile.revealed = tileState != -1
            if (tile.revealed) {
                tile.button.setImageResource(tileState)
            } else {
                tile.button.setImageResource(tile.deckResource)
            }
        }
    }
    fun setOnGameChangeListener(listener: (event: MemoryGameEvent) -> Unit) {
        onGameChangeStateListener = listener
    }

    private fun addTile(button: ImageButton, resourceImage: Int) {
        button.setOnClickListener(::onClickTile)
        val tile = Tile(button, resourceImage, deckResource)
        tiles[button.tag.toString()] = tile
    }


}