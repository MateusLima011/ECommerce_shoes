package com.example.ecommerce_shoes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_shoes.data.NavMenuItemsDataBase
import com.example.ecommerce_shoes.databinding.ActivityMainBinding
import com.example.ecommerce_shoes.domain.NavMenuItem
import com.example.ecommerce_shoes.domain.User
import com.example.ecommerce_shoes.ui.AboutFragment
import com.example.ecommerce_shoes.ui.NavMenuItemsAdapter
import com.example.ecommerce_shoes.util.NavMenuItemDetailsLookup
import com.example.ecommerce_shoes.util.NavMenuItemKeyProvider
import com.example.ecommerce_shoes.util.NavMenuItemPredicate
import com.makeramen.roundedimageview.RoundedImageView

class MainActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "frag-tag"
    }

    val user = User(
        "Mateus Vinicius",
        R.drawable.user,
        true
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    lateinit var navMenuItems: List<NavMenuItem>
    lateinit var selectNavMenuItems: SelectionTracker<Long>
    lateinit var navMenuItemsLogged: List<NavMenuItem>
    lateinit var selectNavMenuItemsLogged: SelectionTracker<Long>
    lateinit var navMenu: NavMenuItemsDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.appBarMain.appBar.toolbar)

        with(binding.appBarMain.appBar.toolbar) {
            setNavigationIcon(R.drawable.round_menu_24)
            setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        initNavMenu(savedInstanceState)
        initFragment()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        selectNavMenuItems.onSaveInstanceState(outState)
        selectNavMenuItemsLogged.onSaveInstanceState(outState)
    }

    private fun initNavMenu(savedInstanceState: Bundle?) {
        navMenu = NavMenuItemsDataBase(this)
        navMenuItems = navMenu.items
        navMenuItemsLogged = navMenu.itemsLogged

        showHideNavMenuViews()

        initNavMenuItemsLogged()
        initNavMenuItems()

        if (savedInstanceState != null) {
            selectNavMenuItems.onRestoreInstanceState(savedInstanceState)
            selectNavMenuItemsLogged.onRestoreInstanceState(savedInstanceState)
        } else {
            selectNavMenuItems.select(R.id.item_all_shoes.toLong())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "trocando layout", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun replaceHeaderView(isLogged: Boolean) = with(binding.navMenu) {
        if (isLogged) {
            navHeaderUserNotLogged.root.visibility = GONE

            navHeaderUserLogged.root.visibility = VISIBLE
            rvMenuItemsLogged.visibility = VISIBLE

            with(navHeaderUserLogged) {
                ivUser.setImageResource(user.image)
                tvUser.text = user.name
            }
        } else {
            navHeaderUserNotLogged.root.visibility = VISIBLE

            navHeaderUserLogged.root.visibility = GONE
            rvMenuItemsLogged.visibility = GONE
        }
    }


    private fun showHideNavMenuViews() {
        val view = replaceHeaderView(isLogged = user.status)
    }

    private fun initNavMenuItemsLogged() {
        binding.navMenu.rvMenuItemsLogged.setHasFixedSize(true)
        binding.navMenu.rvMenuItemsLogged.layoutManager = LinearLayoutManager(this)
        binding.navMenu.rvMenuItemsLogged.adapter =
            NavMenuItemsAdapter(navMenuItemsLogged)

        initNavMenuItemsLoggedSelection()
    }

    private fun initNavMenuItemsLoggedSelection() {

        selectNavMenuItemsLogged = SelectionTracker.Builder<Long>(
            "id-selected-item-logged",
            binding.navMenu.rvMenuItemsLogged,
            NavMenuItemKeyProvider(navMenuItemsLogged),
            NavMenuItemDetailsLookup(binding.navMenu.rvMenuItemsLogged),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(NavMenuItemPredicate(this))
            .build()

        selectNavMenuItemsLogged.addObserver(
            SelectObserverNavMenuItems {
                selectNavMenuItems.selection.filter {
                    selectNavMenuItems.deselect(it)
                }
            }
        )

        (binding.navMenu.rvMenuItemsLogged.adapter as NavMenuItemsAdapter).selectionTracker =
            selectNavMenuItemsLogged
    }

    private fun initNavMenuItems() {
        binding.navMenu.rvMenuItems.setHasFixedSize(false)
        binding.navMenu.rvMenuItems.layoutManager = LinearLayoutManager(this)
        binding.navMenu.rvMenuItems.adapter = NavMenuItemsAdapter(navMenuItems)

        initNavMenuItemsSelection()
    }

    private fun initNavMenuItemsSelection() {
        selectNavMenuItems = SelectionTracker.Builder<Long>(
            "id-selected-item",
            binding.navMenu.rvMenuItems,
            NavMenuItemKeyProvider(navMenuItems),
            NavMenuItemDetailsLookup(binding.navMenu.rvMenuItems),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(NavMenuItemPredicate(this))
            .build()

        selectNavMenuItems.addObserver(
            SelectObserverNavMenuItems {
                selectNavMenuItemsLogged.selection.filter {
                    selectNavMenuItems.deselect(it)
                }
            }
        )

        selectNavMenuItemsLogged.addObserver(
            SelectObserverNavMenuItems {
                selectNavMenuItems.selection.filter {
                    selectNavMenuItemsLogged.deselect(it)
                }
            }
        )

        (binding.navMenu.rvMenuItems.adapter as NavMenuItemsAdapter).selectionTracker =
            selectNavMenuItems
    }

    inner class SelectObserverNavMenuItems(val callbackRemoveSelection: () -> Unit) :
        SelectionTracker.SelectionObserver<Long>() {

        override fun onItemStateChanged(
            key: Long,
            selected: Boolean
        ) {
            val fragment = getFragment(key)
            replaceFragment(fragment)

            if (selected) {
                return
            }

            callbackRemoveSelection()

        }

    }

    private fun fillUserHeaderNavMenu() {
        val userImage = findViewById<RoundedImageView>(R.id.iv_user)
        val userName = findViewById<TextView>(R.id.tv_user)
        userImage.setImageResource(user.image)
        userName.text = user.name
    }

    private fun initFragment() {
        val supFragment = supportFragmentManager
        var fragment = supFragment.findFragmentByTag(FRAGMENT_TAG)

        if (fragment == null) {
            fragment = getFragment(R.id.item_about.toLong())
        }
        replaceFragment(fragment)
    }

    private fun getFragment(fragmentId: Long): Fragment = when (fragmentId) {
            R.id.item_about.toLong() -> AboutFragment()
            else -> AboutFragment()
        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fl_fragment_container,
                fragment,
                FRAGMENT_TAG
            )
            .commit()
    }

    fun updateToolbarTitleInFragment( titleStringId: Int ){
       binding.appBarMain.appBar.toolbar.title = getString( titleStringId )
    }
}


