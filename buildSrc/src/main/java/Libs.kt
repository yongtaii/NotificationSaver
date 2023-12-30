/**
 * 의존성 클래스 파일
 * 버전과 명칭 정보를 관리한다.
 */
/**
 * 의존 라이브러리 정보
 */
object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.2"

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.5"
        const val material = "com.google.android.material:material:1.4.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"
        const val multiDex = "androidx.multidex:multidex:2.0.1"

        object Room {
            private const val version = "2.4.1"
            const val room = "androidx.room:room-runtime:$version"
            const val roomCompiler = "androidx.room:room-compiler:$version"
            const val roomRxjava = "androidx.room:room-rxjava2:$version"
            const val roomKtx = "androidx.room:room-ktx:$version"
            const val roomPaging = "androidx.room:room-paging:$version"
        }

        object Lifecycle {
            private const val lifecycleVersion = "2.3.1"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val java8 = "androidx.lifecycle:lifecycle-common-java8:2.2.0"
        }

        object Navigation {
            private const val version = "2.5.1"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Camera {
            private const val version = "1.0.1"
            const val camera2 = "androidx.camera:camera-camera2:$version"
            const val lifecycle = "androidx.camera:camera-lifecycle:$version"
            const val preview = "androidx.camera:camera-view:1.0.0-alpha27"
        }

        object WorkManager {
            const val rxJava2 = "androidx.work:work-rxjava2:2.7.1"
            const val kotlin = "androidx.work:work-runtime-ktx:2.7.1"
        }

        object Paging {
            const val pagin3 = "androidx.paging:paging-runtime:3.1.1"
        }
    }

    object Google {
        const val location = "com.google.android.gms:play-services-location:18.0.0"
        const val services = "com.google.gms:google-services:4.3.10"
        const val opensource = "com.google.android.gms:play-services-oss-licenses:17.0.0"
        const val ossPlugin = "com.google.android.gms:oss-licenses-plugin:0.10.4"
    }

    object Reativex {
        const val rxjava = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Kotlin {
        const val version = "1.8.0"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.2.1"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    }

    object Square {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.squareup.retrofit2:converter-gson:$version"
        const val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:4.9.1"
        const val adapterRxjava = "com.squareup.retrofit2:adapter-rxjava2:2.6.1"
        const val jackson = "com.fasterxml.jackson.core:jackson-core:2.11.4"
        const val jacksonAnnotation = "com.fasterxml.jackson.core:jackson-annotations:2.11.4"
        const val jacksonDatabind = "com.fasterxml.jackson.core:jackson-databind:2.11.4"

    }

    object Firebase {
        const val bomPlatform = "com.google.firebase:firebase-bom:29.0.1"
        const val remoteConfig = "com.google.firebase:firebase-config"
        const val crashlytics = "com.google.firebase:firebase-crashlytics"
        const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:2.8.1"
        const val analytics = "com.google.firebase:firebase-analytics"
        const val messaging = "com.google.firebase:firebase-messaging:22.0.0"
        const val dynamicLinks = "com.google.firebase:firebase-dynamic-links"
    }

    object Glide {
        private const val version = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object FlexBox {
        const val flexBoxLayout = "com.google.android.flexbox:flexbox:3.0.0"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:2.44"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.44"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:2.44"
    }

    /**
     * minSdk 에 상관없이 Java 8 API를 사용한다
     * https://developer.android.com/studio/write/java8-support#library-desugaring
     * */
    const val androidDesugarJdk = "com.android.tools:desugar_jdk_libs:1.0.9"

    /**
     * 툴팁
     * */
    const val skydovesToolTip= "com.github.skydoves:balloon:1.4.8"

    object Admob {
        private const val version = "22.6.0"
        const val ads = "com.google.android.gms:play-services-ads:$version"
    }

    object DataStore {
        private const val version = "1.0.0"
        const val preferences = "androidx.datastore:datastore-preferences:$version"
    }

    object OpenSource {
        const val plugin = "com.google.android.gms:oss-licenses-plugin:0.10.4"
        const val license = "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object JakeWharton {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

}