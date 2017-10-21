package com.vadim.hasdfa.hrefshrinker.service


import com.vadim.hasdfa.hrefshrinker.model.Link
import com.vadim.hasdfa.hrefshrinker.model.repositories.LinkRepository
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService.Get
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicLong
import javax.annotation.PostConstruct

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Component
class DefaultKeyMapperService: KeyMapperService {

    private val repository: LinkRepository by lazy {
        LinkRepository()
    }

    val converter: KeyConverterService by lazy {
        DefaultKeyConverterService()
    }

    val sequence = AtomicLong(10000000L)

    @Transactional
    override fun add(link: String): String {
        val id = sequence.getAndIncrement()
        val key = converter.idToKey(id)

        repository.insert(Link(link, id))
        return key
    }

    @Transactional
    override fun getLink(key: String): Get {
        val result = repository.findById(converter.keyToId(key))

        return if (result.isPresent)
            KeyMapperService.Get.Link(result.get().link)
        else
            KeyMapperService.Get.NotFound(key)
    }
}







