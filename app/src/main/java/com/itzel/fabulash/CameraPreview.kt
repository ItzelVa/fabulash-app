package com.itzel.fabulash
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.itzel.fabulash.databinding.ActivityHomeBinding

private const val REQUEST_CAMERA_PERMISSION = 1

class CameraPreview : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mCameraPreview: CameraPreviewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_preview)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        // Verificar y solicitar permisos.
        checkCameraPermission()

        // Inicializa la cámara en el onCreate solo si savedInstanceState es nulo.
        if (savedInstanceState == null) {
            initializeCamera()
        }

        val back = findViewById<ImageButton>(R.id.filterBackButton)
        back.setOnClickListener {
            val intent = Intent(this, Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        findViewById<MaterialCardView>(R.id.l1).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l2).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l3).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l4).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l5).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l6).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l7).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l8).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l9).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.filter10).visibility = View.GONE
        }

        findViewById<MaterialCardView>(R.id.l10).setOnClickListener {
            findViewById<ImageView>(R.id.filter1).visibility = View.GONE
            findViewById<ImageView>(R.id.filter2).visibility = View.GONE
            findViewById<ImageView>(R.id.filter3).visibility = View.GONE
            findViewById<ImageView>(R.id.filter4).visibility = View.GONE
            findViewById<ImageView>(R.id.filter5).visibility = View.GONE
            findViewById<ImageView>(R.id.filter6).visibility = View.GONE
            findViewById<ImageView>(R.id.filter7).visibility = View.GONE
            findViewById<ImageView>(R.id.filter8).visibility = View.GONE
            findViewById<ImageView>(R.id.filter9).visibility = View.GONE
            findViewById<ImageView>(R.id.filter10).visibility = View.VISIBLE
        }

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no tienes permiso, solicítalo.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            // Ya tienes permiso, puedes acceder a la cámara.
            initializeCamera()
        }
    }

    private fun initializeCamera() {
        mCameraPreview = findViewById(R.id.camera_preview)
        mCameraPreview.startCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, puedes acceder a la cámara.
                    initializeCamera()
                } else {
                    // Permiso denegado, muestra un mensaje o toma alguna acción.
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                // Manejar otros casos de solicitud de permiso si es necesario.
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mCameraPreview.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        mCameraPreview.startCamera()
    }
}