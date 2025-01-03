plugins {
    `kotlin-dsl`
}
group = "com.plcoding.runique.buildlogic"

dependencies {
    //specifies that these dependencies will only be available at compile-time and will not be bundled into the final
    //APK or library. This is commonly used for plugins or dependencies needed only during the build process.
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "runique.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "runique.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
    }
}