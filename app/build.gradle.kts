import com.android.build.api.dsl.ApplicationDefaultConfig

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}
@Suppress("UnstableApiUsage")
android {
    namespace = "com.geniouscraft.topappandroid"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.geniouscraft.topappandroid"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {

        getByName("debug") {
            isDebuggable = true
            buildConfigField("String", "BASE_URL", config("baseUrl"))
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", config("baseUrl"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }
}



dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.material:material:1.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")


    //project dependencies

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2") // for cache
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    implementation ("androidx.compose.material:material-icons-extended:1.4.0")

    implementation ("com.github.togisoft:jetpack_compose_country_code_picker:1.1.4")

    implementation ( "com.airbnb.android:lottie-compose:6.0.0")

    implementation ("androidx.palette:palette:1.0.0")

}

fun config(k: String) = "${project.properties[k]}"
