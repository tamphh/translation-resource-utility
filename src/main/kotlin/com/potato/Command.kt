package com.potato

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.potato.Constants.Platform.ANDROID
import com.potato.Converter.convert

class ConvertCommand: CliktCommand() {
    private val file: String by option().prompt("Your CSV file")

    override fun run() {
        convert(file, ANDROID)
    }
}

class Command {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) = ConvertCommand().main(args)
    }
}