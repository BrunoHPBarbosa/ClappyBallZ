
package com.bruno.clappy_bee.domain

import android.content.Context
import android.media.SoundPool
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import clappybee.composeapp.generated.resources.Res
import com.bruno.clappy_bee.R
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AudioPlayer(context: Context) {

    private val loppingPlayer = ExoPlayer.Builder(context).build()
    @OptIn(ExperimentalResourceApi::class)
    private val mediaItem = soundResLit.map {
        MediaItem.fromUri(Res.getUri(it))
    }
    private val soundPool  = SoundPool.Builder()
        .setMaxStreams(3)
        .build()

    private val jumpSound = soundPool.load(context, R.raw.jump,2)
    private val fallingSound = soundPool.load(context, R.raw.falling,1)
    private var fallingSoundId:Int = 0
    private val gameOverSoundId = soundPool.load(context, R.raw.bam_dragon_ball, 1)


    init {
        loppingPlayer.prepare()
    }

    actual  fun playGameOverSound(){
        stopFallingSound()
        soundPool.play(gameOverSoundId, 1f,1f,0,0,1f)
    }
    actual fun playJumpSound(){
        stopFallingSound()
        soundPool.play(jumpSound, 1f,1f,0,0,1f)
    }
    actual fun playFallingSound(){
        fallingSoundId = soundPool.play(fallingSound, 1f,1f,0,0,1f)
    }
    actual fun stopFallingSound(){
        soundPool.stop(fallingSoundId)
    }
    actual fun playGameSoundInLoop(){
        loppingPlayer.repeatMode = Player.REPEAT_MODE_ONE
        loppingPlayer.setMediaItem(mediaItem[2])
        loppingPlayer.play()
    }
    actual fun stopGameSound(){
        loppingPlayer.pause()
        playGameOverSound()

    }
    actual fun release(){
        loppingPlayer.stop()
        loppingPlayer.clearMediaItems()
        soundPool.release()

        soundPool.release()
    }
}