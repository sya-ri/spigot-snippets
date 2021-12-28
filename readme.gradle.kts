fun _generateReadme(): String {
    val srcDirs = setOf(
        projectDir.resolve("src/main/kotlin"),
        project(":nms:v1_17_1").projectDir.resolve("src/main/kotlin")
    )
    val files = srcDirs.flatMap { it.listFiles().orEmpty().toList() }
    return buildString {
        appendLine("# spigot-snippets")
        appendLine()
        appendLine("[![Kotlin](https://img.shields.io/badge/kotlin-1.6.10-blue.svg?logo=kotlin)](http://kotlinlang.org)")
        appendLine("[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)")
        appendLine()
        appendLine("## List")
        files.forEach { file ->
            val name = file.nameWithoutExtension
            val link = file.toRelativeString(projectDir)
            appendLine("- [$name]($link)")
        }
    }
}

val generateReadme by extra {
    ::_generateReadme
}
