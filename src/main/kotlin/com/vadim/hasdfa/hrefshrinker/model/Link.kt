package com.vadim.hasdfa.hrefshrinker.model

import org.bson.Document
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Entity
@Table(name = "links")
@org.springframework.data.mongodb.core.mapping.Document
data class Link(
        var link: String = "",
        @Id var id: Long = 0,
        var id_id: String = ""
) {
    public fun toDocument(): Document {
        return Document(
                hashMapOf("link" to link, "id" to id)
        )
    }
}