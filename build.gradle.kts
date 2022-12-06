plugins {
    kotlin("jvm") version "1.8.0-Beta"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks {
    sourceSets {
        main {
            kotlin.srcDirs("src")
        }
    }
}
