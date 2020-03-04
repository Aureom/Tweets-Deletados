package br.ufu.kaiosouza.database.tables

import org.jetbrains.exposed.sql.Table

object TweetStorage : Table("Tweets") {
    val id = long("id").primaryKey()
    val text = varchar("text", 280)
    val media = varchar("media", 500).nullable() // URL para imagem
    val date = datetime("date")
    val deleted = bool("deleted").default(false)
}