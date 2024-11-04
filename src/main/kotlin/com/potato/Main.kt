package com.potato

import com.potato.Constants.Platform.ANDROID
import com.potato.Converter.convert

fun main() =
    convert(
        fileName = "src/main/resources/test.csv",
        platform = ANDROID,
        delimiter = ','
    )