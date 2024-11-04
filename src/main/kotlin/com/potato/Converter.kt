package com.potato

import com.potato.Constants.Language.DEFAULT
import com.potato.Constants.Language.ENGLISH
import com.potato.Parser.parseData
import com.potato.Reader.readFile
import com.potato.Writer.writeFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Converter {
    fun convert(fileName: String, platform: String, delimiter: Char = ',') {
        runBlocking {
            parseData(
                readFile(fileName, delimiter),
                false
            )
                .forEach { (locale, records) ->
                    launch(Dispatchers.IO) {
                        val filteredRecords =
                            records.filterNot {
                                (!isDefaultLanguage(it.locale) && it.untranslatable) ||
                                        it.value.isEmpty()
                            }
                        writeFile(platform, locale, filteredRecords)
                    }
                }
        }
    }

    private fun isDefaultLanguage(locale: String): Boolean {
       return locale.equalsIgnoreCase(DEFAULT) || locale.equalsIgnoreCase(ENGLISH)
    }
}