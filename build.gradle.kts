import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Dependency.springBootVersion
    id("io.spring.dependency-management") version Dependency.dependencyManagementVersion
    id("io.gitlab.arturbosch.detekt") version Dependency.detektVersion
    id("com.epages.restdocs-api-spec") version Dependency.restdocsapiSpecVersion
    kotlin("jvm") version Dependency.kotlinVersion
    kotlin("plugin.spring") version Dependency.kotlinVersion
    kotlin("plugin.jpa") version Dependency.kotlinVersion
    kotlin("plugin.allopen") version Dependency.kotlinVersion
    kotlin("plugin.noarg") version Dependency.kotlinVersion
    kotlin("kapt") version Dependency.kotlinVersion
    jacoco
}
group = "in.ding"
version = Constant.VERSION
java.sourceCompatibility = JavaVersion.toVersion(Dependency.targetJvmVersion)

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Dependency.springCloudVersion}")
    }
}

apply(plugin = "kotlin-kapt")
dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // MVC
    implementation("org.springframework.boot:spring-boot-starter-web")

    // CACHE
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:${Dependency.mySQLConnectorVersion}")

    // query logging 로컬에서만 사용하기 위해 주석 처리 합니다.
//    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

    // Querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // logback
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")

    // sentry
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:${Dependency.sentryVersion}")

    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // OpenFeign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-okhttp:12.2")
    implementation("io.github.openfeign:feign-jackson:12.2")

    // detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Dependency.detektVersion}")

    // Apache Commons Codec
    implementation("commons-codec:commons-codec:1.15")

    // swagger-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    // prometheus
    implementation("io.micrometer:micrometer-registry-prometheus:1.12.2")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:${Dependency.kotestVersion}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${Dependency.kotestSprintExtensions}")
    testImplementation("io.mockk:mockk:${Dependency.mockkVersion}")
    testImplementation("com.ninja-squad:springmockk:${Dependency.springMockkVersion}")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.6.2")

    // restdocs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:${Dependency.restdocsapiSpecVersion}")
    testImplementation("com.epages:restdocs-api-spec-model:${Dependency.restdocsapiSpecVersion}")
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
        from("skeleton/app")
        into("src/main/kotlin/in/ding/app")
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

kapt {
    correctErrorTypes = true
}
detekt {
    autoCorrect = true
}
