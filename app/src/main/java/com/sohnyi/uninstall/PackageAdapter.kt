package com.sohnyi.uninstall

import android.content.pm.PackageInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sohnyi.uninstall.databinding.ItemPackageBinding
import java.lang.Exception

class PackageAdapter : ListAdapter<PackageInfo, PackageAdapter.PackageHolder>(PackageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemPackageBinding.inflate(inflate, parent, false)
        return PackageHolder(binding)
    }

    override fun onBindViewHolder(holder: PackageHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PackageHolder(private val binding: ItemPackageBinding) : ViewHolder(binding.root) {
        fun bind(pi: PackageInfo) {
            try {
                val pm = binding.root.context.packageManager
                binding.ivIcon.setImageDrawable(pm.getApplicationIcon(pi.applicationInfo))
                binding.name.text = pm.getApplicationLabel(pi.applicationInfo)
                binding.id.text = pi.packageName
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}