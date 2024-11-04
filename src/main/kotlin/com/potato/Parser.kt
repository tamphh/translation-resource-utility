package com.potato

import com.potato.Constants.Language.DEFAULT

object Parser {
    fun parseData (data: List<List<String>>, fillEmptyValue: Boolean = false): Map<String,List<Record>> {
        val dataList = mutableListOf<Record>()
        val localesMap = mutableMapOf<Int, String>()

        // must follow order:
        // comment;untranslatable;key;Default;es;{other languages}...
        // Default is English language
        val headerIndex = 0
        val commentIndex = 0
        val untranslatableIndex = 1
        val keyIndex = 2

        for ((lineIndex, line) in data.withIndex()) {
            if (lineIndex == headerIndex) {
                for ((fieldIndex, field) in line.withIndex()) {
                    localesMap[fieldIndex] = field
                }
                continue
            }
            var comment = ""
            var untranslatable = false
            var key = ""
            for ((fieldIndex, value) in line.withIndex()) {
                val locale = localesMap[fieldIndex] ?: continue
                when(fieldIndex) {
                    commentIndex -> comment = value
                    untranslatableIndex -> untranslatable = value.equalsIgnoreCase("true")
                    keyIndex -> key = value
                    else -> dataList.add(Record(key, value, locale, untranslatable, comment))
                }
            }
        }
        val result = dataList
            .filterNot { it.locale.isEmpty() }
            .groupBy { it.locale }
        if (fillEmptyValue)  {
            result.values
                .forEach { fillDefaultValue(result[DEFAULT], it) }
        }
        return result
    }

    private fun fillDefaultValue(englishRecords: List<Record>?, otherLanguageRecords: List<Record>) {
        if (englishRecords == null) {
            return
        }
        for ((index, record) in englishRecords.withIndex()) {
            otherLanguageRecords[index].apply {
               if (value.isEmpty()) {
                   value = record.value
               }
            }
        }
    }
}