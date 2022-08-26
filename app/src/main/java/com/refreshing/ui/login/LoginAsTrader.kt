package com.refreshing.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.messaging.FirebaseMessaging
import com.refreshing.MainActivity
import com.refreshing.databinding.ActivityLoginBinding
import com.refreshing.ui.base.BaseActivity
import com.refreshing.ui.orderdetails.OrderDetails

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAsTrader : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginAsTraderViewModel by viewModels()
    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pd = ProgressDialog(this)
        pd.setMessage("loading")
        pd.setCancelable(false)

        var token = ""
        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if (result != null) {
                token = result
                // DO your thing with your firebase token
            }
        }


        binding.button.setOnClickListener {


            if (!binding.passwordText.text.toString().isNullOrEmpty()) {
                if (!pd.isShowing) {
                    pd.show()
                }

                val map = HashMap<String, String>()
                map["email"] = binding.phoneTextTextPersonName.text.toString()
                map["password"] = binding.passwordText.text.toString()
                map["device_token"] = token
                viewModel.loginTrader(
                    map
                )

            } else {
                Toast.makeText(this, "تحقق من كلمه السر ", Toast.LENGTH_LONG).show()

            }


        }

        viewModel.sucess.observe(this, Observer {
            if (pd.isShowing) {
                pd.dismiss()
            }
            if (it.equals("done")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {


            }

        })

        if(viewModel.islogined){
            if(intent.hasExtra("id")){
                val id =intent.extras?.getString("id")
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("orderId", id.toString())
                startActivity(intent)
                finish()

            }else{
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()
            }
        }

    }
}