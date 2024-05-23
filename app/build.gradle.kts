import com.android.build.api.dsl.ApplicationDefaultConfig

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
}
@Suppress("UnstableApiUsage")
android {
    namespace = "com.geniouscraft.topappandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.geniouscraft.topappandroid"
        minSdk = 24
        targetSdk = 34
        versionCode = 11
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

    }

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = "Start123#"
            storeFile = file("D:\\cartelApps\\TopAppAndroid\\app\\key.jks")
            storePassword = "Start123#"
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
            signingConfig = signingConfigs.getByName("release")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
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

    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation(platform("com.google.firebase:firebase-bom:31.5.0"))

    //project dependencies

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2") // for cache
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    implementation("com.github.togisoft:jetpack_compose_country_code_picker:1.1.4")

    implementation("com.airbnb.android:lottie-compose:6.0.0")

    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("com.google.accompanist:accompanist-drawablepainter:0.31.0-alpha")

    implementation("com.android.billingclient:billing-ktx:6.0.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.google.android.gms:play-services-ads:22.5.0")

}

fun config(k: String) = "${project.properties[k]}"
