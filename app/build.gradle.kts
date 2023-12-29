apply plugin: 'com.android.application'
// firebase
apply plugin: 'com.google.gms.google-services'

android {
    compileSdkVersion 29
    defaultConfig {
        applicationId "com.rnd.jyong.notificationsaver"
        minSdkVersion 19
        targetSdkVersion 29
        versionCode 18
        versionName "2.1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled true
    }
    buildTypes {
        release {
            minifyEnabled true
//            multiDexKeepFile file('multidex-config.txt')
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        debug{
//            multiDexKeepFile file('multidex-config.txt')
        }
    }

    dataBinding {
        enabled = true
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    // material design (cardview)
    implementation 'com.google.android.material:material:1.2.1'
    // Room
    implementation 'android.arch.persistence.room:runtime:1.1.1'
    annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'
    // RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    // gson
    implementation 'com.google.code.gson:gson:2.8.5'
    // admob
    implementation 'com.google.android.gms:play-services-ads:19.4.0'
    //firebase
    implementation platform('com.google.firebase:firebase-bom:25.12.0')
    implementation 'com.google.firebase:firebase-analytics'
    // firebase -realtime database
    implementation 'com.google.firebase:firebase-database'
    // multidex
    implementation 'com.android.support:multidex:1.0.3'
    // firebase - storage
    implementation 'com.google.firebase:firebase-storage'
    implementation 'com.firebaseui:firebase-ui-storage:6.4.0'
    // glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'


}