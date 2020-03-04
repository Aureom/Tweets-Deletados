package br.ufu.kaiosouza.factory

import org.apache.commons.io.FileUtils
import sun.misc.IOUtils
import twitter4j.MediaEntity
import java.io.*
import java.net.URL


fun mediaEntitiesSerializer(medias: List<MediaEntity>): String {
    var serialized = ""
    for(media in medias){
        serialized = serialized + media.mediaURL + "🐓" // Não me pergunte pq coloquei um galo pra separar os links, só lembrei do galo do 'café?'
    }

    return serialized
}

fun mediaEntitiesUnserializer(mediaSerialized: String?): List<InputStream> {

    val urls = mediaSerialized!!.split("🐓")
    val inputStreamList = arrayListOf<InputStream>()

    for(url in urls) {
        try {
            inputStreamList.add(URL(url).openStream())
        }catch (e: Exception){}
    }

    return inputStreamList
}
