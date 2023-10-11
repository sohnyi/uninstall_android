package com.sohnyi.uninstall

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohnyi.uninstall.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        PackageAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pb.visibility = View.VISIBLE

        binding.packageList.layoutManager = LinearLayoutManager(this)
        binding.packageList.adapter = adapter

        MainScope().launch(Dispatchers.IO) {
            val pList = getAllPackages()
            withContext(Dispatchers.Main) {
                adapter.submitList(pList)
                binding.pb.visibility = View.GONE
            }
        }
    }

    // FIXME: can not get all packages
    // FIXME: getInstalledPackages() Deprecated
    private fun getAllPackages(): List<PackageInfo> {
        val packageInfoList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
        } else {
            packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        }

        return packageInfoList
    }
}