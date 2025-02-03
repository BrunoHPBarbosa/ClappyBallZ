
package com.bruno.clappy_bee.domain

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AudioPlayer {
    fun playGameOverSound()
    fun playJumpSound()
    fun playFallingSound()
    fun stopFallingSound()
    fun playGameSoundInLoop()
    fun stopGameSound()
    fun release()
}
val soundResLit = listOf(
    "files/falling.wav",
    "files/bam_dragon_ball.wav",
    "files/game_sound_db.wav",
    "files/jump.wav"
)


