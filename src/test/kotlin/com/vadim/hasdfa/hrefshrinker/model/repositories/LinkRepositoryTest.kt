package com.vadim.hasdfa.hrefshrinker.model.repositories

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.vadim.hasdfa.hrefshrinker.model.AbstractRepositoryTest
import com.vadim.hasdfa.hrefshrinker.model.Link
import org.bson.Document
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.web.WebAppConfiguration
import java.util.*

/**
 * Created by rakshavadim on 20.10.2017.
 */
@EnableAutoConfiguration
@WebAppConfiguration
open class LinkRepositoryTest: AbstractRepositoryTest() {

    //@Autowired
    val repository: LinkRepository = LinkRepository(collection = "links-test")

    @Before
    public fun init() {
        addSamples()
//        Mockito.`when`(repository.findById(LINK_NOT_FOUND)).thenReturn(Optional.empty())
//        Mockito.`when`(repository.findById(LINK_1_ID)).thenReturn(Optional.of(Link(LINK_1_TEXT, LINK_1_ID)))
//        Mockito.`when`(repository.insert(Link(LINK_TBS_TEXT))).thenReturn(Link(LINK_TBS_TEXT))
//        Mockito.`when`(repository.findAll()).thenReturn(mutableListOf(Link(), Link(), Link(), Link()))
    }

    @After
    public fun deinit() {
        repository.deleteAll()
    }

    fun addSamples(){
        repository.insert(Link("https://google.com", 999_996))
        repository.save(Link("https://apple.com", 999_997))
        repository.save(Link("http://avecp.com.ua", 999_998))
    }

    public fun removeSamples() {
        repository.deleteById(999_996)
        repository.deleteById(999_997)
        repository.deleteById(999_998)
        repository.deleteById(999_999)
    }

    @Test
    fun findOneExisting() {
        val got: Optional<Link> = repository.findById(LINK_1_ID)
        assertThat(got.isPresent, Matchers.equalTo(true))
        val link = got.get()

        assertThat(link.link, Matchers.equalTo(LINK_1_TEXT))
        assertThat(link.id, Matchers.equalTo(LINK_1_ID))
    }

    @Test
    fun findOneNotExisting() {
        val got: Optional<Link> = repository.findById(LINK_NOT_FOUND)
        assertThat(got.isPresent, Matchers.equalTo(false))
    }

    @Test
    fun saveNew() {
        val toBeSaved = Link(LINK_TBS_TEXT, LINK_2_ID)
        val got: Link = repository.insert(toBeSaved)
        val list: List<Link> = repository.findAll()

        assertThat(list, Matchers.hasSize(4))
        assertThat(got.link, Matchers.equalTo(LINK_TBS_TEXT))
    }

    companion object {
        const val DATA_SET = "classpath:datasets/link-table.xml"

        private val LINK_NOT_FOUND: Long = 1L
        private val LINK_1_ID: Long = 999_998L
        private val LINK_2_ID: Long = 999_999L
        private val LINK_TBS_TEXT: String = "http://www.ru"
        private val LINK_1_TEXT: String = "http://avecp.com.ua"

    }

}























