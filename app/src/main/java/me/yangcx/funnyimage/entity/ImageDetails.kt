package me.yangcx.funnyimage.entity

import me.yangcx.forrecyclerview.entity.IAdapterData

class ImageDetails(
        id: String,
        width: Int,
        height: Int,
        thumb: String,
        small: String,
        regular: String,
        full: String,
        raw: String,
        var collected: Boolean = false) : ImageInfo(0, id, width, height, thumb, small, regular, full, raw), IAdapterData {
    override fun copySelf(): IAdapterData {
        return ImageDetails(id, width, height, thumb, small, regular, full, raw, collected)
    }

    override fun isSame(item: IAdapterData): Boolean {
        return item is ImageDetails && isIdSame(item)
    }

    override fun isContentSame(item: IAdapterData): Boolean {
        return item is ImageDetails && isWidthSame(item) && isHeightSame(item) && isThumbSame(item) && isSmallSame(item) && isRegularSame(item) && isFullSame(item) && isRawSame(item) && isCollectedSame(item)
    }

    fun isIdSame(item: ImageDetails) = item.id == id

    fun isWidthSame(item: ImageDetails) = item.width == width

    fun isHeightSame(item: ImageDetails) = item.height == height

    fun isThumbSame(item: ImageDetails) = item.thumb == thumb

    fun isSmallSame(item: ImageDetails) = item.small == small

    fun isRegularSame(item: ImageDetails) = item.regular == regular

    fun isFullSame(item: ImageDetails) = item.full == full

    fun isRawSame(item: ImageDetails) = item.raw == raw

    fun isCollectedSame(item: ImageDetails) = item.collected == collected

    override fun getChangePayload(item: IAdapterData, payloadList: MutableList<String>) {
        if (item is ImageDetails) {
            if (isCollectedSame(item)) {
                payloadList.add("collected")
            }
        }
    }
}