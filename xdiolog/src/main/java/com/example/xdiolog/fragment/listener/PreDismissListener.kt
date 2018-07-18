package com.example.xdiolog.fragment.listener

import android.view.View
import com.example.xdiolog.fragment.DismissAction

interface PreDismissListener {
    fun preDismiss(parent: View, dismissAction: DismissAction): Boolean
}