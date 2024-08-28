package com.codeseyee.cloudcontacts.adapters


import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.codeseye.cloudcontacts.contactutils.Contact
import com.codeseye.cloudcontacts.models.User
import com.codeseyee.cloudcontacts.R
import com.codeseyee.cloudcontacts.databinding.ItemAddContactBinding
import com.codeseyee.cloudcontacts.databinding.ItemLabelBinding
import com.codeseyee.cloudcontacts.databinding.ItemUserBinding
import com.codeseyee.cloudcontacts.room.AppDatabase
import com.codeseyee.cloudcontacts.viewholders.AddContactViewHolder
import com.codeseyee.cloudcontacts.viewholders.BaseViewHolder
import com.codeseyee.cloudcontacts.viewholders.ContactViewHolder
import com.codeseyee.cloudcontacts.viewholders.LabelViewHolder
import com.codeseyee.cloudcontacts.viewholders.UserViewHolder

class GenericAdapter(
    val context: Context,
    private val data: List<Any>
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val colorCodes: Array<String> = context.resources.getStringArray(R.array.dp_colors)
    private val appDatabase: AppDatabase = AppDatabase.getDatabase()
    val cardDefaultBgColor: Int

    var selectionListener: MultipleSelectionInterface? = null

    init {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(R.attr.homeToolbarColor, typedValue, true)
        cardDefaultBgColor = typedValue.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val binding = ItemUserBinding.inflate(inflater, parent, false)
                UserViewHolder(binding)
            }
            VIEW_TYPE_ADD_CONTACT -> {
                val binding = ItemAddContactBinding.inflate(inflater, parent, false)
                AddContactViewHolder(binding)
            }
            VIEW_TYPE_CONTACT -> {
                val binding = ItemUserBinding.inflate(inflater, parent, false)
                ContactViewHolder(binding)
            }
            VIEW_TYPE_LABEL -> {
                val binding = ItemLabelBinding.inflate(inflater, parent, false)
                LabelViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is String -> {
                val str = data[position] as String
                when {
                    str == "addContactRow" -> VIEW_TYPE_ADD_CONTACT
                    str.startsWith("label:") -> VIEW_TYPE_LABEL
                    else -> 0
                }
            }
            is User -> VIEW_TYPE_USER
            is Contact -> VIEW_TYPE_CONTACT
            else -> 0
        }
    }

    override fun onBindViewHolder(@NonNull holder: BaseViewHolder, position: Int) {
        try {
            holder.bind(this, data[position])
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int = data.size

    interface MultipleSelectionInterface {
        fun onEnterSelectionMode()
        fun isSelectionMode(): Boolean
        fun onSelectionChanged(`object`: Any)
    }

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_ADD_CONTACT = 2
        private const val VIEW_TYPE_CONTACT = 3
        private const val VIEW_TYPE_LABEL = 4
    }
}
