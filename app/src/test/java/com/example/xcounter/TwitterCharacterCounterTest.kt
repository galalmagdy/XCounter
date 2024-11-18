package com.example.xcounter

import org.junit.Assert.assertEquals
import org.junit.Test


// Unit test for TwitterCharacterCounter
class TwitterCharacterCounterTest {

    @Test
    fun `test character count without URLs`() {
        val tweet = "Hello, this is a sample tweet!"
        val result = TwitterCharacterCounter.countCharacters(tweet)
        assertEquals(30, result)
    }

    @Test
    fun `test character count with one URL`() {
        val tweet = "Check this out https://example.com"
        val result = TwitterCharacterCounter.countCharacters(tweet)
        assertEquals(23 + 15, result)
    }

    @Test
    fun `test character count with multiple URLs`() {
        val tweet = "Visit https://example.com and https://another.com"
        val result = TwitterCharacterCounter.countCharacters(tweet)
        assertEquals(23 + 23 + 11, result)
    }

    @Test
    fun `test character count with surrogate pairs`() {
        val tweet = "Hello 👋😊"
        val result = TwitterCharacterCounter.countCharacters(tweet)
        assertEquals(10, result)
    }
}

