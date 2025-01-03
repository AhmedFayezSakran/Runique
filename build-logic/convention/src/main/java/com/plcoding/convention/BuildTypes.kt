package com.plcoding.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.repositories

//import com.android.utils.gradleLocalProperties
//import org.jetbrains.kotlin.gradle.tasks.CompilationErrorException


internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
//        buildscript {
//            repositories {
//                google()
//                mavenCentral()
//            }
//            dependencies {
//                classpath("com.android.tools.build:gradle:8.0.2")
//                classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
//            }
//        }

        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
            ?: throw IllegalStateException("API_KEY not found in gradle.properties")

        when(extensionType){

            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes{
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }

        }

    }
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"https://runique.pl-coding.com:8080\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}