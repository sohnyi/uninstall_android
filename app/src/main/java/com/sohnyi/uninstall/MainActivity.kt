package com.sohnyi.uninstall

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohnyi.uninstall.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        PackageAdapter(::onDeleteClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.packageList.layoutManager = LinearLayoutManager(this)
        binding.packageList.adapter = adapter

        updatePackages()

    }

    private fun updatePackages() {
        binding.pb.visibility = View.VISIBLE
        val packageInfoList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
        } else {
            packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        }

        MainScope().launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                adapter.submitList(packageInfoList)
                binding.pb.visibility = View.GONE
            }
        }
    }

    private fun onDeleteClicked(pi: PackageInfo) {
        try {
            startActivity(Intent(Intent.ACTION_UNINSTALL_PACKAGE).apply {
                data = Uri.parse("package:${pi.packageName}")
            })
            updatePackages()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}