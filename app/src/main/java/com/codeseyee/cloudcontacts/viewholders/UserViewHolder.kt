package com.codeseyee.cloudcontacts.viewholders


import android.app.Activity
import android.graphics.Color
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.codeseye.cloudcontacts.models.User
import com.codeseyee.cloudcontacts.adapters.GenericAdapter
import com.codeseyee.cloudcontacts.databinding.ItemUserBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class UserViewHolder(@NonNull private val binding: ItemUserBinding) : BaseViewHolder(binding.root) {

    override fun bind(adapter: GenericAdapter, `object`: Any) {
        val user = `object` as User

        binding.itemView.setOnClickListener {
            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                adapter.context as Activity,
                Pair(binding.name, "name"),
                Pair(binding.phone, "phone"),
                Pair(binding.nameArtText, "nameArt"),
                Pair(binding.image, "image")
            )
            AppUtils.launchDetailsActivity(adapter.context, user, activityOptionsCompat.toBundle())
        }

        binding.name.text = Html.fromHtml(user.name)
        binding.phone.text = user.phoneNumber
        val about = buildString {
            user.location?.let { append("$it - ") }
            user.profession?.let { append(it) }
            if (user.location == null || user.profession == null) {
                append(user.email)
            }
        }
        binding.moreInfo.text = about

        binding.image.setImageResource(android.R.color.transparent)

        val initialChar = user.name.first()
        binding.nameArtText.text = initialChar.toUpperCase().toString()
        binding.nameArt.setCardBackgroundColor(
            Color.parseColor(adapter.colorCodes[(initialChar.code % adapter.colorCodes.size)])
        )

        user.profilePicture?.let {
            Picasso.get().load(it)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(200, 200)
                .into(binding.image, object : Callback {
                    override fun onSuccess() {
                        binding.nameArtText.text = ""
                        binding.nameArt.setCardBackgroundColor(Color.WHITE)
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get().load(it).resize(200, 200).into(binding.image)
                    }
                })
        }

        binding.call.setOnClickListener {
            if (user.phoneNumber.isNullOrEmpty()) {
                Toast.makeText(adapter.context, "No phone number found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppUtils.makeCall(adapter.context as Activity, user.phoneNumber)
        }

        binding.message.setOnClickListener {
            if (user.phoneNumber.isNullOrEmpty()) {
                Toast.makeText(adapter.context, "No phone number found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppUtils.openSmsApp(adapter.context as Activity, user.phoneNumber)
        }
    }
}
