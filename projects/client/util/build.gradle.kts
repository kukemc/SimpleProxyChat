import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = parent?.version.toString()

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // Spigot/Paper.
    compileOnly("org.spigotmc", "spigot-api", "1.21.5-R0.1-SNAPSHOT")

    // bStats
    implementation("org.bstats", "bstats-bukkit", "3.0.2")
}

configure<ProcessResources>("processResources") {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}

tasks.withType<ShadowJar> {
    relocate("org.bstats", "com.beanbeanjuice.simpleproxychathelper.libs.org.bstats")
}
