plugins {
    id ("com.android.application")
    id ("androidx.navigation.safeargs") // navigation
    id("com.google.dagger.hilt.android") //hilt
    id("com.google.android.gms.oss-licenses-plugin") //opensource
    id("com.google.gms.google-services") //firebase
    id("com.google.firebase.crashlytics") //crashlytics
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.compileSdk
    buildToolsVersion = Versions.buildTools

    signingConfigs {
        create("yongkey") {
            keyAlias = "yongkey"
            keyPassword = "12qwaszx12"
            storeFile = file("../yongkey(12qwaszx12).jks")
            storePassword = "12qwaszx12"
            enableV2Signing = true
        }
    }

    defaultConfig {
        applicationId = "com.rnd.jyong.notificationsaver"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("yongkey")
            proguardFile("proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }


    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }


//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}

dependencies {

    // AndroidX Library
    implementation (Libs.AndroidX.appcompat)
    implementation (Libs.AndroidX.material)
    implementation (Libs.AndroidX.constraintLayout)
    implementation (Libs.AndroidX.recyclerview)
    implementation (Libs.AndroidX.legacy)
    implementation (Libs.AndroidX.Lifecycle.extensions)
    implementation (Libs.AndroidX.Lifecycle.livedata)
    implementation (Libs.AndroidX.Lifecycle.viewModel)
    implementation (Libs.AndroidX.Lifecycle.java8)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.10.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("androidx.legacy:legacy-support-v4:1.0.0")
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Test
    testImplementation (Libs.Test.junit)
    androidTestImplementation (Libs.Test.junitExt)
    androidTestImplementation (Libs.Test.espresso)

    // Room
    implementation(Libs.AndroidX.Room.room)
    annotationProcessor(Libs.AndroidX.Room.roomCompiler)
    implementation(Libs.AndroidX.Room.roomKtx)

    // gson
    implementation (Libs.Square.gson)
    // admob
    implementation (Libs.Admob.ads)

    // Glide Libs
    implementation (Libs.Glide.glide)
    kapt (Libs.Glide.compiler)

    implementation(project(":data:database"))
    implementation(project(":data:datastore"))

    // Navigation Component
    implementation (Libs.AndroidX.Navigation.fragment)
    implementation (Libs.AndroidX.Navigation.ui)
//    implementation (Libs.AndroidX.Navigation.safeArgs)

    // Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hiltCompiler)

    // Paiging3
    implementation(Libs.AndroidX.Paging.pagin3)

    //coroutine
    implementation (Libs.Kotlin.coroutine)

    //datastore
    implementation (Libs.DataStore.preferences)

    // opensource
    implementation (Libs.OpenSource.license)

    // logger
    implementation (Libs.JakeWharton.timber)

    //firebase
    implementation(platform(Libs.Firebase.bomPlatform))
    implementation(Libs.Firebase.remoteConfig)
    implementation(Libs.Firebase.crashlytics)
    implementation(Libs.Firebase.analytics)


//    //firebase
//    implementation platform("com.google.firebase:firebase-bom:25.12.0")
//    implementation ("com.google.firebase:firebase-analytics")
//    // firebase -realtime database
//    implementation ("com.google.firebase:firebase-database")
//    // multidex
//    implementation ("com.android.support:multidex:1.0.3")
//    // firebase - storage
//    implementation ("com.google.firebase:firebase-storage")
//    implementation ("com.firebaseui:firebase-ui-storage:6.4.0")



}
