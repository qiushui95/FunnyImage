package me.yangcx.xdialog.ask.sheet

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import me.yangcx.xdialog.ask.common.AskItemClickListener
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.entity.TextConfig
import me.yangcx.xdialog.utils.DefaultConfig

internal data class SheetConfig(val title: TextConfig, val message: TextConfig, val cancel: TextConfig, val clickListener: AskItemClickListener?) : Parcelable {
    val buttonList = mutableListOf<TextConfig>()

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(TextConfig::class.java.classLoader)
                    ?: TextConfig(null, Color.BLACK, true, DefaultConfig.DEFAULT_TITLE_SIZE, DimensionEnum.SP),
            parcel.readParcelable(TextConfig::class.java.classLoader)
                    ?: TextConfig(null, Color.BLACK, false, DefaultConfig.DEFAULT_MESSAGE_SIZE, DimensionEnum.SP),
            parcel.readParcelable(TextConfig::class.java.classLoader)
                    ?:TextConfig(null, Color.BLACK, true, DefaultConfig.DEFAULT_MESSAGE_SIZE, DimensionEnum.SP),
            parcel.readParcelable(AskItemClickListener::class.java.classLoader)) {
        parcel.readList(buttonList,TextConfig::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(title, flags)
        parcel.writeParcelable(message, flags)
        parcel.writeParcelable(cancel, flags)
        parcel.writeParcelable(clickListener, flags)
        parcel.writeList(buttonList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SheetConfig> {
        override fun createFromParcel(parcel: Parcel): SheetConfig {
            return SheetConfig(parcel)
        }

        override fun newArray(size: Int): Array<SheetConfig?> {
            return arrayOfNulls(size)
        }
    }
}