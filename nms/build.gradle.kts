subprojects {
    dependencies {
        compileOnly(files("libs/nms.jar"))
    }
}

project("v1_17_1") {
    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    }
}
