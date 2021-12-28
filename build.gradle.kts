plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("org.jmailen.kotlinter") version "3.7.0"
}

apply(from = "readme.gradle.kts")

val generateReadme: () -> String by project

allprojects {
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "org.jmailen.kotlinter")

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/groups/public/")
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}

task("updateSnippets") {
    group = "snippets"
    doLast {
        val readme = projectDir.resolve("README.md")
        readme.writeText(generateReadme())
    }
}

task("checkSnippets") {
    group = "snippets"
    doLast {
        val readme = projectDir.resolve("README.md")
        if (readme.readText() != generateReadme()) {
            error("README.md: The list does not match the snippet.")
        }
    }
}
