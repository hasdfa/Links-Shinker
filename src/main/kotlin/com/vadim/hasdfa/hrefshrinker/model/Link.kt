package com.vadim.hasdfa.hrefshrinker.model

import org.bson.Document
import javax.persistence.Id

/**
 * Created by rakshavadim on 20.10.2017.
 */
@org.springframework.data.mongodb.core.mapping.Document
data class Link(
        var link: String = "",
        @Id var id: Long = 0,
        var id_id: String = ""
) {
    public fun toDocument(): Document {
        return Document(
                mapOf("link" to link, "id" to id)
        )
    }
}