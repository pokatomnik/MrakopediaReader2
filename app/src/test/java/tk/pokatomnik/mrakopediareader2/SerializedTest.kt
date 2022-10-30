package tk.pokatomnik.mrakopediareader2

import org.junit.Assert
import org.junit.Test
import tk.pokatomnik.mrakopediareader2.services.navigation.URLSerializer

class SerializedTest {
    @Test
    fun `URLSerializer - simple case`() {
        val serializer = URLSerializer()
        val str = "hello, world!"
        Assert.assertEquals(str, serializer.parse(serializer.serialize(str)))
    }

    @Test
    fun `URLSerializer - test with cyrillic`() {
        val serializer = URLSerializer()
        val str = "Привет, мир!"
        Assert.assertEquals(str, serializer.parse(serializer.serialize(str)))
    }

    @Test
    fun `URLSerializer - test with various characters`() {
        val serializer = URLSerializer()
        val str = "!:%%!*№:(!?*;:?(*!?;)(!==/\\`\"}{[][:;\" ~<>/"
        Assert.assertEquals(str, serializer.parse(serializer.serialize(str)))
    }
}