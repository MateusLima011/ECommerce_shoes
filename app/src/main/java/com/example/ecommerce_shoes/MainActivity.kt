package com.example.ecommerce_shoes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_shoes.data.NavMenuItemsDataBase
import com.example.ecommerce_shoes.databinding.ActivityMainBinding
import com.example.ecommerce_shoes.domain.NavMenuItem
import com.example.ecommerce_shoes.domain.User
import com.example.ecommerce_shoes.ui.AboutFragment
import com.example.ecommerce_shoes.ui.ContactFragment
import com.example.ecommerce_shoes.ui.NavMenuItemsAdapter
import com.example.ecommerce_shoes.ui.PrivacyPolicyFragment
import com.example.ecommerce_shoes.util.NavMenuItemDetailsLookup
import com.example.ecommerce_shoes.util.NavMenuItemKeyProvider
import com.example.ecommerce_shoes.util.NavMenuItemPredicate
import com.makeramen.roundedimageview.RoundedImageView

class MainActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "frag-tag"
    }

    private val user = User(
        "Mateus Vinicius",
        R.drawable.user,
        true
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    lateinit var selectNavMenuItems: SelectionTracker<Long>
    lateinit var selectNavMenuItemsLogged: SelectionTracker<Long>
    private lateinit var navMenu: NavMenuItemsDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        initNavMenu(savedInstanceState)
        initFragment()

    }

    private fun setupViews() {
        setSupportActionBar(binding.appBarMain.appBar.toolbar)

        with(binding.appBarMain.appBar.toolbar) {
            setNavigationIcon(R.drawable.round_menu_24)
            setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun initNavMenu(savedInstanceState: Bundle?) {
        navMenu = NavMenuItemsDataBase(this)
        val navMenuItems = navMenu.items
        val navMenuItemsLogged = navMenu.itemsLogged

        showHideNavMenuViews()
        initNavMenuItemsLogged(navMenuItemsLogged)
        initNavMenuItems(navMenuItems)

        if (savedInstanceState != null) {
            selectNavMenuItems.onRestoreInstanceState(savedInstanceState)
            selectNavMenuItemsLogged.onRestoreInstanceState(savedInstanceState)
        } else {
            selectNavMenuItems.select(R.id.item_all_shoes.toLong())
        }
    }

    private fun showHideNavMenuViews() {
        replaceHeaderView(user.status)
    }

    private fun replaceHeaderView(isLogged: Boolean) {
        val navHeaderUserNotLogged = binding.navMenu.navHeaderUserNotLogged.root
        val navHeaderUserLogged = binding.navMenu.navHeaderUserLogged.root
        val rvMenuItemsLogged = binding.navMenu.rvMenuItemsLogged

        if (isLogged) {
            navHeaderUserNotLogged.visibility = View.GONE
            navHeaderUserLogged.visibility = View.VISIBLE
            rvMenuItemsLogged.visibility = View.VISIBLE

            binding.navMenu.navHeaderUserLogged.ivUser.setImageResource(user.image)
            binding.navMenu.navHeaderUserLogged.tvUser.text = user.name
        } else {
            navHeaderUserNotLogged.visibility = View.VISIBLE
            navHeaderUserLogged.visibility = View.GONE
            rvMenuItemsLogged.visibility = View.GONE
        }
    }

    private fun initNavMenuItemsLogged(navMenuItemsLogged: List<NavMenuItem>) {
        val rvMenuItemsLogged = binding.navMenu.rvMenuItemsLogged
        rvMenuItemsLogged.setHasFixedSize(true)
        rvMenuItemsLogged.layoutManager = LinearLayoutManager(this)
        rvMenuItemsLogged.adapter = NavMenuItemsAdapter(navMenuItemsLogged)

        initNavMenuItemsLoggedSelection(rvMenuItemsLogged, navMenuItemsLogged)
    }

    private fun initNavMenuItemsLoggedSelection(
        rvMenuItemsLogged: RecyclerView,
        navMenuItemsLogged: List<NavMenuItem>
    ) {

        selectNavMenuItemsLogged = SelectionTracker.Builder<Long>(
            "id-selected-item-logged",
            rvMenuItemsLogged,
            NavMenuItemKeyProvider(navMenuItemsLogged),
            NavMenuItemDetailsLookup(rvMenuItemsLogged),
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
        (rvMenuItemsLogged.adapter as NavMenuItemsAdapter).selectionTracker =
            selectNavMenuItemsLogged
    }

    private fun initNavMenuItems(navMenuItems: List<NavMenuItem>) {
        val rvMenuItems = binding.navMenu.rvMenuItems
        rvMenuItems.setHasFixedSize(false)
        rvMenuItems.layoutManager = LinearLayoutManager(this)
        rvMenuItems.adapter = NavMenuItemsAdapter(navMenuItems)

        initNavMenuItemsSelection(rvMenuItems, navMenuItems)
    }

    private fun initNavMenuItemsSelection(
        rvMenuItems: RecyclerView,
        navMenuItems: List<NavMenuItem>
    ) {
        selectNavMenuItems = SelectionTracker.Builder<Long>(
            "id-selected-item",
            rvMenuItems,
            NavMenuItemKeyProvider(navMenuItems),
            NavMenuItemDetailsLookup(rvMenuItems),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(NavMenuItemPredicate(this))
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

        (rvMenuItems.adapter as NavMenuItemsAdapter).selectionTracker =
            selectNavMenuItems
    }

    private fun initFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment =
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) ?: getFragment(R.id.item_about)
        fragmentTransaction.commit()
    }

    private fun getFragment(fragmentId: Int): Fragment = when (fragmentId) {
        R.id.item_about -> AboutFragment()
        R.id.item_contact -> ContactFragment()
        R.id.item_privacy_policy -> PrivacyPolicyFragment()
        else -> AboutFragment()
    }

    fun updateToolbarTitleInFragment(titleStringId: Int) {
        binding.appBarMain.appBar.toolbar.title = getString(titleStringId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    inner class SelectObserverNavMenuItems(private val callbackRemoveSelection: () -> Unit) :
        SelectionTracker.SelectionObserver<Long>() {

        override fun onItemStateChanged(
            key: Long,
            selected: Boolean
        ) {
            val fragment = getFragment(key.toInt())
            replaceFragment(fragment)
            callbackRemoveSelection()

        }

    }

    private fun fillUserHeaderNavMenu() {
        val userImage = findViewById<RoundedImageView>(R.id.iv_user)
        val userName = findViewById<TextView>(R.id.tv_user)
        userImage.setImageResource(user.image)
        userName.text = user.name
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


}

