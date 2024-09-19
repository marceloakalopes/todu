plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "CSD215-Lab-1"
include("src:main:utils")
findProject(":src:main:utils")?.name = "utils"
