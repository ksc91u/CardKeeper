buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath(kotlin("gradle-plugin", version = "1.9.21"))
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
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
        maven("https://maven.fabric.io/public")
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
 }