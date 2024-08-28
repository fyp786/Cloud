package com.codeseyee.cloudcontacts.viewholders


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.codeseye.cloudcontacts.models.User
import com.codeseyee.cloudcontacts.Models.UserQR
import com.codeseyee.cloudcontacts.R
import com.codeseyee.cloudcontacts.adapters.GenericAdapter
import com.codeseyee.cloudcontacts.databinding.ItemContactCardBinding
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.common.BitMatrix
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

class ContactCardViewHolder(private val binding: ItemContactCardBinding) : BaseViewHolder(binding.root) {

    private lateinit var context: Context
    private lateinit var user: User
    private lateinit var colorCodes: Array<String>

    fun bind(context: Context, user: User) {
        this.context = context
        this.user = user
        colorCodes = context.resources.getStringArray(R.array.dp_colors)

        binding.title.text = user.name
        binding.contact.text = user.phoneNumber

        binding.layoutContact.setOnClickListener {
            if (user.phoneNumber.isNullOrEmpty()) {
                Toast.makeText(context, "No phone number found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AppUtils.makeCall(context as Activity, user.phoneNumber)
        }

        try {
            binding.qrCode.setImageBitmap(generateQRCode(Gson().toJson(UserQR(user))))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!user.email.isNullOrEmpty()) {
            binding.emailLayout.visibility = View.VISIBLE
            binding.email.text = user.email
            binding.emailLayout.setOnClickListener {
                AppUtils.sendEmail(context, user.email, "", "", "Email using")
            }
        } else {
            binding.emailLayout.visibility = View.GONE
        }

        if (!user.websiteLink.isNullOrEmpty()) {
            binding.layoutWebsite.visibility = View.VISIBLE
            binding.website.text = user.websiteLink.replace("http://", "").replace("https://", "").split("/")[0]
            binding.layoutWebsite.setOnClickListener {
                AppUtils.openLink(context, user.websiteLink)
            }
        } else {
            binding.layoutWebsite.visibility = View.GONE
        }

        if (!user.address.isNullOrEmpty()) {
            binding.addressLocationIcon.visibility = View.VISIBLE
            binding.address.text = user.address
        } else {
            binding.address.text = "www.contactcard.me"
            binding.addressLocationIcon.visibility = View.GONE
        }

        if (!user.location.isNullOrEmpty()) {
            binding.location.text = user.location
        } else {
            binding.location.visibility = View.GONE
        }

        val c = user.name[0]
        binding.nameArtText.text = c.toUpperCase().toString()
        binding.nameArt.setCardBackgroundColor(Color.parseColor(colorCodes[(c.toInt()) % colorCodes.size]))

        if (user.profilePicture != null) {
            Picasso.get()
                .load(user.profilePicture)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(500, 500)
                .into(binding.image, object : Callback {
                    override fun onSuccess() {
                        binding.nameArtText.text = ""
                        binding.nameArt.setCardBackgroundColor(Color.WHITE)
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get()
                            .load(user.profilePicture)
                            .resize(100, 100)
                            .into(binding.image)
                    }
                })
        }

        itemView.setOnClickListener {
            // Uncomment and modify the intent code for details view if needed
            // val intent = Intent(context, UserDetailsActivity::class.java).apply {
            //     putExtra("data", Gson().toJson(user))
            //     putExtra("url", url ?: "")
            // }
            // val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            //     context as Activity,
            //     Pair(binding.title, "title"),
            //     Pair(binding.brandImage, "image")
            // )
            // context.startActivity(intent, options.toBundle())
        }

        if (context is AppUtils.BottomSheetInterface) {
            binding.more.visibility = View.VISIBLE
            binding.more.setOnClickListener {
                AppUtils.showCardOptionsBottomSheet(user, context, object : AppUtils.CardOptionsBottomSheetListener {
                    override fun onSaveAsImageSelected() {
                        saveInGallery()
                    }

                    override fun onShareSelected() {
                        saveInCacheAndShare()
                    }
                })
            }
        } else {
            binding.more.visibility = View.GONE
        }
    }

    private fun generateQRCode(content: String): Bitmap? {
        val writer = QRCodeWriter()
        return try {
            val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 300, 300)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bmp
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveInCacheAndShare() {
        binding.more.visibility = View.GONE
        binding.cv.isDrawingCacheEnabled = true
        val bitmap = binding.cv.drawingCache

        val root = context.cacheDir.toString()
        val newDir = File("$root/${context.getString(R.string.app_name)}")
        newDir.mkdirs()
        val file = File(newDir, "contactCard.jpg")

        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
            }
            AppUtils.shareImage(context, file.path, "Share contact card on")
        } catch (e: Exception) {
            Toast.makeText(context, "error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        binding.more.visibility = View.VISIBLE
    }

    private fun saveInGallery() {
        binding.more.visibility = View.GONE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            (context as Activity).requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 11
            )
            binding.more.visibility = View.VISIBLE
            return
        }
        binding.cv.isDrawingCacheEnabled = true
        val bitmap = binding.cv.drawingCache

        val root = Environment.getExternalStorageDirectory().toString()
        val newDir = File("$root/${context.getString(R.string.app_name)}/Contact Cards")
        newDir.mkdirs()
        val file = File(newDir, "${user.id}.jpg")

        try {
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
            }
            MediaScannerConnection.scanFile(context, arrayOf(file.path), arrayOf("image/jpeg"), null)
            Toast.makeText(context, "Saved in gallery", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        binding.more.visibility = View.VISIBLE
    }

    override fun bind(adapter: GenericAdapter, `object`: Any) {
        bind(adapter.context, `object` as User)
    }
}
