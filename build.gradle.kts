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
}

detekt {
    autoCorrect = true
}
