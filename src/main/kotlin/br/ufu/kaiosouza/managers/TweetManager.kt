package br.ufu.kaiosouza.managers

import br.ufu.kaiosouza.database.tables.TweetStorage
import br.ufu.kaiosouza.database.tables.TweetStorage.media
import br.ufu.kaiosouza.databaseManager
import br.ufu.kaiosouza.factory.mediaEntitiesUnserializer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import twitter4j.Paging
import twitter4j.StatusUpdate
import twitter4j.TwitterException
import twitter4j.TwitterFactory

class TweetManager {

    private val twitter = TwitterFactory.getSingleton()

    fun verifyNewTweets() {
        for (status in  twitter.getUserTimeline("Aureom_", Paging().count(1))) {
            databaseManager.addTweet(status)
        }
    }

    fun verifyDeletedTweets() {
        transaction (DatabaseManager.twitter) {
            SchemaUtils.create(TweetStorage)
            TweetStorage.selectAll().orderBy(TweetStorage.id to SortOrder.DESC).having { TweetStorage.deleted eq false }.limit(5).forEach {

                if(!checkIfTweetExist(it[TweetStorage.id])) { // Tweetar se o tweet for deletado
                    val status = StatusUpdate(it[TweetStorage.text])
                    val media = it[TweetStorage.media]

                    println("Media ${mediaEntitiesUnserializer(media)}")

                    if(!media.isNullOrEmpty()) {
                        for(inputStream in mediaEntitiesUnserializer(media)){
                            status.media(inputStream.toString().substring(0, 5), inputStream)
                        }
                    }

                    twitter.updateStatus(status)

                    println("Postado")

                    TweetStorage.update({TweetStorage.id eq it[TweetStorage.id]}){ it2 ->
                        it2[deleted] = true
                    }
                }
            }
        }
    }

    private fun checkIfTweetExist(id: Long): Boolean {
        return try {
            val status = twitter.showStatus(id)
            status != null
        } catch (e: TwitterException) { // Quando o tweet não existe ele cai no catch
            false
        }
    }


}