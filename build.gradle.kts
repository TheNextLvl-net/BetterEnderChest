plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "net.nonswag.tnl.enderchest"
version = "3.0.0"

repositories {
    mavenCentral()
    maven("https://repo.thenextlvl.net/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.36")
    compileOnly("net.thenextlvl.core:annotations:2.0.1")

    implementation("net.thenextlvl.core:api:3.1.10")

    annotationProcessor("org.projectlombok:lombok:1.18.36")
}


tasks {
    assemble {
        dependsOn(reobfJar)
    }
    shadowJar {
        minimize()
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
}

bukkit {
    name = "BetterEnderChest"
    main = "net.thenextlvl.enderchest.BetterEnderChest"
    apiVersion = "1.19"
    website = "https://thenextlvl.net"
    authors = listOf("NonSwag")
}
