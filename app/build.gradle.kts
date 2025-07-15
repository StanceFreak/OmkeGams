import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.android.kapt)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    val localPropertiesFile = rootProject.file("local.properties")
    val localProperties = Properties()
    localProperties.load(localPropertiesFile.inputStream())
    namespace = "com.stancefreak.pihakaseng"
    compileSdk = 35

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        applicationId = "com.stancefreak.pihakaseng"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", localProperties["BASE_URL"].toString())
            buildConfigField("String", "API_KEY", localProperties["API_KEY"].toString())
            buildConfigField("String", "ACCESS_TOKEN", localProperties["ACCESS_TOKEN"].toString())
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", localProperties["BASE_URL"].toString())
            buildConfigField("String", "API_KEY", localProperties["API_KEY"].toString())
            buildConfigField("String", "ACCESS_TOKEN", localProperties["ACCESS_TOKEN"].toString())
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.compiler)
    implementation(libs.lifecycle.livedata)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)
    implementation(libs.glide)
    coreLibraryDesugaring(libs.desugar)
}