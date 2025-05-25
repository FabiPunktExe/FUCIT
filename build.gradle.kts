plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
}

group = "de.fabiexe"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
    compileOnly("io.papermc:paperclip:3.0.4-SNAPSHOT")
    implementation("net.lenni0451.classtransform:core:1.14.1")
    implementation("net.lenni0451.classtransform:additionalclassprovider:1.14.0")
    implementation("net.lenni0451.classtransform:mixinstranslator:1.14.1")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release = 21
    }

    jar {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        manifest.attributes["Premain-Class"] = "de.fabiexe.fucit.Fucit"
        manifest.attributes["Can-Redefine-Classes"] = "true"
        manifest.attributes["Can-Retransform-Classes"] = "true"
        archiveFileName = "FUCIT.jar"
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
