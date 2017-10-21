package com.vadim.hasdfa.hrefshrinker.service


import com.vadim.hasdfa.hrefshrinker.model.Link
import com.vadim.hasdfa.hrefshrinker.model.repositories.LinkRepository
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService.Get
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.atomic.AtomicLong

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

    val sequence: AtomicLong
    get() {
        return AtomicLong(10000000L + repository.count())
    }

    @Transactional
    override fun add(link: String): String {
        val id = sequence.get()
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







