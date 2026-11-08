import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Данные подписи: локально из keystore.properties, на сервере (CI) из переменных окружения
val keystoreProps = Properties().apply {
    val file = rootProject.file("keystore.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}
val keystoreFile = file("keystore.jks")
val storePasswordValue: String? = keystoreProps.getProperty("storePassword") ?: System.getenv("KEYSTORE_PASSWORD")
val keyAliasValue: String? = keystoreProps.getProperty("keyAlias") ?: System.getenv("KEY_ALIAS")
val keyPasswordValue: String? = keystoreProps.getProperty("keyPassword") ?: System.getenv("KEY_PASSWORD")
val canSignRelease = keystoreFile.exists() &&
    storePasswordValue != null && keyAliasValue != null && keyPasswordValue != null

android {
    namespace = "com.example.aquaapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.aquaapp"
        minSdk = 26
        targetSdk = 37
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        if (canSignRelease) {
            create("release") {
                storeFile = keystoreFile
                storePassword = storePasswordValue
                keyAlias = keyAliasValue
                keyPassword = keyPasswordValue
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            // Для теста оптимизацию (в AGP 9 вместо minifyEnabled) оставляем выключенной
            optimization {
                enable = false
            }
            if (canSignRelease) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}