package com.codeseyee.cloudcontacts.viewholders


import android.content.Intent
import android.view.View
import com.codeseyee.cloudcontacts.AddContact
import com.codeseyee.cloudcontacts.R
import com.codeseyee.cloudcontacts.ScannerActivity
import com.codeseyee.cloudcontacts.adapters.GenericAdapter
import com.codeseyee.cloudcontacts.databinding.ItemAddContactBinding
import com.squareup.picasso.BuildConfig

class AddContactViewHolder(private val binding: ItemAddContactBinding) : BaseViewHolder(binding.root) {

    override fun bind(adapter: GenericAdapter, item: Any) {
        binding.layoutAddByEmail.setOnClickListener {
            val intent = Intent(adapter.context, AddContact::class.java).apply {
                putExtra("type", "email")
            }
            adapter.context.startActivity(intent)
        }

        binding.layoutAddByPhone.setOnClickListener {
            val intent = Intent(adapter.context, AddContact::class.java).apply {
                putExtra("type", "phone")
            }
            adapter.context.startActivity(intent)
        }

        binding.layoutAddByQrCode.setOnClickListener {
            val intent = Intent(adapter.context, ScannerActivity::class.java).apply {
                putExtra("toAdd", true)
            }
            adapter.context.startActivity(intent)
        }

        binding.layoutAddByInvite.setOnClickListener {
            val shareText = "Hey, check out this wonderful application named \"" +
                    adapter.context.getString(R.string.app_name) +
                    "\" that let backup contacts and search contacts nearby you: " +
                    "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            AppUtils.shareText(adapter.context, shareText)
        }
    }
}
