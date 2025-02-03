package com.bruno.clappy_bee.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.russhwolf.settings.ObservableSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.collections.minusAssign
import kotlin.compareTo
import kotlin.random.Random

const val SCORE_KEY = "SCORE"
data class Game(
    val screenWidth: Int = 0,
    val screenHeight: Int = 0,
    val gravity: Float = 0.8f,
    val beeRadius: Float = 30f,
    val beeJumpImpulse: Float = -12f,
    val beeMaxVelocity: Float = 25f,
    val pipeWidth: Float = 150f,
    val pipeVelocity: Float = 5f,
    val pipeGapSize: Float = 250f
): KoinComponent{
    val audioPlayer:AudioPlayer by inject()
    private val settings:ObservableSettings by inject()

    var status by mutableStateOf(GameStatus.Idle)
        private set
    var beeVelocity by mutableStateOf(0f)
        private set
    var bee by mutableStateOf(
        Bee(
            x = (screenWidth / 4).toFloat(),
            y = (screenHeight / 2).toFloat(),
            radius = beeRadius
        )
    )
    private set
    var pipePairs = mutableListOf<PipePair>()
    var currentScore by mutableStateOf(0)
    private set
    var bestScore by mutableStateOf(0)
        private set

    private var isFallingSoundPlayer = false


init {
    bestScore = settings.getInt(
        key = SCORE_KEY,
        defaultValue = 0
    )
    settings.addIntListener(
        key = SCORE_KEY,
        defaultValue = 0
    ){
        bestScore = it
    }
}

    fun start(){
        status = GameStatus.Started
        audioPlayer.playGameSoundInLoop()
    }
    fun gameOver(){
        status = GameStatus.Over
        audioPlayer.stopGameSound()
        saveScore()
        isFallingSoundPlayer = false
    }
    private fun saveScore(){
        if (bestScore < currentScore){
            settings.putInt(key = SCORE_KEY, value = currentScore)
            bestScore = currentScore
        }
    }
    fun jump(){
        beeVelocity = beeJumpImpulse
        audioPlayer.playJumpSound()
        isFallingSoundPlayer = false
    }
    fun restart(){
        resetBeePosition()
        resetScore()
        removePipes()
        start()
        isFallingSoundPlayer = false
    }
    private fun resetBeePosition(){
        bee = bee.copy(y = (screenHeight / 2).toFloat())
        beeVelocity = 0f
    }
    private fun removePipes(){
        pipePairs.clear()
    }
    private fun resetScore(){
        currentScore = 0
    }
    fun updateGameProgress(){
        pipePairs.forEach { pipe->
            if (isCollision(pipePair = pipe)){
                gameOver()
                return
            }
            if (!pipe.scored && bee.x > pipe.x + pipeWidth / 2){
                pipe.scored = true
                currentScore += 1
            }
        }
        if (bee.y < 0){
            stopTheBee()
            return

        }else if(bee.y > screenHeight){
            gameOver()
            return
        }

        beeVelocity = (beeVelocity + gravity)
            .coerceIn(-beeMaxVelocity,beeMaxVelocity)
        bee = bee.copy(y = (bee.y + beeVelocity))

            if(beeVelocity >(beeMaxVelocity / 1.1)){
                if(!isFallingSoundPlayer){
                    audioPlayer.playFallingSound()
                    isFallingSoundPlayer = true
                }
            }
        spawnPipes()
    }

    private fun spawnPipes() {
        pipePairs.forEach { it.x -= pipeVelocity }
        pipePairs.removeAll { it.x + pipeWidth < 0 }

        val isLandscape = screenWidth > screenHeight
        val spawnThreshold = if (isLandscape) screenWidth / 1.25
        else screenWidth / 2.0

        if (pipePairs.isEmpty() || pipePairs.last().x < spawnThreshold) {
            val initialPipeX = screenWidth.toFloat() + pipeWidth
            val topHeight = Random.nextFloat() * (screenHeight / 2)
            val bottomHeight = screenHeight - topHeight - pipeGapSize
            val newPipePair = PipePair(
                x = initialPipeX,
                y = topHeight + pipeGapSize / 2,
                topHeight = topHeight,
                bottomHeight = bottomHeight
            )
            pipePairs.add(newPipePair)
        }
    }

    private fun isCollision(pipePair: PipePair): Boolean{
        val  beeRightEdge = bee.x + bee.radius
        val beeLeftEdge = bee.x - bee.radius
        val pipeLeftEdge = pipePair.x - pipeWidth / 2
        val pipeRightEdge = pipePair.x + pipeWidth / 2
        val horizontalCollision = beeRightEdge > pipeLeftEdge
                && beeLeftEdge < pipeRightEdge

        val beeTopEdge = bee.y -bee.radius
        val beeBottomEdge = bee.y + bee.radius
        val gapTopEdge = pipePair.y - pipeGapSize /2
        val gapBottomEdge = pipePair.y + pipeGapSize / 2
        val beeInGap = beeBottomEdge > gapTopEdge
                && beeTopEdge < gapBottomEdge

        return horizontalCollision && !beeInGap
    }
    fun stopTheBee(){
        beeVelocity = 0f
        bee = bee.copy(y = 0f)
    }
    fun cleanUp(){
        audioPlayer.release()

    }
}