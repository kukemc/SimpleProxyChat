import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
}

version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    // Velocity
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

    // Bungeecord
    compileOnly("net.md-5", "bungeecord-api", "1.20-R0.2")

    // bStats
    implementation("org.bstats", "bstats-velocity", "3.0.2")
    implementation("org.bstats", "bstats-bungeecord", "3.0.2")
}

configure<ProcessResources>("processResources") {
    filesMatching("bungee.yml") {
        expand(project.properties)
    }
    filesMatching("velocity-plugin.json") {
        expand(project.properties)
    }
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
//    relocate("net.kyori", "com.beanbeanjuice.simpleproxychat.libs.net.kyori")  // check
//    relocate("net.dv8tion", "com.beanbeanjuice.simpleproxychat.libs.net.dv8tion")
//    relocate("dev.dejvokep", "com.beanbeanjuice.simpleproxychat.libs.dev.dejvokep")
    relocate("org.bstats", "com.beanbeanjuice.simpleproxychat.libs.org.bstats")
//    relocate("joda-time", "com.beanbeanjuice.simpleproxychat.libs.joda-time")  // check
//    relocate("org.apache.maven", "com.beanbeanjuice.simpleproxychat.libs.org.apache.maven")  // check
}
