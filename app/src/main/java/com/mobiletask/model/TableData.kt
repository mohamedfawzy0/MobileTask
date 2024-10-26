import android.os.Parcel
import android.os.Parcelable

data class TableData(val key: String, var value: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TableData> {
        override fun createFromParcel(parcel: Parcel): TableData {
            return TableData(parcel)
        }

        override fun newArray(size: Int): Array<TableData?> {
            return arrayOfNulls(size)
        }
    }
}
