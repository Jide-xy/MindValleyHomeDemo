package com.example.mindvalleytest.core.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MindValleyServiceTest : NetworkTestSetup() {

    @Test
    fun `verify channel response from server is correctly returned and parsed`() = runBlocking {
        enqueueResponse("sample-channel-response.json")
        val response = service.getChannels()
        //verify request
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("GET", request.method)
        Assert.assertEquals("/raw/Xt12uVhM", request.path)
        Assert.assertEquals(response.isSuccessful, true)

        //verify response
        val channels = response.body()!!.data.channels
        Assert.assertEquals(channels.size, 9)
        Assert.assertEquals(channels.first().series.size, 0)
        Assert.assertEquals(
            channels.first().iconAsset?.thumbnailUrl,
            "https://edgecastcdn.net/80EC13/public/overmind2/asset/11914f01-ba4a-4d68-9c33-efb34c43ed23/channel-icon-mentoring_thumbnail.png"
        )
        Assert.assertEquals(
            channels.first().coverAsset.url,
            "https://assets.mindvalley.com/api/v1/assets/8fd5837a-539c-4367-b1af-8579a3e3d461.jpg?transform=w_1080"
        )
        Assert.assertEquals(channels.first().latestMedia.size, 12)
        Assert.assertEquals(channels.first().latestMedia.first().type, "course")
        Assert.assertEquals(
            channels.first().latestMedia.first().title,
            "How Journaling Helped Create a \$500M Company"
        )
        Assert.assertEquals(
            channels.first().latestMedia.first().coverAsset.url,
            "https://assets.mindvalley.com/api/v1/assets/a90653b8-8475-41a9-925a-3a1bf0e7cd5f.jpg?transform=w_1080"
        )


    }

    @Test
    fun `verify new episodes response from server is correctly returned and parsed`() =
        runBlocking {
            enqueueResponse("sample-new-episode-response.json")
            val response = service.getNewEpisodes()
            //verify request
            val request = mockWebServer.takeRequest()
            Assert.assertEquals("GET", request.method)
            Assert.assertEquals("/raw/z5AExTtw", request.path)
            Assert.assertEquals(response.isSuccessful, true)

            //verify response
            val newEpisodes = response.body()!!.data.media
            Assert.assertEquals(newEpisodes.size, 6)
            Assert.assertEquals(newEpisodes.first().channel.title, "Little Humans")
            Assert.assertEquals(newEpisodes.first().type, "course")
            Assert.assertEquals(newEpisodes.first().title, "Conscious Parenting")
            Assert.assertEquals(
                newEpisodes.first().coverAsset.url,
                "https://assets.mindvalley.com/api/v1/assets/5bdbdd0e-3bd3-432b-b8cb-3d3556c58c94.jpg?transform=w_1080"
            )


        }


    @Test
    fun `verify category response from server is correctly returned and parsed`() = runBlocking {
        enqueueResponse("sample-categories-response.json")
        val response = service.getCategories()
        //verify request
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("GET", request.method)
        Assert.assertEquals("/raw/A0CgArX3", request.path)
        Assert.assertEquals(response.isSuccessful, true)

        //verify response
        val categories = response.body()!!.data.categories
        Assert.assertEquals(categories.size, 12)
        Assert.assertEquals(categories.first().name, "Career")
        Assert.assertEquals(categories.component2().name, "Character")
        Assert.assertEquals(categories.component3().name, "Emotional")
        Assert.assertNotEquals(categories.component4().name, "Financia")

    }


}