package com.codeseyee.cloudcontacts.viewholders


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codeseyee.cloudcontacts.adapters.GenericAdapter

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(adapter: GenericAdapter, item: Any)
}
