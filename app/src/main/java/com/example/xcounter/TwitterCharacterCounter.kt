package com.example.xcounter

object TwitterCharacterCounter {
    // Fixed length for URLs according to Twitter's rules
    private const val URL_LENGTH = 23

    // Regex pattern to detect URLs
    private val urlPattern = Regex("(https?://\\S+|www\\.\\S+)")

    // Function to count characters based on Twitter's rules
    fun countCharacters(tweetText: String): Int {
        var totalLength = 0

        // Find and count all URLs first
        val matches = urlPattern.findAll(tweetText)
        var adjustedText = tweetText

        for (match in matches) {
            totalLength += URL_LENGTH
            // Remove the URL from the text to avoid double counting
            adjustedText = adjustedText.replace(match.value, "")
        }

        // Count remaining characters
        adjustedText.forEach { char ->
            totalLength += if (char.isEmoji()) {
                // Count emoji as 1 character
                1
            } else {
                // Count normal characters as 1 character
                1
            }
        }

        return totalLength
    }

    // Extension function to check if a character is an emoji
    private fun Char.isEmoji(): Boolean {
        // Unicode range for emojis
        return this.code in 0x1F600..0x1F64F || // Emoticons
                this.code in 0x1F300..0x1F5FF || // Miscellaneous Symbols and Pictographs
                this.code in 0x1F680..0x1F6FF || // Transport and Map Symbols
                this.code in 0x1F700..0x1F77F || // Alchemical Symbols
                this.code in 0x1F780..0x1F7FF || // Geometric Shapes Extended
                this.code in 0x1F800..0x1F8FF || // Supplemental Arrows-C
                this.code in 0x1F900..0x1F9FF || // Supplemental Symbols and Pictographs
                this.code in 0x1FA00..0x1FA6F || // Chess Symbols
                this.code in 0x1FA70..0x1FAFF // Symbols and Pictographs Extended-A
    }
}
