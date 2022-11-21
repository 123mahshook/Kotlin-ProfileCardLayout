package com.mahshook.profilecardlayout

data class UserProfile
constructor(
    val name:String,
    val status:Boolean,
    val drawableId:Int) {
}

val userProfileList= arrayListOf<UserProfile>(
    UserProfile(name = "Jhone Doe", status =  true, drawableId =  R.drawable.profile_picture),
            UserProfile(name = "Anna jhones", status =  false, drawableId =  R.drawable.profile_picture2)
)