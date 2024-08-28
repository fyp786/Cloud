package com.codeseyee.cloudcontacts.viewholders


import android.view.View
import androidx.annotation.NonNull
import com.codeseyee.cloudcontacts.adapters.GenericAdapter
import com.codeseyee.cloudcontacts.databinding.ItemLabelBinding

class LabelViewHolder(private val binding: ItemLabelBinding) : BaseViewHolder(binding.root) {

    override fun bind(adapter: GenericAdapter, `object`: Any) {
        var label = `object` as String
        label = label.replace("label:", "")

        if (label.contains("-divider")) {
            label = label.replace("-divider", "")
            binding.divider3.visibility = View.GONE
        } else {
            binding.divider3.visibility = View.VISIBLE
        }

        binding.title.text = label
    }
}
