package com.example.xcounter
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TwitterCharacterCountUI(
    onPostClick: (String) -> Unit,
    onClearClick: () -> Unit = {},
) {
    var tweetText by remember { mutableStateOf("") }
    val parsedTweet = TwitterCharacterCounter.countCharacters(tweetText)
    val characterLimit = 280
    val currentCharacterCount = parsedTweet
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "X Character Count", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))

        Image(
            painter = painterResource(id = R.drawable.twitter_logo),
            contentDescription = "Twitter Logo",
            modifier = Modifier.size(100.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, Color(230, 246, 254, 255), shape = RoundedCornerShape(12.dp))
                    .width(160.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Characters Typed",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .height(30.dp)
                        .width(160.dp)
                        .background(
                            Color(230, 246, 254, 255),
                            shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .padding(6.dp)
                        .wrapContentSize(Alignment.Center)
                )

                Text(
                    text = "$currentCharacterCount/$characterLimit",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.height(40.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, Color(230, 246, 254, 255), shape = RoundedCornerShape(12.dp))
                    .width(160.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Characters Remaining",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .height(30.dp)
                        .width(160.dp)
                        .background(
                            Color(230, 246, 254, 255),
                            shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .padding(6.dp)
                        .wrapContentSize(Alignment.Center)
                )
                Text(text = "${characterLimit - currentCharacterCount}",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.height(40.dp))
            }
        }

        TextField(
            value = tweetText,
            onValueChange = { newText ->
                if (newText.length <= characterLimit) {
                    tweetText = newText
                }
            },
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .width(170.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)
                .border(2.dp, Color(247, 247, 247, 255), shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp)),
            textStyle = TextStyle(fontSize = 16.sp),
            placeholder = {
                Text(
                    text = "start typing! you can enter up to 280 characters ...",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {


            Button(colors = ButtonDefaults.buttonColors(containerColor = (Color(0,169,112,255))),
                onClick = {
                    copyToClipboard(context, tweetText)
                },
                enabled = tweetText.isNotEmpty(),shape = RoundedCornerShape(12.dp)
            ) {
                Text("Copy Text")
            }
            Button(shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(220,1,37,255)),onClick = {
                tweetText = ""
                onClearClick()
            }) {
                Text("Clear Text")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(8.dp)
            ,colors = ButtonDefaults.buttonColors(containerColor = Color(3,169,244,255)),
            onClick = { onPostClick(tweetText) },
            enabled = tweetText.isNotEmpty(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Post Tweet")
        }
    }
}
private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Tweet", text)
    clipboard.setPrimaryClip(clip)
}