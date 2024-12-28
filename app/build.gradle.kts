plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
}

apply(from = "dependencies.gradle")

android {
    namespace = "com.example.outiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.outiz"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
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
    // Récupération des dépendances du fichier dependencies.gradle
    implementation(project.deps["kotlinStdlib"] as String)
    implementation(project.deps["coroutines"] as String)

    // Room
    implementation(project.deps["roomRuntime"] as String)
    implementation(project.deps["roomKtx"] as String)
    ksp(project.deps["roomCompiler"] as String)

    // ViewModel & LiveData
    implementation(project.deps["lifecycleViewModel"] as String)
    implementation(project.deps["lifecycleLiveData"] as String)
    implementation(project.deps["lifecycleRuntime"] as String)

    // Navigation
    implementation(project.deps["navigationFragment"] as String)
    implementation(project.deps["navigationUI"] as String)

    // Preferences
    implementation(project.deps["preferenceKtx"] as String)

    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}