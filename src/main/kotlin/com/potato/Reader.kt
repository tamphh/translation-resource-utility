package com.potato

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

object Reader {
    fun readFile(csvFile: String, separator: Char = ',') =
        csvReader {
            quoteChar = '"'
            delimiter = separator
        }.readAll(File(csvFile))
}