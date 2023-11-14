package com.itzel.fabulash
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itzel.fabulash.databinding.AccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Account : AppCompatActivity() {
    private lateinit var binding: AccountBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = AccountBinding.inflate(layoutInflater)

        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account)

        val backButton = findViewById<ImageButton>(R.id.accountBackButton)
        val addButton = findViewById<Button>(R.id.b1)
        val logout = findViewById<Button>(R.id.logoutButton)
        val delete = findViewById<Button>(R.id.deleteButton)
        val viewCards = findViewById<Button>(R.id.view_button)
        val addCard = findViewById<Button>(R.id.add_button)
        val deleteCard = findViewById<Button>(R.id.delete_button)
        val modCard = findViewById<Button>(R.id.edit_button)
        val myReviews = findViewById<Button>(R.id.b3)
        val modData = findViewById<Button>(R.id.b2)

        modData.setOnClickListener {
            val view = Intent(this,modificar_datos::class.java)
            startActivity(view)
        }

        deleteCard.setOnClickListener {
            val view = Intent(this,DeleteCard::class.java)
            startActivity(view)
        }

        viewCards.setOnClickListener{
            val view = Intent(this,ViewCards::class.java)
            startActivity(view)
        }

        addCard.setOnClickListener {
            val view = Intent(this,RegisterCard::class.java)
            startActivity(view)
        }

        modCard.setOnClickListener {
            val view = Intent(this,ModifyCard::class.java)
            startActivity(view)
        }

        myReviews.setOnClickListener {
            val view = Intent(this,MyReviews::class.java)
            startActivity(view)
        }


        backButton.setOnClickListener{
            val intent = Intent(this,Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        addButton.setOnClickListener {
            val hiddenButtonsLayout = findViewById<LinearLayout>(R.id.buttonContainer)
            if (hiddenButtonsLayout.visibility == View.GONE){
                hiddenButtonsLayout.visibility = View.VISIBLE
            }
            else{
                hiddenButtonsLayout.visibility = View.GONE
            }
        }

        logout.setOnClickListener {
            MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
                .setTitle(R.string.logout_question)
                .setNeutralButton(R.string.no, {dialog, i -> })
                .setPositiveButton(R.string.si, {dialog, i -> })
                .setCancelable(true)
                .show()
        }

        delete.setOnClickListener {
            MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
                .setTitle(R.string.delete_question)
                .setMessage(R.string.delete_details)
                .setNeutralButton(R.string.no, {dialog, i -> })
                .setPositiveButton(R.string.si, {dialog, i -> })
                .setCancelable(true)
                .show()
        }




    }

    override fun onStart() {
        super.onStart()
    }
}