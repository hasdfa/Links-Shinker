package com.vadim.hasdfa.hrefshrinker.service

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*


/**
 * Created by rakshavadim on 20.10.2017.
 */
class DefaultKeyConverterServiceTest {

    val service: KeyConverterService = DefaultKeyConverterService()

    @Test
    fun givenIdMustBeConvertibleBothWays() {
        val rand = Random()
        for (i in 0..1000L) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)

            assertEquals(initialId, id)
        }
    }

}