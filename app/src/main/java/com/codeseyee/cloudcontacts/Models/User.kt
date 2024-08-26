package com.codeseye.cloudcontacts.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.firebase.encoders.annotations.Encodable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @JsonIgnore
    var authProvider: String = "email",

    @JsonIgnore
    var loadingDate: Long = System.currentTimeMillis(),

    @JsonIgnore
    @get:Encodable.Ignore
    var isContact: Boolean = false,

    @JsonIgnore
    @Expose
    @SerializedName("isFavourite")
    var isFavourite: Int = 0,

    @Expose
    @SerializedName("isPromoted")
    var isPromoted: Int = 0,

    @Expose
    @SerializedName("isBusinessAccount")
    var isBusinessAccount: Boolean = false,
    @Expose
    @SerializedName("isPublic")
    var isPublic: Int = 1,

    @Expose
    @SerializedName("websiteLink")
    var websiteLink: String? = null,

    @Expose
    @SerializedName("youTubeLink")
    var youTubeLink: String? = null,

    @Expose
    @SerializedName("twitterLink")
    var twitterLink: String? = null,

    @Expose
    @SerializedName("instagramLink")
    var instagramLink: String? = null,

    @Expose
    @SerializedName("fbLink")
    var fbLink: String? = null,

    @Expose
    @SerializedName("gender")
    var gender: String? = null,

    @Expose
    @SerializedName("profession")
    var profession: String? = null,

    @Expose
    @SerializedName("about")
    var about: String? = null,

    @Expose
    @SerializedName("address")
    var address: String? = null,

    @Expose
    @SerializedName("dateOfBirth")
    var dateOfBirth: String? = null,

    @Expose
    @SerializedName("location")
    var location: String? = null,

    @Expose
    @SerializedName("profilePicture")
    var profilePicture: String? = null,

    @Expose
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null,

    @Expose
    @SerializedName("name")
    var name: String? = null,

    @Expose
    @SerializedName("email")
    var email: String? = null,

    @PrimaryKey
    @Expose
    @SerializedName("id")
    var id: Long = 0
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun isFavourite(): Boolean = isFavourite == 1

    fun isPromoted(): Boolean = isPromoted == 1

    fun isBusinessAccount(): Boolean = isBusinessAccount

    fun getProfilePictureUri(): Uri? {
        return if (!profilePicture.isNullOrEmpty()) Uri.parse(profilePicture) else null
    }
}
