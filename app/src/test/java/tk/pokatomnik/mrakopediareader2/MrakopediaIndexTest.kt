package tk.pokatomnik.mrakopediareader2

import org.junit.Assert.assertEquals
import org.junit.Test
import tk.pokatomnik.mrakopediareader2.domain.PageMeta
import tk.pokatomnik.mrakopediareader2.domain.Category
import tk.pokatomnik.mrakopediareader2.domain.ImageInfo
import tk.pokatomnik.mrakopediareader2.services.index.MrakopediaIndex
import tk.pokatomnik.mrakopediareader2.services.textassetresolver.TextAssetResolver
import java.io.IOException

class MrakopediaIndexTest {
    @Test
    fun `Check if parsing is correct`() {
        val parsed = MrakopediaIndex(MockTextAssetResolver())

        assertEquals(
            listOf(
                PageMeta(
                    "title0",
                    99,
                    99,
                    100,
                    "0",
                    setOf("category0", "category1"),
                    setOf("foo", "bar"),
                    listOf(),
                ),
                PageMeta(
                    "title1",
                    null,
                    null,
                    200,
                    "1",
                    setOf(),
                    setOf(),
                    listOf(
                        ImageInfo(
                            "/path/to/image/1.jpg",
                            "First Image"
                        ),
                        ImageInfo(
                            "/path/to/image/2.jpg",
                            "Second Image"
                        ),
                        ImageInfo(
                            "/path/to/image/3.jpg",
                            null
                        )
                    )
                ),
                PageMeta(
                    "title2",
                    0,
                    0,
                    300,
                    "2",
                    setOf(),
                    setOf(),
                    listOf()
                )
            ),
            parsed.getCategory("category0").pages,
        )
        assertEquals(
            parsed.getCategory("category1").pages,
            listOf(
                PageMeta(
                    "title0",
                    50,
                    50,
                    100,
                    "0",
                    setOf("category0", "category1"),
                    setOf("foo", "bar"),
                    listOf()
                )
            )
        )
    }

    @Test
    fun `Check if missing category has no pages`() {
        val parsed = MrakopediaIndex(MockTextAssetResolver())
            .getCategory("")
        val missingCategory = Category("", mapOf(), MockTextAssetResolver())
        assertEquals(parsed.size, missingCategory.size)
        assertEquals(parsed.size, 0)
    }

    @Test
    fun `Check if size is correct`() {
        val size = MrakopediaIndex(MockTextAssetResolver())
            .uniquePagesTotalComputed
        assertEquals(size, 3)
    }

    @Test
    fun `Check if page resolved correctly`() {
        val page = MrakopediaIndex(MockTextAssetResolver())
            .getCategory("category0")
            .getPageMetaByTitle("title2")
        assertEquals(
            page, PageMeta(
                "title2",
                0,
                0,
                300,
                "2",
                setOf(),
                setOf(),
                listOf(),
            )
        )
    }

    @Test
    fun `Check if missing page resolves to null`() {
        val page = MrakopediaIndex(MockTextAssetResolver()).getCategory("category0")
            .getPageMetaByTitle("")
        assertEquals(page, null)
    }

    @Test
    fun `Check resolve existing content`() {
        val index = MrakopediaIndex(MockTextAssetResolver())
        val category = index.getCategory("category0")
        val content0 = category.getPageContentByTitle("title0")
        val content1 = category.getPageContentByTitle("title1")
        val missingContent = category.getPageContentByTitle("")

        assertEquals("STORY_0_CONTENT", content0)
        assertEquals("STORY_1_CONTENT", content1)
        assertEquals("", missingContent)
    }

    @Test
    fun `Check if categories getter works correctly`() {
        val category0PagesTotal = MrakopediaIndex(MockTextAssetResolver())

        assertEquals(
            setOf(
                "category0",
                "category1",
                "Все страницы"
            ),
            category0PagesTotal.getCategoryNames()
        )
    }

    @Test
    fun `Check if average rating is correct`() {
        val avgRating = MrakopediaIndex(MockTextAssetResolver()).getCategory("category0").avgRating
        assertEquals(avgRating, 33)
    }

    @Test
    fun `Check if average voted is correct`() {
        val avgVoted = MrakopediaIndex(MockTextAssetResolver()).getCategory("category0").avgVoted
        assertEquals(avgVoted, 33)
    }

    @Test
    fun `Check if good stories are parsed`() {
        val goodStories = MrakopediaIndex(MockTextAssetResolver()).getGoodStories()
        assertEquals(
            listOf("title0"),
            goodStories,
        )
    }

    @Test
    fun `Check if stories of month are parsed`() {
        val storiesOfMonth = MrakopediaIndex(MockTextAssetResolver()).getStoriesOfMonth()
        assertEquals(
            listOf("title1"),
            storiesOfMonth
        )
    }

    @Test
    fun `Check random pages amount`() {
        val randomPages = MrakopediaIndex(MockTextAssetResolver()).getRandomTitles(2)
        assertEquals(2, randomPages.size)
    }

    @Test
    fun `Test required random pages amount exceeded total pages`() {
        val randomPages = MrakopediaIndex(MockTextAssetResolver()).getRandomTitles(Int.MAX_VALUE)
        assertEquals(3, randomPages.size)
    }
}

class MockTextAssetResolver : TextAssetResolver {
    override fun resolve(filePath: String): String {
        if (filePath == "content/index.json") {
            return """
                {
                  "mrakopediaIndex": {
                    "category0": [
                      {
                        "title": "title0",
                        "rating": 99,
                        "voted": 99,
                        "charactersInPage": 100,
                        "contentId": "0",
                        "categories": [
                          "category0",
                          "category1"
                        ],
                        "seeAlso": [
                          "foo",
                          "bar"
                        ],
                        "images": []
                      },
                      {
                        "title": "title1",
                        "charactersInPage": 200,
                        "contentId": "1",
                        "categories": [],
                        "seeAlso": [],
                        "images": [
                          {
                            "imgPath": "/path/to/image/1.jpg",
                            "imgCaption": "First Image"
                          },
                          {
                            "imgPath": "/path/to/image/2.jpg",
                            "imgCaption": "Second Image"
                          },
                          {
                            "imgPath": "/path/to/image/3.jpg"
                          }
                        ]
                      },
                      {
                        "title": "title2",
                        "rating": 0,
                        "voted": 0,
                        "charactersInPage": 300,
                        "contentId": "2",
                        "categories": [],
                        "seeAlso": [],
                        "images": []
                      }
                    ],
                    "category1": [
                      {
                        "title": "title0",
                        "rating": 50,
                        "voted": 50,
                        "charactersInPage": 100,
                        "contentId": "0",
                        "categories": [
                          "category0",
                          "category1"
                        ],
                        "seeAlso": [
                          "foo",
                          "bar"
                        ],
                        "images": []
                      }
                    ]
                  },
                  "storiesOfMonth": {
                    "goodStories": [
                      "title0"
                    ],
                    "storiesOfMonth": [
                      "title1"
                    ]
                  }
                }
            """.trimIndent()
        }
        if (filePath == "content/0.md") {
            return "STORY_0_CONTENT"
        }
        if (filePath == "content/1.md") {
            return "STORY_1_CONTENT"
        }
        if (filePath == "content/2.md") {
            return "STORY_2_CONTENT"
        }
        throw IOException("No such file")
    }
}