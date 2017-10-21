package com.vadim.hasdfa.hrefshrinker.service

import org.springframework.stereotype.Component

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Component
class DefaultKeyConverterService: KeyConverterService {

    private final val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_".toCharArray()
    private final val charToLong = (0 until chars.size)
            .map { Pair(chars[it], it.toLong()) }
            .toMap()

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n%chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }

    override fun keyToId(key: String) = key
            .map { charToLong[it]!! }
            .fold(0L, {a, b -> a * chars.size + b })
}