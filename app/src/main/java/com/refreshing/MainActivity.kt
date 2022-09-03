package com.refreshing






import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.refreshing.databinding.ActivityMainBinding
import com.refreshing.fcm.channelManager.NotificationChannelManager
import com.refreshing.ui.base.BaseActivity
import com.refreshing.ui.login.LoginAsTrader
import com.refreshing.ui.login.LoginAsTraderViewModel
import com.refreshing.ui.orderdetails.OrderDetails
import dagger.hilt.android.AndroidEntryPoint
import java.io.OutputStream


@AndroidEntryPoint
class MainActivity : BaseActivity(){



    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginAsTraderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home , R.id.nav_gallery
            ), drawerLayout
        )
        navView.setupWithNavController(navController)

        binding.appBarMain.SideMenue.setOnClickListener {
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

        }

        FirebaseMessaging.getInstance().subscribeToTopic("muneshOrder")


        FirebaseMessaging.getInstance().subscribeToTopic("muneshOrderTest")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannelManager.initChannel(this)
        }



        if(intent.hasExtra("id")){
            val id =intent.extras?.getString("id")
            val intent = Intent(this, OrderDetails::class.java)

            intent.putExtra("orderId", id.toString())
            startActivity(intent)

        }


        binding.ar.setOnClickListener {
            val preferences= PreferenceManager.getDefaultSharedPreferences(this)
            preferences.edit().putString("lang","ar").commit()
            val intent = Intent(this, LoginAsTrader::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            ActivityCompat.finishAffinity(this)
        }
        binding.en.setOnClickListener {
            val preferences= PreferenceManager.getDefaultSharedPreferences(this)
            preferences.edit().putString("lang","en").commit()
            val intent = Intent(this, LoginAsTrader::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            ActivityCompat.finishAffinity(this)
        }
        binding.LogOut.setOnClickListener {
                            viewModel.logOut()

                val intent = Intent(this, LoginAsTrader::class.java)

                startActivity(intent)
                finish()

                Toast.makeText(this, "logOut", Toast.LENGTH_SHORT).show()
        }



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

