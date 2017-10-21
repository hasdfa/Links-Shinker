package com.vadim.hasdfa.hrefshrinker.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.vadim.hasdfa.hrefshrinker.HrefShrinkerApplication
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by rakshavadim on 21.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootApplication(scanBasePackageClasses = arrayOf(HrefShrinkerApplication::class))
@WebAppConfiguration
class AddControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext

    val mockMvc: MockMvc by lazy {
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build()
    }

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    @Mock
    lateinit var service: KeyMapperService

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(service.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddLinkHeTakesAKey() {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper()
                        .writeValueAsString(AddController.AddRequest(LINK))
                )).andExpect {
            println(it.response.contentAsString)
        }.andExpect(MockMvcResultMatchers.jsonPath("$.key", equalTo(KEY)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.link", equalTo(LINK)))
    }


    companion object {
        private val KEY = "MjAa"
        private val LINK = "https://google.com"
    }
}



































