package tk.pokatomnik.mrakopediareader2

import org.junit.Assert
import org.junit.Test
import tk.pokatomnik.mrakopediareader2.screens.search.formatTripleDotInAMiddle

class TripleDotMiddleFormatterTest {
    @Test
    fun `test short string`() {
        val string = "hello, world!"
        val formatted = formatTripleDotInAMiddle(string, 13)
        Assert.assertEquals(string, formatted)
    }

    @Test
    fun `test a little larger string even`() {
        val string = "hello, world!"
        val maxLength = 12
        val formatted = formatTripleDotInAMiddle(string, maxLength)
        Assert.assertEquals(formatted.length, maxLength)
        Assert.assertEquals("hello...rld!", formatted)
    }

    @Test
    fun `test a little larger string odd`() {
        val string = "hello, world!!"
        val maxLength = 12
        val formatted = formatTripleDotInAMiddle(string, maxLength)
        Assert.assertEquals(formatted.length, maxLength)
        Assert.assertEquals("hello...ld!!", formatted)
    }

    @Test
    fun `test large string even`() {
        val string = "hello, world, this is the test string that must be much more shorter"
        val maxLength = 20
        val formatted = formatTripleDotInAMiddle(string, maxLength)
        Assert.assertEquals(formatted.length, maxLength)
        Assert.assertEquals("hello, wo... shorter", formatted)
    }

    @Test
    fun `test large string odd`() {
        val string = "hello, world, this is the test string that must be much more shorter!"
        val maxLength = 20
        val formatted = formatTripleDotInAMiddle(string, maxLength)
        Assert.assertEquals(formatted.length, maxLength)
        Assert.assertEquals("hello, wo...shorter!", formatted)
    }
}