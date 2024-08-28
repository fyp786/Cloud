plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.codeseyee.cloudcontacts"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codeseyee.cloudcontacts"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.androidx.room.common)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.camera.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")

    implementation ("com.google.android.material:material:1.9.0")

    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation ("com.google.zxing:core:3.4.1")
    implementation ("me.dm7.barcodescanner:zxing:1.9")
    implementation ("com.google.mlkit:barcode-scanning:17.0.2")


    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.14.0")
    implementation ("com.google.code.gson:gson:2.10")

    implementation ("com.github.yalantis:ucrop:2.2.6")
    implementation ("com.yalantis:ucrop:2.2.8")

    implementation ("com.google.android.gms:play-services-auth:21.0.1")


    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    // Retrofit and Gson
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation ("com.fasterxml.jackson.core:jackson-core:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.15.0")

    implementation ("com.github.yalantis:ucrop:2.2.6")

}

