package me.yangcx.xdialog.utils

import android.content.res.Resources
import android.util.TypedValue
import me.yangcx.xdialog.entity.DimensionEnum
import kotlin.math.roundToInt

internal object DimensionUtils {

    fun toPxFloat(value: Float, dimensionEnum: DimensionEnum): Float {
        return when (dimensionEnum) {
            DimensionEnum.DP -> dp2pxFloat(value)
            DimensionEnum.SP -> sp2pxFloat(value)
            DimensionEnum.PX -> value
        }
    }

    fun toPxInt(value: Float, dimensionEnum: DimensionEnum): Int {
        return toPxFloat(value, dimensionEnum).roundToInt()
    }

    private fun sp2pxFloat(spValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().displayMetrics)
    }

    private fun dp2pxFloat(dpValue: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().displayMetrics)
    }

}