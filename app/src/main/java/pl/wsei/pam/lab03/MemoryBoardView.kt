package pl.wsei.pam.lab03

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.GridLayout
import android.widget.ImageButton
import pl.wsei.pam.lab01.R
import java.util.Random
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
                    layoutParams.width = 0
                    layoutParams.height = 0
                    layoutParams.setGravity(Gravity.CENTER)
                    layoutParams.columnSpec = GridLayout.spec(col, 1, 1f)
                    layoutParams.rowSpec = GridLayout.spec(row, 1, 1f)
                    it.layoutParams = layoutParams
                    it.setImageResource(R.drawable.baseline_circle_24)
                    gridLayout.addView(it)
                }
                tiles[tag] = (Tile(btn, shuffledIcons.get(0), R.drawable.baseline_rocket_launch_24))
                addTile(btn, shuffledIcons.removeAt(0))
            }
        }
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
                tile.tileResource = state[index]
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

    fun animateMismatchedPair(context: Activity, tile: Tile) {
        val button = tile.button
        val animatorSet = AnimatorSet()
        val rotateLeft = ObjectAnimator.ofFloat(button, "rotation", 0f, -10f)
        val rotateRight = ObjectAnimator.ofFloat(button, "rotation", -10f, 10f)
        val rotateBack = ObjectAnimator.ofFloat(button, "rotation", 10f, 0f)
        rotateLeft.duration = 100
        rotateRight.duration = 200
        rotateBack.duration = 100

        animatorSet.playSequentially(rotateLeft, rotateRight, rotateBack)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                context.runOnUiThread {
                    tile.revealed = false

                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    fun animatePairedButton(button: ImageButton, action: Runnable) {
        val set = AnimatorSet()
        val random = Random()
        button.pivotX = random.nextFloat() * 200f
        button.pivotY = random.nextFloat() * 200f

        val rotation = ObjectAnimator.ofFloat(button, "rotation", 1080f)
        val scallingX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 4f)
        val scallingY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 4f)
        val fade = ObjectAnimator.ofFloat(button, "alpha", 1f, 0f)
        set.startDelay = 500
        set.duration = 2000
        set.interpolator = DecelerateInterpolator()
        set.playTogether(rotation, scallingX, scallingY, fade)
        set.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                button.scaleX = 1f
                button.scaleY = 1f
                button.alpha = 0.0f
                action.run();
                button.visibility = View.GONE
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })
        set.start()
    }


}