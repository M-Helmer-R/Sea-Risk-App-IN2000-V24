plugins {
    id("com.android.application")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "no.uio.ifi.in2000.testgit"
    compileSdk = 34

    defaultConfig {
        applicationId = "no.uio.ifi.in2000.testgit"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Android Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    //Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Testing (Espresso, junit, Compose UI Testing)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Ktor
    implementation("io.ktor:ktor-client-core:2.3.8")
    implementation("io.ktor:ktor-client-cio:2.3.8")
    implementation("io.ktor:ktor-client-android:2.3.8")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.8")
    implementation("io.ktor:ktor-serialization-gson:2.3.8")

    //
    implementation ("com.google.code.gson:gson:2.10.1")


    //Room
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")

    // Compose Debugger
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //mapbox
    implementation("com.mapbox.maps:android:11.3.0")
    implementation("com.mapbox.extension:maps-compose:11.3.0")
    implementation ("com.mapbox.mapboxsdk:mapbox-sdk-services:6.15.0")


    //mapbox search


    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")
    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    


}