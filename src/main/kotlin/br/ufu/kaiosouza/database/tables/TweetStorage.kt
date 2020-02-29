package br.ufu.kaiosouza.database.tables

import org.jetbrains.exposed.sql.Table

object TweetStorage : Table("Tweets") {
    val contentText = varchar("content", 280)
    val contentImage = varchar("imageContent", 500).nullable() // URL para imagem
    val date = datetime("date")
}