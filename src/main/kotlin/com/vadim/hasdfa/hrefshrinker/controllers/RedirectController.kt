package com.vadim.hasdfa.hrefshrinker.controllers

import com.vadim.hasdfa.hrefshrinker.service.DefaultKeyMapperService
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.servlet.http.HttpServletResponse

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Component
@Controller
class RedirectController {

    val service: KeyMapperService by lazy {
        DefaultKeyMapperService()
    }

    @GetMapping("/")
    fun homeHandler() = "index"

    @GetMapping("/{key}")
    fun keyHandler(@PathVariable("key") key: String, response: HttpServletResponse) {
        val result = service.getLink(key)

        when (result) {
            is KeyMapperService.Get.Link -> {
                response.setHeader(HEADER_NAME, result.link)
                response.status = HttpStatus.PERMANENT_REDIRECT.value()
                response.outputStream.print("Redirecting...")
            }
            is KeyMapperService.Get.NotFound -> {
                response.status = HttpStatus.NOT_FOUND.value()
                response.outputStream.print("Not found!")
            }
        }
    }

    @PostMapping("/addPost")
    fun addKeyHandler(@RequestBody link: String, response: HttpServletResponse) {
        val result = service.add(link)
        response.status = HttpStatus.OK.value()
        response.outputStream.println(result)
    }

    companion object {
        private val HEADER_NAME = "Location"
    }
}


























