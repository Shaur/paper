

plugins {
    kotlin("jvm") version "1.3.50"
}

repositories {
    mavenCentral()
}

buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
    }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:1.3.50")
    compile("org.jsoup:jsoup:1.12.1")
}

tasks.wrapper {
    gradleVersion = "5.6.2"
}