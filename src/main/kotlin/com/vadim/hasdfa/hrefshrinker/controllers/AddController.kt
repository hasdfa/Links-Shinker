package com.vadim.hasdfa.hrefshrinker.controllers

import com.vadim.hasdfa.hrefshrinker.service.DefaultKeyMapperService
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

/**
 * Created by rakshavadim on 21.10.2017.
 */
@Controller
class AddController {

    val service: KeyMapperService by lazy {
        DefaultKeyMapperService()
    }

    @PostMapping("/add")
    @ResponseBody
    fun add(@RequestBody request: AddRequest, response: HttpServletResponse): ResponseEntity<AddResponse> {
        return ResponseEntity.ok(AddResponse(service.add(request.link), request.link))
    }

    data class AddRequest(val link: String = "")
    data class AddResponse(val key: String = "", val link: String = "")

}
















