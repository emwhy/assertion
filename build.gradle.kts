plugins {
    id("java")
    id("org.checkerframework").version("0.6.61")
}

group = "assertfire"
version = "release"

repositories {
    mavenCentral()
}

java {
    withJavadocJar()
}

checkerFramework {
    this.checkers = listOf(
        "org.checkerframework.checker.nullness.NullnessChecker",
        "org.checkerframework.checker.optional.OptionalChecker"
    )
}

dependencies {
    testImplementation("org.testng:testng:7.11.+")
    implementation("org.json:json:20251224")
}

tasks.test {
    useTestNG()
}

tasks.javadoc {
    options.overview = "src/main/javadoc/overview.html"
}