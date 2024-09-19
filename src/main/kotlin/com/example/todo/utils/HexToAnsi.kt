package com.example.todo.utils

/**
 * Convert a hex color to an *ANSI* escape code.
 *
 * @param [hex] The hex color to convert.
 * @return The ANSI escape code.
 *
 */
fun hexToAnsi(hex: String): MutableMap<String, String> {
    val r = Integer.valueOf(hex.substring(1, 3), 16)
    val g = Integer.valueOf(hex.substring(3, 5), 16)
    val b = Integer.valueOf(hex.substring(5, 7), 16)
    return mutableMapOf<String, String>("value" to "\u001B[38;2;${r};${g};${b}m")
}