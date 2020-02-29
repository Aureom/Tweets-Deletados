package br.ufu.kaiosouza

import br.ufu.kaiosouza.database.tables.TweetStorage
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.session.config.account
import jp.nephy.penicillin.core.session.config.application
import jp.nephy.penicillin.core.session.config.token
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime


fun main() {
    val client = PenicillinClient {
        account {
            application("ConsumerKey", "ConsumerSecret")
            token("AccessToken", "AccessToken Secret")
        }
    }

    val database = Database.connect("jdbc:mysql://localhost:3306/twitter?useTimezone=true&serverTimezone=UTC", "com.mysql.cj.jdbc.Driver", "root", "")

    transaction (database) {
        SchemaUtils.create(TweetStorage)
        TweetStorage.insert  {
            it[contentText] = "teste"
            it[date] = DateTime.now()
        }
    }
}