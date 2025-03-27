import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
}

version = parent?.version.toString()

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Boosted YAML - https://mvnrepository.com/artifact/dev.dejvokep/boosted-yaml/
    implementation("dev.dejvokep", "boosted-yaml", "1.3.7")

    // Text Parsing
    implementation("net.kyori", "adventure-api", "4.17.0")  // Convert Velocity -> Bungee https://mvnrepository.com/artifact/net.kyori/adventure-api
    implementation("net.kyori", "adventure-text-minimessage", "4.17.0")  // Convert Velocity -> Bungee https://mvnrepository.com/artifact/net.kyori/adventure-text-minimessage
}

configure<ProcessResources>("processResources") { }

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    relocate("dev.dejvokep", "com.beanbeanjuice.simpleproxychat.proxy.libs.dev.dejvokep")
}
