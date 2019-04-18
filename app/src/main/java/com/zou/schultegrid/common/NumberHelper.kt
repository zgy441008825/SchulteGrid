package com.zou.schultegrid.common

import java.util.*


fun getRandomString(len: Int): String {

    val random: Random = Random()
    var sb: StringBuilder = java.lang.StringBuilder()
    for (i in 1..len) {
        sb.append(random.nextInt(9))
    }
    return sb.toString()

}