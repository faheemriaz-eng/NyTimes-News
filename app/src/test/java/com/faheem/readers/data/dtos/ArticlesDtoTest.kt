package com.faheem.readers.data.dtos

import com.faheem.readers.testutils.ReadAssetFile
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Test

class ArticlesDtoTest {
    @Test
    fun `test popular articles json response maps to data model`() {
        val popularArticlesResponse = readJsonFile()

        Assert.assertEquals(20, popularArticlesResponse.results.count())

        Assert.assertEquals(
            "Adidas Ends Partnership With Kanye West at a Considerable Cost",
            popularArticlesResponse.results.first().title
        )

        Assert.assertEquals(
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