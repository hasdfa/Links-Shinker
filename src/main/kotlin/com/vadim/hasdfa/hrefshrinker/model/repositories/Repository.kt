package com.vadim.hasdfa.hrefshrinker.model.repositories

import com.vadim.hasdfa.hrefshrinker.model.Link
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Repository("links")
interface Repository: MongoRepository<Link, Long> {

}