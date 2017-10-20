package com.vadim.hasdfa.hrefshrinker.service

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Created by rakshavadim on 20.10.2017.
 */
class DefaultKeyMapperServiceTest {

    var service: KeyMapperService = DefaultKeyMapperService()

    companion object {
        private val KEY = "testKey"
        private val LINK = "https://apple.com"
        private val LINK_NEW = "https://google.com"
    }

    @Test
    fun clientCanAddNewKeyWithLink() {
        assertEquals(KeyMapperService.Add.Success(KEY, LINK), service.add(KEY, LINK))
        assertEquals(KeyMapperService.Get.Link(LINK), service.getLink(KEY))
    }

    @Test
    fun clientCanNotAddExistingKey() {
        service.add(KEY, LINK)
        assertEquals(KeyMapperService.Add.AlreadyExist(KEY), service.add(KEY, LINK_NEW))
        assertEquals(KeyMapperService.Get.Link(LINK), service.getLink(KEY))
    }

    @Test
    fun clientCanNotTakeLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }

}



















