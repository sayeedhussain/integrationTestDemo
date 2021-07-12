import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.8"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
	kotlin("kapt") version "1.4.32"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.squareup.retrofit2:retrofit:2.0.0")
	implementation("com.squareup.retrofit2:converter-gson:2.0.0")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("com.github.tomakehurst:wiremock:1.58")
}

sourceSets {
	create("testIntegration") {
		withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
			kotlin.srcDir("src/testIntegration/kotlin")
			resources.srcDir("src/testIntegration/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}

task<Test>("testIntegration") {
	description = "Runs the integration tests"
	group = "verification"
	testClassesDirs = sourceSets["testIntegration"].output.classesDirs
	classpath = sourceSets["testIntegration"].runtimeClasspath
	useJUnitPlatform()
}

sourceSets {
	create("testMockServerConfiguration") {
		withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
			kotlin.srcDir("src/testMockServerConfiguration/kotlin")
			resources.srcDir("src/testMockServerConfiguration/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}

task<Test>("testMockServerConfiguration") {
	description = "Runs the mock server configuration tests"
	group = "verification"
	testClassesDirs = sourceSets["testMockServerConfiguration"].output.classesDirs
	classpath = sourceSets["testMockServerConfiguration"].runtimeClasspath
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		outputs.upToDateWhen {false}
		showStandardStreams = true
	}
}
