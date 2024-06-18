buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath(kotlin("gradle-plugin", version = "1.9.21"))
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.1")
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.21.0"
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }