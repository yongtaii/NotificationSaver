buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {

        classpath(Libs.androidGradlePlugin)

        // firebase
//        classpath("com.google.gms:google-services:4.3.4")
        classpath(Libs.Kotlin.gradlePlugin)


        // navigation
        classpath(Libs.AndroidX.Navigation.safeArgs)
        // hilt
        classpath(Libs.Hilt.hiltPlugin)
        // open source
        classpath(Libs.OpenSource.plugin)
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")

    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}