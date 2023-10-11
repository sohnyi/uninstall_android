package com.sohnyi.uninstall

import android.content.pm.PackageInfo
import androidx.recyclerview.widget.DiffUtil

object PackageDiffCallback : DiffUtil.ItemCallback<PackageInfo>() {

    override fun areItemsTheSame(oldItem: PackageInfo, newItem: PackageInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PackageInfo, newItem: PackageInfo): Boolean {
        return oldItem.applicationInfo.name == newItem.applicationInfo.name
    }
}