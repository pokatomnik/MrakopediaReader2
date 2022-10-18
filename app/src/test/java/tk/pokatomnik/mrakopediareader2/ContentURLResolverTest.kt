package tk.pokatomnik.mrakopediareader2

import org.junit.Assert.assertEquals
import org.junit.Test
import tk.pokatomnik.mrakopediareader2.screens.story.resolveContentURL

class ContentURLResolverTest {
    @Test
    fun `Test resolve`() {
        val expectedUrl = "https://mrakopedia.net/wiki/%D0%9C%D0%B0%D0%BC%D0%B0_(%D0%90%D1%80%D1%82%D1%91%D0%BC%D0%BE%D0%B2_%D0%90.)"
        val url = resolveContentURL("Мама_(Артёмов_А.)")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test PageContentPathResolver resolve with slashes`() {
        val expectedUrl = "https://mrakopedia.net/wiki/%D0%98%D0%B3%D1%80%D0%B0_%D0%B2_%D0%BB%D0%B5%D0%B2%D0%BE-%D0%BF%D1%80%D0%B0%D0%B2%D0%BE/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D0%B5%D1%80%D0%B8%D0%B9"
        val url = resolveContentURL("Игра в лево-право/Список серий")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with slashes and dash`() {
        val expectedUrl = "https://mrakopedia.net/wiki/SCP-507._%D0%9D%D0%B5_%D0%BB%D1%8E%D0%B1%D0%B8%D1%82%D0%B5%D0%BB%D1%8C_%D1%85%D0%BE%D0%B4%D0%B8%D1%82%D1%8C_%D0%BC%D0%B5%D0%B6%D0%B4%D1%83_%D0%BC%D0%B8%D1%80%D0%B0%D0%BC%D0%B8/%D0%94%D0%BE%D0%BA%D1%83%D0%BC%D0%B5%D0%BD%D1%82_507-3B"
        val url = resolveContentURL("SCP-507. Не любитель ходить между мирами/Документ 507-3B")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with colon`() {
        val expectedUrl = "https://mrakopedia.net/wiki/Support_Call_ID:_100156-03"
        val url = resolveContentURL("Support Call ID: 100156-03")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with bracers`() {
        val expectedUrl = "https://mrakopedia.net/wiki/%D0%9A%D1%83%D0%BA%D0%B8-%D0%BC%D0%BE%D0%BD%D1%81%D1%82%D1%80_(%D0%92%D0%B5%D1%80%D0%BD%D0%BE%D1%80_%D0%92%D0%B8%D0%BD%D0%B4%D0%B6)"
        val url = resolveContentURL("Куки-монстр (Вернор Виндж)")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with quotes`() {
        val expectedUrl = "https://mrakopedia.net/wiki/Lavender_Town_%E2%80%94_%C2%AB%D0%9B%D0%B0%D0%B2%D0%B0%D0%BD%D0%B4%D0%BE%D0%B2%D1%8B%D0%B9_%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%D0%BE%D0%BA%C2%BB"
        val url = resolveContentURL("Lavender Town — «Лавандовый городок»")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with unicode`() {
        val expectedUrl = "https://mrakopedia.net/wiki/Szomor%C3%BA_Vas%C3%A1rnap"
        val url = resolveContentURL("Szomorú Vasárnap")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with exclamation and comma`() {
        val expectedUrl = "https://mrakopedia.net/wiki/%D0%AD%D1%82%D0%BE_%D1%82%D0%B5%D0%B1%D0%B5_%D0%BF%D0%BE%D0%B4%D0%B0%D1%80%D0%BE%D1%87%D0%B5%D0%BA,_%D1%81%D0%BB%D0%B0%D0%B4%D0%B5%D0%BD%D1%8C%D0%BA%D0%B8%D0%B9!"
        val url = resolveContentURL("Это тебе подарочек, сладенький!")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with question mark`() {
        val expectedUrl = "https://mrakopedia.net/wiki/%D0%9B%D1%8E%D0%B1%D0%B8%D1%82%D0%B5_%D1%81%D0%B8%D0%B4%D0%B5%D1%82%D1%8C_%D0%B2%D0%BA%D0%BE%D0%BD%D1%82%D0%B0%D0%BA%D1%82%D0%B5%3F"
        val url = resolveContentURL("Любите сидеть вконтакте?")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with backtick`() {
        val expectedUrl = "https://mrakopedia.net/wiki/Bum%60s_face.gif"
        val url = resolveContentURL("Bum`s face.gif")
        assertEquals(expectedUrl, url)
    }

    @Test
    fun `Test resolve with double quotes`() {
        val expectedUrl = "https://mrakopedia.net/wiki/%D0%A0%D0%B5%D0%BA%D0%BB%D0%B0%D0%BC%D0%B0_%D0%BC%D0%B0%D0%B3%D0%B0%D0%B7%D0%B8%D0%BD%D0%B0_%22%D0%9F%D0%B0%D0%BD%D0%B4%D0%B0%22"
        val url = resolveContentURL("Реклама магазина \"Панда\"")
        assertEquals(expectedUrl, url)
    }
}