package com.potato

import com.potato.Constants.Platform.ANDROID
import com.potato.Constants.Platform.IOS
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

object Writer {
    fun writeFile(
        platform: String,
        locale: String,
        records: List<Record>
    ) {
        val fileName = getFilename(platform, locale)
        val dataWriter = BufferedWriter(FileWriter(fileName))

        try {
            // Pre tag header
            val preText = getPreText(platform)
            dataWriter.write(preText)
            dataWriter.flush()

            records.forEach {
                val formatted = getFormattedEntry(platform, it)
                dataWriter.write(formatted)
                dataWriter.flush()
            }

            // Post tag header
            val postText = getPostText(platform)
            dataWriter.write(postText)
            dataWriter.flush()

            println("$fileName finished")
        } catch (e: IOException) {
            System.err.println("Failed creating file: $fileName")
        } finally {
            dataWriter.close()
        }
    }

    private fun getFormattedEntry(platform: String, record: Record): String {
        val key = record.key
        //val value = StringEscapeUtils.escapeEcmaScript(record.value) // in case of escape special char needed
        val value = record.value
        val comment = record.comment
        val androidEntry: (Boolean) -> String = { untranslatable ->
            if (untranslatable) {
                "\t<string translatable=\"false\" name=\"$key\">$value</string>\n"
            } else {
                "\t<string name=\"$key\">$value</string>\n"
            }
        }
        return when (platform) {
            ANDROID -> androidEntry(record.untranslatable)
            IOS -> "\"$key\" = \"$value\";\n"
            else -> "$key: \"$value\",\n"
        }.let {
            if (comment?.isNotEmpty() == true) {
                "\n\t<!-- $comment -->\n$it"
            } else {
                it
            }
        }
    }

    private fun getFilename(platform: String, locale: String): String {
        val outputFolder = createOutputFolder(platform, locale)
        return when (platform) {
            ANDROID -> "strings.xml"
            IOS -> "Localizable.strings"
            else -> "strings_$locale.ts"
        }.let {
            "$outputFolder/$it"
        }
    }

    private fun createOutputFolder(platform: String, locale: String): String {
        val dirName = "exported"
        val folder = when (platform) {
            ANDROID -> "$dirName/values-$locale"
            IOS -> "$dirName/$locale.lproj"
            else -> dirName
        }
        File(folder).mkdirs()
        return folder
    }

    private fun getPreText(platform: String): String {
        return when (platform) {
            "ios" -> ""
            "android" -> "<resources>\n"
            else -> "const LOCALIZED_STRINGS = {\n"
        }
    }

    private fun getPostText(platform: String): String {
        return when (platform) {
            ANDROID -> "</resources>"
            IOS -> ""
            else -> "}\n\nexport default LOCALIZED_STRINGS;"
        }
    }
}