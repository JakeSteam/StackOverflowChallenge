package uk.co.jakelee.stackoverflowchallenge.model

import android.os.Parcel
import android.os.Parcelable

data class BadgeList(
    val bronze: Int,
    val silver: Int,
    val gold: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bronze)
        parcel.writeInt(silver)
        parcel.writeInt(gold)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BadgeList> {
        override fun createFromParcel(parcel: Parcel): BadgeList {
            return BadgeList(parcel)
        }

        override fun newArray(size: Int): Array<BadgeList?> {
            return arrayOfNulls(size)
        }
    }
}