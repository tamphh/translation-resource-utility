package com.potato

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.potato.Constants.Platform.ANDROID
import com.potato.Converter.convert

class ConvertCommand: CliktCommand() {
    private val file by option("-f", "--file")
    private val platform by option("-os", "--platform")
    private val delimiter by option("-d", "--delimiter").default(",")

    /*
        Example:
        ./gradlew run --args="-f src/main/resources/sample.csv -os android -d ;"
        ./gradlew run --args="-f src/main/resources/test.csv -os ios -d ,"
    */
    override fun run() =
        convert(
            fileName = file!!,
            platform = platform!!.lowercase(),
            delimiter = delimiter.toCharArray().first()
        )
}

class Command {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = ConvertCommand().main(args)
    }
}