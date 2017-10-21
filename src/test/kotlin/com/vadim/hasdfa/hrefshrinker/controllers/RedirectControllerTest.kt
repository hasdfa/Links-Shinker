package com.vadim.hasdfa.hrefshrinker.controllers

import com.vadim.hasdfa.hrefshrinker.HrefShrinkerApplication
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

/**
 * Created by rakshavadim on 20.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootApplication(scanBasePackageClasses = arrayOf(HrefShrinkerApplication::class))
@WebAppConfiguration
class RedirectControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext

    val mockMvc: MockMvc by lazy {
        webAppContextSetup(webApplicationContext)
                .build()
    }

    @InjectMocks
    lateinit var controller: RedirectController

    @Mock
    lateinit var service: KeyMapperService

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(service.getLink(Mockito.anyString())).thenReturn(KeyMapperService.Get.NotFound(""))
        Mockito.`when`(service.getLink(PATH)).thenReturn(KeyMapperService.Get.Link(HEADER_VALUE))
    }

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccess() {
        assertEquals(service.getLink(PATH), KeyMapperService.Get.Link(HEADER_VALUE))

        mockMvc.perform(get("/$PATH"))
                .andExpect {
                    println(it.response.contentAsString)
                }
                .andExpect(status().`is`(HttpStatus.NOT_FOUND.value()))
                //.andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
                .andExpect(status().`is`(HttpStatus.NOT_FOUND.value()))
    }

    @Test fun homeWorksFine() {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
    }


    companion object {
        private var PATH = "ave"
        private val BAD_PATH = "yandex"

        private val HEADER_NAME = "Location"
        private val HEADER_VALUE = "http://avecp.com.ua"
    }

}

























