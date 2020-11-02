package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = if (fullName.equals("") || fullName.equals(" ")) null
        else fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var newName = payload.toLowerCase().replace(Regex("[а-я]")) {
            when (it.value) {
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е" -> "e"
                "ё" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и" -> "i"
                "й" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh'"
                "ъ" -> ""
                "ы" -> "i"
                "ь" -> ""
                "э" -> "e"
                "ю" -> "yu"
                "я" -> "ya"
                else -> it.value
            }
        }

        return newName.split(" ").joinToString(" ") { it.capitalize() }.replace(" ", divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstChar = if (firstName.equals(null)) null
        else if (firstName.equals("") || firstName.equals(" ")) null
        else firstName?.get(0)!!.toUpperCase()

        val secondChar = if (lastName.equals(null)) null
        else if (lastName.equals("") || lastName.equals(" ")) null
        else lastName?.get(0)!!.toUpperCase()

        return if (firstChar == null && secondChar == null) null
        else if (firstChar != null && secondChar == null) "$firstChar"
        else if (firstChar == null && secondChar != null) "$secondChar"
        else "$firstChar$secondChar"
    }
}
