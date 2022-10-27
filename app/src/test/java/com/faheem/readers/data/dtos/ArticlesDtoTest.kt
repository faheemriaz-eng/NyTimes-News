package com.faheem.readers.data.dtos

import com.faheem.readers.testutils.ReadAssetFile
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ArticlesDtoTest {
    @Test
    fun `test popular articles json response maps to data model`() {
        val popularArticlesResponse = readJsonFile()

        Assertions.assertEquals(20, popularArticlesResponse.results.count())

        Assertions.assertEquals(
            "Adidas Ends Partnership With Kanye West at a Considerable Cost",
            popularArticlesResponse.results.first().title
        )

        Assertions.assertEquals(
            "First Known Family of Neanderthals Found in Russian Cave",
            popularArticlesResponse.results.last().title
        )
    }

    private fun readJsonFile(): ArticlesDto {
        val gson = GsonBuilder().create()
        val itemType = object : TypeToken<ArticlesDto>() {}.type
        return gson.fromJson(
            ReadAssetFile.readFileFromTestResources("mostPopularArticlesResponse.json"),
            itemType
        )
    }
}