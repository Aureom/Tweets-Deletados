package br.ufu.kaiosouza.managers

import br.ufu.kaiosouza.database.tables.TweetStorage
import br.ufu.kaiosouza.factory.mediaEntitiesSerializer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import twitter4j.Status

class DatabaseManager {

    companion object Databases {
        val twitter = Database.connect("jdbc:mysql://localhost:3306/twitter?useTimezone=true&serverTimezone=UTC", "com.mysql.cj.jdbc.Driver", "root", "")
    }

    fun addTweet(status: Status){
        println("" + status.displayTextRangeStart + " | " + status.displayTextRangeEnd)
        transaction (twitter) {
            SchemaUtils.create(TweetStorage)
            TweetStorage.insertIgnore {
                it[id] = status.id
                it[text] = status.text.substring(0, status.displayTextRangeEnd)
                it[media] = mediaEntitiesSerializer(status.mediaEntities.toList())
                it[date] = DateTime(status.createdAt)
                it[deleted] = false
            }
        }
    }

}