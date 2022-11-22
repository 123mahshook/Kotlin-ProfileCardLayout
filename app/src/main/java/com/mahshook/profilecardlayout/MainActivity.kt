package com.mahshook.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.mahshook.profilecardlayout.ui.theme.MyTheme
import com.mahshook.profilecardlayout.ui.theme.lightGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                UsersApplication()
            }

        }
    }
}

@Composable
fun UsersApplication(userProfile: List<UserProfile> = userProfileList){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "users_list", builder = {
        composable("users_list"){
            UserListScreen(userProfile,navController)
        }
        composable(route="user_details/{userId}",
            arguments= listOf(navArgument("userId"){
                type= NavType.IntType
            })
        ){navStackBackStackEntry->

            UserProfileDetailsScreen(navStackBackStackEntry.arguments!!.getInt("userId"),navController)
        }
    })
}

@Composable
fun UserListScreen(userProfile: List<UserProfile>,navController:NavHostController?) {
    Scaffold(topBar = {AppBar(
        title = "Users List",
        icon=Icons.Default.Home

    ){}
    }) {
        androidx.compose.material.Surface(modifier = Modifier.fillMaxSize(),

            ) {
            LazyColumn(){
               items(userProfile){
                   userProfile->
                   ProfileCard(userProfile = userProfile){
                       navController?.navigate("user_details/${userProfile.id}")
                   }
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
fun UserProfileDetailsScreen(userId: Int,navController: NavHostController?) {
    val userProfile = userProfileList.first {
    userProfile ->
        userId==userProfile.id
}
    Scaffold(topBar = {AppBar(
        title = "Users Profile Details",
        icon=Icons.Default.ArrowBack
    ){
        navController?.navigateUp()
    }
    }) {
        androidx.compose.material.Surface(modifier = Modifier.fillMaxSize(),

            ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =Arrangement.Top
            ) {
                ProfilePicture(userProfile.pictureUrl,userProfile.status,240.dp)
                ProfileContent(userProfile.name,userProfile.status,Alignment.CenterHorizontally)
            }
        }
    }

}

@Composable
fun AppBar( title:String,icon:ImageVector,iconClickAction:()->Unit){
    TopAppBar(

        navigationIcon = {
             Icon(
                 imageVector =  icon,
            "content description",
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .clickable { iconClickAction.invoke() }
        )},

        title = { Text(text = title) },
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile,clickAction:()->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .wrapContentHeight(align = Alignment.Top)
            .clickable (
                onClick = {
                    clickAction.invoke()
                }
                    )
        ,
        elevation = 8.dp,
                backgroundColor = Color.White
    ) {
       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement =Arrangement.Start
       ) {
           ProfilePicture(userProfile.pictureUrl,userProfile.status,72.dp)
           ProfileContent(userProfile.name,userProfile.status,Alignment.Start)
       }
    }
}

@Composable
fun ProfilePicture(pictureUrl: String,onlineStatus:Boolean,imageSize:Dp){
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
         .padding(16.dp)
         .size(imageSize)
     ,

     elevation = 4.dp,

         ){
     Image(painter = rememberCoilPainter(
         request = pictureUrl,
         requestBuilder = {
             transformations(CircleCropTransformation())
         }
     ),

         modifier = Modifier.size(72.dp),
         contentDescription = "Profile Picture Description",

     )


    /* Image(painter = painterResource(id = drawableId),
         contentDescription ="Content Description",
         modifier = Modifier.size(72.dp),
         contentScale = ContentScale.Crop
     )*/
 }
    
}

@Composable
fun ProfileContent(userName:String,onlineStatus: Boolean,alignment: Alignment.Horizontal){

    Column(modifier = Modifier
        .padding(8.dp),
        horizontalAlignment = alignment
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
fun UserProfileDetailsPreview() {
    MyTheme {
        UserProfileDetailsScreen(userId = 0,null )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    MyTheme {
   UserListScreen(userProfile = userProfileList,null)
    }
}

