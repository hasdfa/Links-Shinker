package com.vadim.hasdfa.hrefshrinker.service

import org.springframework.stereotype.Component

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Component
interface KeyMapperService {
    interface Get {
        data class Link(val link: String): Get
        data class NotFound(val key: String): Get
    }

    fun getLink(key: String): Get
    fun add(link: String): String
}