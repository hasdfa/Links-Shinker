package com.vadim.hasdfa.hrefshrinker.controllers

import com.vadim.hasdfa.hrefshrinker.service.DefaultKeyMapperService
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

/**
 * Created by rakshavadim on 21.10.2017.
 */
@Controller
class AddController {

    private val prefix = "http://localhost:8080/"

    val service: KeyMapperService by lazy {
        DefaultKeyMapperService()
    }

    @PostMapping("/add")
    @ResponseBody
    fun addRest(@RequestBody request: AddRequest, response: HttpServletResponse) = ResponseEntity.ok(add(request.link))

    @PostMapping("/result")
    fun result(model: Model, @RequestParam("link", required = true) link: String): String {
        val result = add(link)
        return when (result) {
            is Add.AddResponse -> {
                model.addAttribute("link", result.link)
                model.addAttribute("keyed", prefix + result.key)
                "result"
            }
            is Add.InvalidUrl -> {
                model.addAttribute("url", result)
                "invalid"
            }
            else -> "index"
        }
    }

    data class AddRequest(val link: String = "")
    interface Add {
        data class AddResponse(val key: String = "", val link: String = ""): Add
        data class InvalidUrl(val url: String): Add
    }

    private fun add(link: String): Add {
        val urlValidator = URLValidator()
        return if (urlValidator.isValid(link, null)) {
            Add.AddResponse(service.add(link), link)
        } else {
            Add.AddResponse(service.add(link), link)
        }
    }
}
















