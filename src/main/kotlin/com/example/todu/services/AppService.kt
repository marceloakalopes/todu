import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

/**
 * Data class representing a GitHub release
 */
data class GitHubRelease(val tagName: String)

/**
 * Check for updates on GitHub
 * @return true if an update is available, false otherwise
 */
fun checkForUpdates(): Boolean {
    val currentVersion = "1.0"
    val uri = URI("https://api.github.com/repos/yourusername/todu/releases/latest")
    val url = uri.toURL()

    try {
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        if (connection.responseCode == 200) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()

            // Use Jackson to map the JSON response to a Kotlin data class
            val objectMapper = jacksonObjectMapper()
            val release: GitHubRelease = objectMapper.readValue(response)

            if (release.tagName != currentVersion) {
                println("A new version (${release.tagName}) is available. Do you want to update? (y/n)")
                if (readLine()?.lowercase() == "y") {
                    performUpdate(release.tagName)
                } else {
                    println("Update skipped.")
                }
                return true
            } else {
                println("You are already using the latest version.")
            }
        } else {
            println("Failed to check for updates. HTTP response code: ${connection.responseCode}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

/**
 * Perform the update by downloading the latest release from GitHub
 * @param latestVersion the latest version available on GitHub
 */
fun performUpdate(latestVersion: String) {
    println("Updating to version $latestVersion...")

    val downloadUrl = "https://github.com/marceloakalopes/todu/releases/download/$latestVersion/todu.tar.gz"
    val targetFile = File("/usr/local/bin/todu")

    try {
        val url = URL(downloadUrl)
        url.openStream().use { input ->
            targetFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        targetFile.setExecutable(true)
        println("Update completed.")
    } catch (e: Exception) {
        e.printStackTrace()
        println("Update failed.")
    }
}

/**
 * Uninstall the todu app by removing the binary and data directory
 */
fun uninstallApp() {
    println("Are you sure you want to uninstall the todu app? (y/n)")
    val input = readLine()?.lowercase()

    if (input == "y") {
        val binaryFile = File("/usr/local/bin/todu")
        val toduDirectory = File(System.getProperty("user.home") + "/.todu")

        // Delete the binary
        if (binaryFile.exists()) {
            binaryFile.delete()
            println("todu binary has been removed.")
        } else {
            println("todu binary not found.")
        }

        // Delete the ~/.todu directory
        if (toduDirectory.exists()) {
            toduDirectory.deleteRecursively()  // Delete directory and its contents
            println("todu data directory has been removed.")
        } else {
            println("todu data directory not found.")
        }

        println("todu has been uninstalled.")
    } else {
        println("Uninstall canceled.")
    }
}

