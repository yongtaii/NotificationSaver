buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {

        classpath(Libs.androidGradlePlugin)

        // firebase
        classpath(Libs.Firebase.googleService)

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