repositories {
    maven { url = uri("https://oss.sonatype.org/content/repositories/public/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.azisaba.net/repository/maven-public/") }
}

dependencies {
    api(project(":common"))
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.azisaba.azipluginmessaging:api:4.0.4")
}
