package com.vadim.hasdfa.hrefshrinker.service


import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService.Add
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService.Get
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService.Add.*
import com.vadim.hasdfa.hrefshrinker.service.KeyMapperService.Get.*
import org.springframework.stereotype.Component
import java.io.*
import java.util.*

/**
 * Created by rakshavadim on 20.10.2017.
 */
@Component
class DefaultKeyMapperService: KeyMapperService {
    final var urls = hashMapOf<String, String>()

    init {
        try {
            val scanner = Scanner(File("data.txt"))
            while (scanner.hasNext()) {
                val str = scanner.nextLine().split(":")
                val key = str[0]
                val link = str[1]

                urls.put(key, link)
            }
            scanner.close()
        } catch (io: IOException) {
            io.printStackTrace()
        }
    }
    override fun add(key: String, link: String): Add {
        return if (urls.containsKey(key))
            AlreadyExist(key)
        else {
            urls.put(key, link)
            Success(key, link)//.also { writeToFileAsync(key, link) }
        }
    }

//    private fun writeToFileAsync(key: String, link: String) {
//        thread {
//            try {
//                val os = OutputStreamWriter(FileOutputStream(File("data.txt"), true))
//                os.write("\"$key\":\"$link\"\n")
//                os.flush()
//                os.close()
//            } catch (io: IOException) {
//                io.printStackTrace()
//            }
//        }
//    }

    override fun getLink(key: String): Get {
        return if (urls.containsKey(key))
            Link(urls[key]!!)
        else
            NotFound(key)
    }
}







