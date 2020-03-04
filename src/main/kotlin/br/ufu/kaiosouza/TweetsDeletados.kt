package br.ufu.kaiosouza

import br.ufu.kaiosouza.managers.DatabaseManager
import br.ufu.kaiosouza.managers.TweetManager
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

val databaseManager = DatabaseManager()
val tweetManager = TweetManager()

fun main() {
    val timer = Timer()
    val verifyNewTweetsTask = object: TimerTask() {
        override fun run() {
            tweetManager.verifyNewTweets()
        }
    }
    val verifyDeletedTweetsTask = object: TimerTask() {
        override fun run() {
            tweetManager.verifyDeletedTweets()
        }
    }

    timer.schedule(verifyNewTweetsTask, 0, TimeUnit.SECONDS.toMillis(30))
    timer.schedule(verifyDeletedTweetsTask, 0, TimeUnit.MINUTES.toMillis(5))

    //exitProcess(0)
}