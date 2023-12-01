package com.itzel.fabulash
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.YuvImage
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.ByteArrayOutputStream
import java.io.IOException

internal class CameraPreviewActivity @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    private var mCamera: Camera? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback, Camera.PreviewCallback {
    private val TAG = "CameraPreview"

    init {
        holder.addCallback(this)
        mCamera = getCameraInstance()
        // Configura la orientación de la cámara para evitar la imagen volteada
        mCamera?.setDisplayOrientation(90)
    }

    private fun getCameraInstance(): Camera? {
        var c: Camera? = null
        try {
            // Intenta obtener una instancia de la cámara frontal (cámara con ID 1)
            val cameraInfo = Camera.CameraInfo()
            for (cameraId in 0 until Camera.getNumberOfCameras()) {
                Camera.getCameraInfo(cameraId, cameraInfo)
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    c = Camera.open(cameraId)
                    break
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error opening camera: ${e.message}")
        }
        return c
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            mCamera?.setPreviewDisplay(holder)
            // Configura el formato de previsualización y el tamaño de la imagen
            val parameters = mCamera?.parameters
            parameters?.previewFormat = ImageFormat.NV21
            val previewSize = getBestPreviewSize(holder.surfaceFrame.width(), holder.surfaceFrame.height())
            parameters?.setPreviewSize(previewSize.width, previewSize.height)
            mCamera?.parameters = parameters
            mCamera?.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "Error setting up camera preview: ${e.message}")
        }
    }

    private fun getBestPreviewSize(width: Int, height: Int): Camera.Size {
        val sizes = mCamera?.parameters?.supportedPreviewSizes

        if (sizes.isNullOrEmpty()) {
            Log.e(TAG, "No hay tamaños de previsualización compatibles")
            throw RuntimeException("No hay tamaños de previsualización compatibles")
        }

        var bestSize = sizes[0]

        for (size in sizes) {
            if (size.width * size.height > bestSize.width * bestSize.height) {
                bestSize = size
            }
        }

        Log.d(TAG, "Mejor tamaño de previsualización: ${bestSize.width} x ${bestSize.height}")
        return bestSize
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // Configura el tamaño y la orientación de la previsualización aquí
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // Detiene la previsualización y libera la cámara cuando se destruye la superficie
        mCamera?.stopPreview()
        mCamera?.release()
    }

    override fun onPreviewFrame(data: ByteArray, camera: Camera) {
        // Aquí puedes procesar los datos del fotograma (byte[]) y aplicar los filtros
        // Por ejemplo, convierte los datos en un Bitmap y rota la imagen
        val previewSize = camera.parameters.previewSize
        val rotatedBitmap = rotateBitmap(
            data, previewSize.width, previewSize.height, 90
        )

        // Aplica un filtro a la imagen
        val filteredBitmap = applyFilter(rotatedBitmap)

        // Muestra la imagen en la vista previa
        val canvas = holder.lockCanvas()
        if (canvas != null) {
            canvas.drawBitmap(filteredBitmap, 0f, 0f, null)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun rotateBitmap(data: ByteArray, width: Int, height: Int, rotationDegrees: Int): Bitmap {
        val yuvImage = YuvImage(data, ImageFormat.NV21, width, height, null)
        val baos = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, baos)
        val jpegData = baos.toByteArray()
        val originalBitmap = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.size)

        // Rota la imagen según la orientación de la cámara
        val matrix = Matrix()
        matrix.postRotate(rotationDegrees.toFloat())
        return Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)
    }

    private fun applyFilter(source: Bitmap): Bitmap {
        // Aquí puedes aplicar tu lógica de filtro
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f) // Desaturar la imagen
        val filter = ColorMatrixColorFilter(colorMatrix)
        val paint = Paint()
        paint.colorFilter = filter
        val filteredBitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(filteredBitmap)
        canvas.drawBitmap(source, 0f, 0f, paint)
        return filteredBitmap
    }

    fun startCamera() {
        try {
            mCamera?.setPreviewDisplay(holder)
            val parameters = mCamera?.parameters
            parameters?.previewFormat = ImageFormat.NV21
            val previewSize = getBestPreviewSize(holder.surfaceFrame.width(), holder.surfaceFrame.height())
            parameters?.setPreviewSize(previewSize.width, previewSize.height)
            mCamera?.parameters = parameters
            mCamera?.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "Error al configurar la previsualización de la cámara: ${e.message}")
            // Puedes realizar acciones adicionales en caso de error, como mostrar un mensaje al usuario.
        }
    }

    fun stopCamera() {
        mCamera?.stopPreview()
        mCamera?.release()
        mCamera = null
    }
}