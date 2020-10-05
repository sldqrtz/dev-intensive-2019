package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {
    return if (this.substringAfterLast(" ") == "")
        this.substring(0, value).substringBeforeLast(" ")
    else
        if(this.take(value).endsWith(" "))
            this.take(value-1).padEnd(value-1 + 3, '.')
        else
            this.take(value).padEnd(value + 3, '.')
}

fun String.stripHtml(): String {
    var str = this.replace("\\<.*?\\>".toRegex(), "")
    return str.replace("\\s+".toRegex(), " ")
}