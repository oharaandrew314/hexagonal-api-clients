plugins {
    kotlin("jvm") version "1.6.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:4.19.0.0"))

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-format-moshi")
}