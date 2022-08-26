package com.refreshing.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.print.PrintHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.refreshing.databinding.PrintBinding
import com.refreshing.databinding.PrintReceiptBinding
import com.refreshing.datalayer.models.orderDetails.OrderDetails
import com.refreshing.datalayer.setFontSizeForPath
import com.refreshing.ui.base.BaseActivity
import com.refreshing.ui.orderdetails.productadapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.toLongOrDefault


@AndroidEntryPoint
class Print : BaseActivity() {

    companion object {
        fun startPrint(oderDetails: OrderDetails, context: Context) {
            val intent = Intent(context, Print::class.java)
            intent.putExtra("order", oderDetails)
            context.startActivity(intent)
        }
    }

    var adapter: ProductAdapter? = null

    lateinit var binding:  PrintBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<OrderDetails>("order")


        binding.orderNumber.text = "ORDER NO.\n${item?.data?.first()?.orderNumber}".setFontSizeForPath(
            item?.data?.first()?.orderNumber.toString(),100)


        binding.shippingPrice.text = "${item?.data?.first()?.price_ship}"
        binding.disCount.text = "${item?.data?.first()?.discount}"+"%"
        binding.total.text  ="${item?.data?.first()?.total_without_ship}"
        binding.disCount3.text="${ item?.data?.first()?.total}"


        adapter = ProductAdapter()
        binding?.recyclerViewNewItems?.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewNewItems?.adapter = adapter
        item?.data?.first()?.products?.let { it1 -> adapter?.submitNew(it1) }

        binding.date.text=item?.data?.first()?.orderCreated
        binding.paymentMethod.text=item?.data?.first()?.paymentMethod

        binding.paymentMethod2.text=item?.data?.first()?.user
        binding.status3.text= item?.data?.first()?.address
        binding.paymentMethod3.text=item?.data?.first()?.phone

        binding.textView15.text= "يوجد عدد ( " + adapter?.itemCount + " ) صنف"

        binding.button?.setOnClickListener {

            doPhotoPrint()


        }
        try {
            val barcodeEncoder = BarcodeEncoder()
            val lat =item?.data?.first()?.lat
            val long =item?.data?.first()?.long

            val bitmap = barcodeEncoder.encodeBitmap("geo:$lat,$long",
                BarcodeFormat.QR_CODE, 400, 400)

            binding.imageView5.setImageBitmap(bitmap)
        } catch (e: Exception) {
        }


    }

    @Throws(EscPosConnectionException::class)
    private fun doPhotoPrint() {
        val photoPrinter = PrintHelper(this)

        photoPrinter.scaleMode = PrintHelper.SCALE_MODE_FIT
        val totalHeight: Int = binding.sc.getChildAt(0).getHeight()
        val totalWidth: Int = binding.sc.getChildAt(0).getWidth()
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val bitmap = Bitmap.createScaledBitmap(
            getBitmapFromView(binding.sc, totalHeight, totalWidth)!!,
            width, totalHeight, false
        )

        photoPrinter.printBitmap("droids.jpg - test print", bitmap)
    }

    fun getBitmapFromView(view: View, totalHeight: Int, totalWidth: Int): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    fun getViewBitmap(v: View): Bitmap? {
        v.clearFocus()
        v.isPressed = false
        val willNotCache = v.willNotCacheDrawing()
        v.setWillNotCacheDrawing(false)

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        val color = v.drawingCacheBackgroundColor
        v.drawingCacheBackgroundColor = 0
        if (color != 0) {
            v.destroyDrawingCache()
        }
        v.buildDrawingCache()
        val cacheBitmap = v.drawingCache ?: return null
        val bitmap = Bitmap.createBitmap(cacheBitmap)

        // Restore the view
        v.destroyDrawingCache()
        v.setWillNotCacheDrawing(willNotCache)
        v.drawingCacheBackgroundColor = color
        return bitmap
    }


}