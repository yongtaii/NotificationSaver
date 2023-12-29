plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.rnd.jyong.notificationsaver.network"
    compileSdkVersion(33)

    defaultConfig {
        minSdkVersion(19)

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
//    implementation(Libs.AndroidX.appcompat)
//    implementation("com.google.android.material:material:1.10.0")
    // Test
    testImplementation (Libs.Test.junit)
    androidTestImplementation (Libs.Test.junitExt)
    androidTestImplementation (Libs.Test.espresso)
}