package com.vadim.hasdfa.hrefshrinker.model.repositories

import com.sun.xml.internal.bind.v2.model.core.ID
import com.vadim.hasdfa.hrefshrinker.model.Link
import org.springframework.data.domain.Example
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.QueryByExampleExecutor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Repository("links")
interface Repository: MongoRepository<Link, Long> {

}