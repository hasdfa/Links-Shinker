package com.vadim.hasdfa.hrefshrinker.service

import com.vadim.hasdfa.hrefshrinker.model.Link
import com.vadim.hasdfa.hrefshrinker.model.repositories.LinkRepository
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by rakshavadim on 20.10.2017.
 */
class DefaultKeyMapperServiceTest {

    @InjectMocks
    var service: KeyMapperService = DefaultKeyMapperService()

    companion object {
        private val KEY = "testKey"
        private val LINK_A = "http://apple.com"
        private val LINK_B = "https://google.com"

        private val ID_A = 10000000L
        private val KEY_A = "abc"

        private val ID_B = 10000001L
        private val KEY_B = "cde"
    }

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repository: LinkRepository

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)

        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)

        val link1 = Link(LINK_A, ID_A)
        val link2 = Link(LINK_B, ID_B)

        Mockito.`when`(repository.insert(link1)).thenReturn(link1)
        Mockito.`when`(repository.insert(link2)).thenReturn(link2)

        Mockito.`when`(repository.findById(ID_A)).thenReturn(Optional.of(link1))
        Mockito.`when`(repository.findById(ID_B)).thenReturn(Optional.of(link2))
    }

    @Test
    fun clientCanAddLinks() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))

        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))

        assertNotEquals(keyA, keyB)
    }

    @Test
    fun clientCanNotTakeLinkIfKeyIsNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }

}



















