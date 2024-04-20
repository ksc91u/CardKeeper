plugins {
    id("com.android.application")
    id("kotlin-android")
//    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.firebase.crashlytics")
}


android {
    buildFeatures.buildConfig = true
    namespace = "com.awscherb.cardkeeper"

    signingConfigs {
//        register("debug") {
//            storeFile = file("../keystore/debug.keystore")
//        }

        register("release") {
            storeFile = file("../keystore/release.keystore")
            storePassword = "release"
            keyAlias = "release"
            keyPassword = "release"
        }
    }

    compileSdkVersion(34)

    defaultConfig {
        applicationId = "com.awscherb.cardkeeper"
        multiDexEnabled = true
        minSdkVersion(24)
        targetSdkVersion(34)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = 56
        versionName = "1.0"
    }


    flavorDimensions("env")

    productFlavors {
        create("dev") {
            dimension = "env"
            applicationId = "com.ksc91u.billscanner.dev"
            buildConfigField("Boolean", "PRODUCTION", "false")
        }
        create("prod") {
            dimension = "env"
            applicationId = "com.ksc91u.billscanner"
            buildConfigField("Boolean", "PRODUCTION", "true")
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    packagingOptions {
        exclude("META-INF/rxjava.properties")
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

//    sourceSets {
//        named("dev") {
//            java.srcDirs += "src/prod"
//        }
//    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("com.google.firebase:firebase-crashlytics:18.3.6")
    implementation("com.google.firebase:firebase-analytics:21.2.0")
    implementation("androidx.databinding:viewbinding:8.3.2")
    testImplementation("junit:junit:4.12")

    // Support libs
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.github.nisrulz:recyclerviewhelper:x1.0.0")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")
    implementation("com.google.firebase:firebase-core:21.1.1")

    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.google.mlkit:barcode-scanning:17.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-rxjava2:2.6.1")
    kapt("androidx.lifecycle:lifecycle-compiler:2.8.0-beta01")
    kapt("androidx.room:room-compiler:2.6.1")

    // Kotlin
    implementation(kotlin("stdlib"))

    // Rx
    implementation("io.reactivex.rxjava2:rxjava:2.2.4")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")

    implementation("com.jakewharton.rxbinding3:rxbinding:3.0.0-alpha2")
    implementation("com.jakewharton.rxbinding3:rxbinding-core:3.0.0-alpha2")
    implementation("com.jakewharton.rxbinding3:rxbinding-appcompat:3.0.0-alpha2")
    implementation("com.jakewharton.rxbinding3:rxbinding-material:3.0.0-alpha2")

    implementation("com.trello.rxlifecycle3:rxlifecycle:3.0.0")
    implementation("com.trello.rxlifecycle3:rxlifecycle-components:3.0.0")

    // Scanning
    implementation("com.google.zxing:core:3.3.3")
    implementation("com.journeyapps:zxing-android-embedded:3.6.0@aar")

    implementation("com.github.jenly1314.MLKit:mlkit-camera-core:1.3.0")
    implementation("com.github.jenly1314.MLKit:mlkit-common:1.3.0")
    implementation("com.github.jenly1314.MLKit:mlkit-barcode-scanning:1.3.0")

    // Dependency Injection
//    implementation("com.google.dagger:dagger:2.48.1")
//    kapt("com.google.dagger:dagger-compiler:2.48.1")

    implementation(platform("io.insert-koin:koin-bom:3.6.0-Beta2"))
    implementation("io.insert-koin:koin-core")
    implementation("io.insert-koin:koin-android")

    // Fabric.,m ];"

    // Dependencies for local unit tests
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-all:1.10.19")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("com.nhaarman:mockito-kotlin:1.5.0")
}

apply(plugin = "com.google.gms.google-services")