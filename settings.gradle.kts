plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "todu"
include("src:main:utils")
findProject(":src:main:utils")?.name = "utils"
