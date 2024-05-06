// localProperties에서 값 읽어오기
import java.util.Properties
import java.io.FileInputStream
var properties = Properties()
properties.load(FileInputStream("local.properties"))

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {

    namespace = "com.example.tomicsandroidappclone"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.tomicsandroidappclone"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "WEBTOON_API_URL", properties.getProperty("WEBTOON_API_URL"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // retrofit2
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // paging3
    implementation(libs.paging.runtime)

    // glide
    implementation(libs.glide)

    // coroutine
    implementation(libs.coroutines.core)

    // viewmodel
    implementation(libs.viewmodel.ktx)

    // room
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // splash screen
    implementation(libs.splashscreen)

    // defalut
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
kapt {
    correctErrorTypes = true
}

