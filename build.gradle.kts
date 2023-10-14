plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `java-library`
}

allprojects {
    group = "net.azisaba.paypay"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.serialization")
        plugin("com.github.johnrengelman.shadow")
        plugin("java-library")
    }

    repositories {
        mavenCentral()
    }

    tasks {
        test {
            useJUnitPlatform()
        }

        processResources {
            filteringCharset = "UTF-8"
            from(sourceSets.main.get().resources.srcDirs) {
                include("**")

                val tokenReplacementMap = mapOf(
                    "version" to project.version,
                    "name" to project.rootProject.name,
                )

                filter<org.apache.tools.ant.filters.ReplaceTokens>("tokens" to tokenReplacementMap)
            }

            duplicatesStrategy = DuplicatesStrategy.INCLUDE

            from(projectDir) { include("LICENSE") }
        }

        shadowJar {
            relocate("jp.ne.paypay", "net.azisaba.paypay.lib.jp.ne.paypay")
            relocate("okio", "net.azisaba.paypay.lib.okio")
            relocate("io.swagger", "net.azisaba.paypay.lib.io.swagger")
            relocate("com.auth0", "net.azisaba.paypay.lib.com.auth0")
            relocate("com.fasterxml", "net.azisaba.paypay.lib.com.fasterxml")
            relocate("com.google", "net.azisaba.paypay.lib.com.google")
            relocate("com.squareup", "net.azisaba.paypay.lib.com.squareup")
            relocate("com.sun", "net.azisaba.paypay.lib.com.sun")
            relocate("jakarta", "net.azisaba.paypay.lib.jakarta")
            relocate("kotlin", "net.azisaba.paypay.lib.kotlin")
            relocate("kotlinx", "net.azisaba.paypay.lib.kotlinx")
            relocate("org.apache", "net.azisaba.paypay.lib.org.apache")
            relocate("org.hibernate", "net.azisaba.paypay.lib.org.hibernate")
            relocate("org.intellij", "net.azisaba.paypay.lib.org.intellij")
            relocate("org.jetbrains", "net.azisaba.paypay.lib.org.jetbrains")
            relocate("org.jboss", "net.azisaba.paypay.lib.org.jboss")
            mergeServiceFiles()
            archiveBaseName.set("${parent?.name}-${project.name}")
        }
    }

    kotlin {
        jvmToolchain(8)
    }
}
