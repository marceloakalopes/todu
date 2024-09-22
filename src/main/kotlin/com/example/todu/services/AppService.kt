import java.net.HttpURLConnection
import java.net.URI
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Check for updates on GitHub
 *
 * @return true if a new version is available, false otherwise
 */
fun checkForUpdates(): Boolean {
    val currentVersion = "v0.1.0"  // Your current todu version
    val uri = URI("https://api.github.com/repos/marceloakalopes/todu/releases/latest")

    try {
        // Convert URI to URL
        val url = uri.toURL()
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connect()

        if (connection.responseCode == 200) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.readText()
            reader.close()

            // Extract tag_name (version) from the response
            val latestVersion = Regex("\"tag_name\":\\s*\"([^\"]+)\"").find(response)?.groupValues?.get(1) ?: "unknown"

            println("Latest version: $latestVersion")

            if (latestVersion != currentVersion) {
                println("A new version of todu ($latestVersion) is available.")
                println("Use 'brew upgrade todu' to update.")
                return true
            } else {
                println("todu is up-to-date.")
            }
        } else {
            println("Failed to check for updates. HTTP response code: ${connection.responseCode}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}
