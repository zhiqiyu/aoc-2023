plugins {
    kotlin("jvm") version "1.9.20"
    application
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

application {
    mainClass.set("Day02Kt")
}