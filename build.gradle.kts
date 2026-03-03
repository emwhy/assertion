plugins {
    id("java")
    id("org.checkerframework").version("0.6.61")
}

group = "emw-assertion"
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
}

tasks.test {
    useTestNG()
}