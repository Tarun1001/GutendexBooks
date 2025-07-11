package com.example.gutendexbooks.domain.usecases

import javax.inject.Inject

class GetBookFormatsUseCase @Inject constructor() {
    operator fun invoke(formats: Map<String, String>): String? {
        // Priority order: HTML, PDF, TXT
        val priorityFormats = listOf("text/html", "application/pdf", "text/plain")

        for (format in priorityFormats) {
            val url = formats.entries.find { entry ->
                entry.key.contains(format, ignoreCase = true) &&
                        !entry.value.contains(".zip", ignoreCase = true)
            }?.value

            if (url != null) return url
        }

        return null
    }
}