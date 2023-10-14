val ktorVersion = "2.3.5"
val logbackVersion = "1.2.11"

dependencies {
    api(project(":common"))
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("redis.clients:jedis:5.0.1")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
}

tasks {
    shadowJar {

    }
}
