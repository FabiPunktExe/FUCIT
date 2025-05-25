plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
    compileOnly("net.lenni0451.classtransform:core:1.14.1")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release = 21
    }

    processResources {
        filesMatching("paper-paper-plugin.yml") {
            expand(mapOf("version" to version))
        }
    }

    jar {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    runServer {
        dependsOn(rootProject.tasks.jar)
        minecraftVersion("1.21.5")
        workingDir = file("run")
        jvmArgs = listOf("-Djava.instrument.debug=true", "-javaagent:${rootProject.tasks.jar.get().outputs.files.first()}")
    }
}
