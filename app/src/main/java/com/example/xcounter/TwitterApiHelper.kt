package com.example.xcounter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken

object TwitterApiHelper {

    private const val CONSUMER_KEY = "your_consumer_key"
    private const val CONSUMER_SECRET = "your_consumer_secret"
    private const val ACCESS_TOKEN = "your_access_token"
    private const val ACCESS_TOKEN_SECRET = "your_access_token_secret"


    private val twitter: Twitter by lazy {
        TwitterFactory().instance.apply {
            setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
            oAuthAccessToken = AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
        }
    }

    fun postTweet(tweet: String, onComplete: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                twitter.updateStatus(tweet)
                onComplete(true)
            } catch (e: TwitterException) {
                e.printStackTrace()
                onComplete(false)
            }
        }
    }
}