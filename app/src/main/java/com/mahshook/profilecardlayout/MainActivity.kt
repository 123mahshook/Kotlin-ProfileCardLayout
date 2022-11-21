package com.mahshook.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahshook.profilecardlayout.ui.theme.MyTheme
import com.mahshook.profilecardlayout.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MainScreen()
            }

        }
    }
}

@Composable
fun MainScreen(userProfile: List<UserProfile> = userProfileList) {
    Scaffold(topBar = {AppBar()}) {
        androidx.compose.material.Surface(modifier = Modifier.fillMaxSize(),

            ) {
            LazyColumn(){
               items(userProfile){
                   userProfile->
                   ProfileCard(userProfile = userProfile)
               }
            }
            /*Column() {
               for(user in userProfile){
                   ProfileCard(userProfile = user)
               }
            }*/

        }
    }

}

@Composable
fun AppBar(){
    TopAppBar(

        navigationIcon = { Icon(Icons.Default.Home,
            "content description",
            modifier = Modifier.padding(horizontal = 12.dp)
        )},

        title = { Text(text = "Messaging Application Users") },
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .wrapContentHeight(align = Alignment.Top)
        ,
        elevation = 8.dp,
                backgroundColor = Color.White
    ) {
       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement =Arrangement.Start
       ) {
           ProfilePicture(userProfile.drawableId,userProfile.status)
           ProfileContent(userProfile.name,userProfile.status)
       }
    }
}

@Composable
fun ProfilePicture(drawableId: Int,onlineStatus:Boolean){
 Card (
     shape = CircleShape,
     border = BorderStroke(
         width = 2.dp,
         color =
         if(onlineStatus)
         MaterialTheme.colors.lightGreen
     else
         Color.Red
     ),
     modifier = Modifier
         .padding(16.dp),
     elevation = 4.dp,

         ){
     Image(painter = painterResource(id = drawableId),
         contentDescription ="Content Description",
         modifier = Modifier.size(72.dp),
         contentScale = ContentScale.Crop
     )
 }
    
}

@Composable
fun ProfileContent(userName:String,onlineStatus: Boolean){

    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {
        CompositionLocalProvider(LocalContentAlpha provides (
                if(onlineStatus)
                    1f
                            else
                ContentAlpha.medium
                )) {
        Text(text = userName,
            style = MaterialTheme.typography.h5

        )
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text =
            if(onlineStatus)
            "Active now"
                else
                    "offline"
                ,
                style = MaterialTheme.typography.body2
            )
        }



    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyTheme {
   MainScreen()
    }
}