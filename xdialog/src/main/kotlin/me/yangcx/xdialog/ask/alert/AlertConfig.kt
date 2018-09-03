package me.yangcx.xdialog.ask.alert

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import me.yangcx.xdialog.ask.common.AskItemClickListener
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.entity.TextConfig
import me.yangcx.xdialog.utils.DefaultConfig

internal class AlertConfig(val title: TextConfig, val message: TextConfig, val clickListener: AskItemClickListener?) : Parcelable {
    val buttonList = mutableListOf<TextConfig>()

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(TextConfig::class.java.classLoader)
                    ?: TextConfig(null, Color.BLACK, true, DefaultConfig.DEFAULT_TITLE_SIZE, DimensionEnum.SP),
            parcel.readParcelable(TextConfig::class.java.classLoader)
                    ?: TextConfig(null, Color.BLACK, true, DefaultConfig.DEFAULT_MESSAGE_SIZE, DimensionEnum.SP),
            parcel.readParcelable(AskItemClickListener::class.java.classLoader)) {
        parcel.readList(buttonList, TextConfig::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(title, flags)
        parcel.writeParcelable(message, flags)
        parcel.writeParcelable(clickListener, flags)
        parcel.writeList(buttonList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlertConfig> {
        override fun createFromParcel(parcel: Parcel): AlertConfig {
            return AlertConfig(parcel)
        }

        override fun newArray(size: Int): Array<AlertConfig?> {
            return arrayOfNulls(size)
        }
    }
}