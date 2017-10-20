package com.vadim.hasdfa.hrefshrinker.controllers

import com.vadim.hasdfa.hrefshrinker.HrefShrinkerApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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

    lateinit var mockMvc: MockMvc

    @Before
    fun init() {
        mockMvc = webAppContextSetup(webApplicationContext)
                .build()
    }

    private val PATH = "ave"

    private val HEADER_NAME = "Location"
    private val HEADER_VALUE = "http://avecp.com.ua"

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccess() {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"key\":\"$PATH\", \"link\": \"$HEADER_VALUE\"}")
                ).andExpect(status().`is`(HttpStatus.ACCEPTED.value()))
                .andExpect {
                    println(it.response.contentAsString)
                    if (it.response.contentAsString != "Added: $HEADER_VALUE")  throw Exception()
                }

        mockMvc.perform(get("/$PATH"))
                .andExpect(status().`is`(HttpStatus.PERMANENT_REDIRECT.value()))
                .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    private val BAD_PATH = "yandex"

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
                .andExpect(status().`is`(HttpStatus.NOT_FOUND.value()))
    }

}

























