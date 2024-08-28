package com.codeseyee.cloudcontacts.viewholders


import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.codeseye.cloudcontacts.contactutils.Contact
import com.codeseyee.cloudcontacts.adapters.GenericAdapter
import com.codeseyee.cloudcontacts.databinding.ItemUserBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ContactViewHolder(private val binding: ItemUserBinding) : BaseViewHolder(binding.root) {

    override fun bind(adapter: GenericAdapter, `object`: Any) {
        val contact = `object` as Contact

        binding.itemView.setOnClickListener {
            if (adapter.listener?.isSelectionMode == true) {
                contact.isSelected = !contact.isSelected
                adapter.listener?.onSelectionChanged(contact)
                refreshBackground(adapter, contact)
                return@setOnClickListener
            }
            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                adapter.context as Activity,
                Pair(binding.name, "name"),
                Pair(binding.phone, "phone"),
                Pair(binding.nameArtText, "nameArt"),
                Pair(binding.image, "image")
            )
            AppUtils.launchDetailsActivity(adapter.context, contact, activityOptionsCompat.toBundle())
        }

        binding.itemView.setOnLongClickListener {
            if (adapter.listener?.isSelectionMode == false) {
                adapter.listener?.onEnterSelectionMode()
                binding.itemView.performClick()
            }
            true
        }

        binding.name.text = contact.displayName
        binding.phone.text = contact.phoneNumbers?.takeIf { it.isNotEmpty() } ?: "Number not found"

        val about = buildString {
            contact.companyName?.let { append(it).append(" ") }
            contact.companyTitle?.let { append(it).append(" ") }
            contact.emails?.let { append(it).append(" ") }
        }
        binding.moreInfo.text = about

        binding.image.setImageResource(android.R.color.transparent)

        val c = contact.displayName[0]
        binding.nameArtText.text = c.toUpperCase().toString()
        binding.nameArt.setCardBackgroundColor(Color.parseColor(adapter.colorCodes[(c.toInt()) % adapter.colorCodes.size]))

        contact.photoUri?.let {
            Picasso.get()
                .load(it)
                .resize(100, 100)
                .into(binding.image, object : Callback {
                    override fun onSuccess() {
                        binding.nameArtText.text = ""
                        binding.nameArt.setCardBackgroundColor(Color.WHITE)
                    }

                    override fun onError(e: Exception?) {}
                })
        }

        binding.call.setOnClickListener {
            if (contact.phoneNumbers.isNullOrEmpty()) {
                Toast.makeText(adapter.context, "Phone number not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppUtils.makeCall(adapter.context as Activity, contact.phoneNumbers)
        }

        binding.message.setOnClickListener {
            if (contact.phoneNumbers.isNullOrEmpty()) {
                Toast.makeText(adapter.context, "No phone number found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppUtils.openSmsApp(adapter.context as Activity, contact.phoneNumbers)
        }

        refreshBackground(adapter, contact)
    }

    private fun refreshBackground(adapter: GenericAdapter, contact: Contact) {
        binding.itemView.setBackgroundColor(
            if (contact.isSelected) Color.parseColor("#6A5598DC") else adapter.cardDefaultBgColor
        )
    }
}
