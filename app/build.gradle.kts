plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.airvibes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.airvibes"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt{
            arguments{
                arg("room.schemaLocation","$projectDir/schemas")
            }
        }
        packagingOptions {
            resources.excludes.add("META-INF/*")
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true // Enable Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // Latest Compose compiler version
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
    // Core Android libraries
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Jetpack Compose dependencies
    val compose_version = "1.6.0"
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("androidx.compose.material3:material3:1.2.1")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    implementation( "androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material:material:1.5.0")
    implementation ("androidx.compose.ui:ui-tooling:1.5.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.0")


    // Navigation components
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Room components
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    // Firebase services
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-ml-modeldownloader")

    // TensorFlow Lite
    implementation("org.tensorflow:tensorflow-lite:2.12.0")

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation ("org.seleniumhq.selenium:selenium-java:4.1.2")
    implementation ("org.seleniumhq.selenium:selenium-chrome-driver:4.1.2")


    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    // Google Maps and Location
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.android.gms:play-services-location:19.0.1")

    // Lottie animations
    implementation("com.airbnb.android:lottie:6.1.0")

    // UI libraries
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    implementation("nl.joery.animatedbottombar:library:1.1.0")

    // Scalable DP for responsive design
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    // Google Places API
    implementation("com.google.android.libraries.places:places:3.1.0")

    // ViewPager
    implementation("androidx.viewpager2:viewpager2:1.0.0")
   //s
    implementation ("org.jsoup:jsoup:1.16.1")

    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    //chrometab
    implementation ("androidx.browser:browser:1.4.0")

    implementation ("com.google.android.material:material:1.9.0")



    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
