
plugins {
    kotlin("multiplatform") version "1.3.61"
    id("com.github.raniejade.godot-kotlin") version "32.1.0"
}

repositories {
    jcenter()
    mavenCentral()
}

godot {
    libraries {
        val sample by creating {
            classes("main.MainScreen", "main.ChooseGameServer")
        }
    }
}
