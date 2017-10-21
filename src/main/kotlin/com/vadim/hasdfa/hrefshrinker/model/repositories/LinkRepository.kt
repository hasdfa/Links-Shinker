package com.vadim.hasdfa.hrefshrinker.model.repositories

import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.vadim.hasdfa.hrefshrinker.model.Link
import org.bson.Document
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by rakshavadim on 21.10.2017.
 */
@Component
class LinkRepository(): Repository {

    private final val mongoClient: MongoClient = MongoClient()
    private final val database: MongoDatabase
    private final val collection: MongoCollection<Document>

    final var collectionName = "links"
    constructor(collection: String): this() {
        collectionName = collection
    }

    init {
        database = mongoClient.getDatabase("links-shrinker")
        collection = database.getCollection(collectionName)
    }

    private fun read(results: FindIterable<HashMap<*, *>>): List<Link> {
        return results.toList().map {
            Link(
                    it.getOrDefault("link", "").toString(),
                    it.getOrDefault("id", "0").toString().toLong(),
                    it["_id"]!!.toString()
            )
        }
    }


    override fun <S: Link?> findAll(p0: Example<S>): MutableList<S> {
        TODO("not implemented")
    }

    override fun findAll(sort: Sort): MutableList<Link> {
        val all = read(collection.find(HashMap::class.java)).toMutableList()
        if (sort.isSorted) {
            all.sortByDescending { it.link }
            return all
        }
        return all
    }

    override fun <S : Link?> findAll(p0: Example<S>, p1: Pageable): Page<S> {
        TODO("not implemented")
    }

    override fun findAll(): MutableList<Link> {
        return read(collection.find(HashMap::class.java)).toMutableList()
    }

    override fun <S : Link?> findAll(p0: Example<S>, p1: Sort): MutableList<S> {
        TODO("not implemented")
    }

    override fun findAll(pageable: Pageable): Page<Link> {
        TODO("not implemented")
    }

    override fun findAllById(mutableIterable: MutableIterable<Long>): MutableIterable<Link> {
        val list = arrayListOf<Link>()
        read(collection.find(HashMap::class.java)).forEach {
            if (it.id in mutableIterable) {
                list.add(it)
            }
        }
        return list.toMutableList()
    }

    override fun <S : Link?> save(link: S): S {
        collection.insertOne(link?.toDocument())
        return link
    }

    override fun <S : Link?> exists(example: Example<S>): Boolean {
        read(collection.find(HashMap::class.java)).forEach {
            if (it == example.probe) {
                return true
            }
        }
        return false
    }

    override fun <S : Link?> saveAll(p0: MutableIterable<S>): MutableList<S> {
        TODO("not implemented")
    }

    override fun <S : Link?> count(p0: Example<S>): Long {
        TODO("not implemented")
    }

    override fun count(): Long {
        return collection.count()
    }

    override fun deleteAll() {
        collection.drop()
        collection.find()
    }

    override fun deleteAll(mutableIterable: MutableIterable<Link>) {
        TODO("not implemented")
    }

    override fun <S : Link?> insert(link: S): S {
        collection.insertOne(link?.toDocument())
        return link
    }

    override fun <S : Link?> insert(mutableIterable: MutableIterable<S>): MutableList<S> {
        val list = mutableIterable.toMutableList()
        collection.insertMany(list.map { it?.toDocument() })
        return list
    }

    override fun delete(link: Link) {
        collection.dropIndex(link.toDocument())
    }

    override fun findById(id: Long): Optional<Link> {
        read(collection.find(HashMap::class.java)).forEach {
            if (it.id == id)
                return Optional.of(it)
        }
        return Optional.empty()
    }

    override fun <S : Link?> findOne(example: Example<S>): Optional<S> {
        collection.find(example.probeType).forEach {
            if (it == example.probe)
                return Optional.of(it)
        }
        return Optional.empty()
    }

    override fun deleteById(id: Long) {
        read(collection.find(HashMap::class.java)).forEach {
            if (it.id == id)
                return collection.dropIndex(it.toDocument())
        }
    }

    override fun existsById(id: Long): Boolean {
        read(collection.find(HashMap::class.java)).forEach {
            if (it.id == id) {
                return true
            }
        }
        return false
    }
}