plugins {
    kotlin("jvm") version "1.3.61"
    application
}

group = "br.ufu.kaiosouza"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("mysql:mysql-connector-java:8.0.19")

    compile("org.twitter4j:twitter4j-core:4.0.7")
    compile("org.jetbrains.exposed:exposed:0.17.7")
    compile("org.slf4j:slf4j-simple:1.7.30")
}

application {
    applicationName = "Tweets Deletados"
    group = "br.ufu.kaiosouza"
    mainClassName = "br.ufu.kaiosouza.TweetsDeletadosKt"
}

tasks {
    withType<Jar> {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
        val version = "1.0-SNAPSHOT"

        archiveName = "${application.applicationName}-$version.jar"
        from(configurations.runtime.get().map { if (it.isDirectory) it else zipTree(it) })
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}