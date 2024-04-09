// localProperties에서 값 읽어오기
import java.util.Properties
import java.io.FileInputStream
var properties = Properties()
properties.load(FileInputStream("local.properties"))

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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

    // 버전 관리
    val paging_version = "3.2.1"
    val hilt_version = "2.51"
    val retrofit2_version = "2.9.0"

    // hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:$retrofit2_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2_version")

    // paging3
    implementation ("androidx.paging:paging-runtime:$paging_version")

    // glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    // corutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // indicator
    implementation("com.viewpagerindicator:library:2.4.1")

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

