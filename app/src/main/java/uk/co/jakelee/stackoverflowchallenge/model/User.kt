package uk.co.jakelee.stackoverflowchallenge.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val id: Int,

    @SerializedName("reputation")
    val reputation: Int,

    @SerializedName("creation_date")
    val created: Long,

    @SerializedName("location")
    val location: String?,

    @SerializedName("age")
    val age: Int?,

    @SerializedName("profile_image")
    val avatar: String,

    @SerializedName("display_name")
    val name: String,

    @SerializedName("badge_counts")
    val badges: BadgeList
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(BadgeList::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(reputation)
        parcel.writeLong(created)
        parcel.writeString(location)
        parcel.writeValue(age)
        parcel.writeString(avatar)
        parcel.writeString(name)
        parcel.writeParcelable(badges, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}