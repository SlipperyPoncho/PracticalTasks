plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.artem.android.task1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.artem.android.task1"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val roomVersion = "2.6.1"
    val okHttpVersion = "4.12.0"
    val glideVersion = "4.16.0"
    val coroutinesVersion = "1.8.1-Beta"
    val rxKotlinVersion = "3.0.1"
    val rxBindingVersion = "4.0.0"
    val rxJavaVersion = "3.1.8"
    val rxAndroidVersion = "3.0.2"
    val kotlinxDateTimeVersion = "0.5.0"
    val recyclerViewVersion = "1.3.2"
    val splashScreenVersion = "1.0.1"
    val retrofitVersion = "2.9.0"
    val gsonVersion = "2.10.1"
    val jUnitVersion = "4.13.2"
    val coordinatorLayoutVersion = "1.2.0"
    val constraintLayoutVersion = "2.1.4"
    val androidMaterialVersion = "1.11.0"
    val appcompatVersion = "1.6.1"
    val activityComposeVersion = "1.8.2"
    val lifecycleRuntimeKtxVersion = "2.7.0"
    val coreKtxVersion = "1.9.0"
    val daggerVersion = "2.51.1"
    val fragmentVersion = "1.7.1"

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("com.google.android.material:material:$androidMaterialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.coordinatorlayout:coordinatorlayout:$coordinatorLayoutVersion")
    testImplementation("junit:junit:$jUnitVersion")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")
    implementation ("com.google.code.gson:gson:$gsonVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion")
    implementation("androidx.core:core-splashscreen:$splashScreenVersion")
    implementation("androidx.recyclerview:recyclerview:$recyclerViewVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion")
    implementation("io.reactivex.rxjava3:rxandroid:$rxAndroidVersion")
    implementation("io.reactivex.rxjava3:rxjava:$rxJavaVersion")
    implementation("com.jakewharton.rxbinding4:rxbinding:$rxBindingVersion")
    implementation("com.jakewharton.rxbinding4:rxbinding-appcompat:$rxBindingVersion")
    implementation("io.reactivex.rxjava3:rxkotlin:$rxKotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
}