plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.artem.android.authfeature"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    val rxKotlinVersion = "3.0.1"
    val rxBindingVersion = "4.0.0"
    val rxJavaVersion = "3.1.8"
    val rxAndroidVersion = "3.0.2"
    val daggerVersion = "2.51.1"

    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation("io.reactivex.rxjava3:rxandroid:$rxAndroidVersion")
    implementation("io.reactivex.rxjava3:rxjava:$rxJavaVersion")
    implementation("com.jakewharton.rxbinding4:rxbinding:$rxBindingVersion")
    implementation("com.jakewharton.rxbinding4:rxbinding-appcompat:$rxBindingVersion")
    implementation("io.reactivex.rxjava3:rxkotlin:$rxKotlinVersion")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(":core"))
}