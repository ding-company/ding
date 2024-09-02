import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Dependency.springBootVersion
    id("io.spring.dependency-management") version Dependency.dependencyManagementVersion
    kotlin("jvm") version Dependency.kotlinVersion
    kotlin("plugin.spring") version Dependency.kotlinVersion
    id("io.gitlab.arturbosch.detekt") version Dependency.detektVersion
    kotlin("plugin.jpa") version Dependency.kotlinVersion
}

group = "in.ding"
version = Constant.VERSION
java.sourceCompatibility = JavaVersion.toVersion(Dependency.targetJvmVersion)

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Dependency.detektVersion}")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:${Dependency.mySQLConnectorVersion}")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = Dependency.targetJvmVersion
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
    register<Copy>("copyDirectory") {
        from("skeleton")
        into("src/main/kotlin/in/ding")
        val appName = project.findProperty("appName") as String? ?: "app"
        doLast {
            val copiedFolder = file("src/main/kotlin/in/ding/app")
            if (copiedFolder.exists()) {
                val renamedFolder = file("src/main/kotlin/in/ding/$appName")
                copiedFolder.renameTo(renamedFolder)
            }
        }
    }
    bootBuildImage {
        imageName.set(System.getenv("IMAGE"))

        val profile = "${System.getProperties()["spring.profiles.active"]}"


        buildpacks.set(listOf(
            "urn:cnb:builder:paketo-buildpacks/java"
        ))
        var threadCount = "250"
        if (profile == "stg") {
            threadCount = "50"
        }
        environment.set(
            mapOf(
                "BP_JVM_VERSION" to Dependency.targetJvmVersion,
                "BPE_DELIM_JAVA_TOOL_OPTIONS" to " ",
                "BPE_SPRING_PROFILES_ACTIVE" to profile,
                "BPE_DD_VERSION" to Constant.VERSION,
                "BP_DATADOG_ENABLED" to "true",
                "BPE_LANG" to "en_US.utf8",
                "BPE_OVERRIDE_BPL_JVM_THREAD_COUNT" to threadCount,
            )
        )

        buildCache {
            volume {
                name.set("cache-${rootProject.name}.build")
            }
        }
    }

}

detekt {
    autoCorrect = true
}
