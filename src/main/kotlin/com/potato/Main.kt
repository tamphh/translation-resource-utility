package com.potato

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.potato.Constants.Language.DEFAULT
import com.potato.Constants.Platform.ANDROID
import com.potato.Parser.parseData
import com.potato.Reader.readFile
import com.potato.Writer.writeFile

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    runBlocking {
        parseData(
            readFile("src/main/resources/test.csv"),
            true
        )
        .forEach { (locale, records) ->
            launch(Dispatchers.IO) {
                val filteredRecords =
                    records.filterNot {
                        (!it.locale.equalsIgnoreCase(DEFAULT) && it.untranslatable) ||
                                it.value.isEmpty()
                    }
                writeFile(ANDROID, locale, filteredRecords)
            }
        }
    }
}