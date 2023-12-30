plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.dagger.hilt.android") //hilt
    kotlin("kapt")
}

android {
    namespace = "com.rnd.jyong.notificationsaver.database"
    compileSdk = 33

    defaultConfig {
        minSdk = 19

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile("proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }


    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation (Libs.AndroidX.appcompat)
    implementation (Libs.AndroidX.legacy)
//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.10.0")
    // Test
    testImplementation (Libs.Test.junit)
    androidTestImplementation (Libs.Test.junitExt)
    androidTestImplementation (Libs.Test.espresso)

    // Room
    implementation(Libs.AndroidX.Room.room)
    kapt (Libs.AndroidX.Room.roomCompiler)
    implementation(Libs.AndroidX.Room.roomKtx)
    implementation(Libs.AndroidX.Room.roomPaging)

    // Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.hiltCompiler)

    // Paiging3
    implementation(Libs.AndroidX.Paging.pagin3)

    //coroutine
//    implementation (Libs.Kotlin.coroutine)
}