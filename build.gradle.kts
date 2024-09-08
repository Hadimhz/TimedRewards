plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

group = "dev.hadimhz.rewards"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.aikar.co/content/groups/aikar/") // ACF

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PAPI

}

dependencies {

    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    compileOnly("me.clip:placeholderapi:2.11.2")


    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")

    implementation(fileTree(rootDir.path + "/lib"))

}

tasks.shadowJar {
    relocate("gg.supervisor", "dev.hadimhz.rewards.supervisor")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

