package com.example.ecommerce_shoes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.GravityCompat
import com.example.ecommerce_shoes.databinding.ActivityMainBinding
import com.example.ecommerce_shoes.databinding.NavHeaderUserLoggedBinding
import com.example.ecommerce_shoes.databinding.NavHeaderUserNotLoggedBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appBarMain.appBar.toolbar)

        with(binding.appBarMain.appBar.toolbar) {
            setNavigationIcon(R.drawable.round_menu_24)
            setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        setupNavDrawer()
    }

    private fun setupNavDrawer() = binding.navView.setNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.allShoes -> {}
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                replaceHeaderView(isLogged = true)
                Toast.makeText(this, "trocando layout", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun replaceHeaderView(isLogged: Boolean) = with(binding.navView) {
        removeHeaderView(getHeaderView(0))
        val view = if (isLogged) R.layout.nav_header_user_logged
        else R.layout.nav_header_user_not_logged
        inflateHeaderView(view)
    }
}
