import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version Dependency.springBootVersion
    id("io.spring.dependency-management") version Dependency.dependencyManagementVersion
    kotlin("jvm") version Dependency.kotlinVersion
    kotlin("plugin.spring") version Dependency.kotlinVersion
    id("io.gitlab.arturbosch.detekt") version Dependency.detektVersion
}

version = Constant.VERSION
java.sourceCompatibility = JavaVersion.toVersion(Dependency.targetJvmVersion)

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Dependency.detektVersion}")
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = Dependency.targetJvmVersion
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


detekt {
    autoCorrect = true
}
