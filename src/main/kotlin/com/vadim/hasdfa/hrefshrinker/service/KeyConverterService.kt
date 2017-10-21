package com.vadim.hasdfa.hrefshrinker.service

import org.springframework.stereotype.Component

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Component
interface KeyConverterService {
    fun idToKey(id: Long): String
    fun keyToId(key: String): Long
}