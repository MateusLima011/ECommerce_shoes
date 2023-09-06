package com.example.ecommerce_shoes

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
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
import com.example.ecommerce_shoes.domain.User.Companion.KEY
import com.example.ecommerce_shoes.ui.AboutFragment
import com.example.ecommerce_shoes.ui.config.AccountSettingsActivity
import com.example.ecommerce_shoes.ui.ContactFragment
import com.example.ecommerce_shoes.ui.LoginActivity
import com.example.ecommerce_shoes.ui.NavMenuItemsAdapter
import com.example.ecommerce_shoes.ui.PrivacyPolicyFragment
import com.example.ecommerce_shoes.ui.SignUpActivity
import com.example.ecommerce_shoes.util.NavMenuItemDetailsLookup
import com.example.ecommerce_shoes.util.NavMenuItemKeyProvider
import com.example.ecommerce_shoes.util.NavMenuItemPredicate
import com.makeramen.roundedimageview.RoundedImageView
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_TAG = "frag-tag"
        const val FRAGMENT_ID = "frag-id"
    }

    private val user = User(
        "Mateus Vinicius",
        R.drawable.user,
        true
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    private lateinit var navMenuItems: List<NavMenuItem>
    private lateinit var navMenuItemsLogged: List<NavMenuItem>
    lateinit var selectNavMenuItems: SelectionTracker<Long>
    lateinit var selectNavMenuItemsLogged: SelectionTracker<Long>
    private lateinit var navMenu: NavMenuItemsDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        initNavMenu(savedInstanceState)
        initFragment()

        binding.navMenu.navHeaderUserNotLogged.btLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.navMenu.navHeaderUserNotLogged.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

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
        navMenuItems = navMenu.items
        navMenuItemsLogged = navMenu.itemsLogged

        showHideNavMenuViews()
        initNavMenuItemsLogged(navMenuItemsLogged)
        initNavMenuItems(navMenuItems)


        if (savedInstanceState != null) {
            selectNavMenuItems.onRestoreInstanceState(savedInstanceState)
            selectNavMenuItemsLogged.onRestoreInstanceState(savedInstanceState)
        } else {
            val fragId = intent?.getIntExtra(FRAGMENT_ID, R.id.item_all_shoes)
            selectNavMenuItems.select(fragId!!.toLong())
        }

    }

    private fun showHideNavMenuViews() {
        replaceHeaderView(user.status)
        fillUserHeaderNavMenu()
    }

    private fun fillUserHeaderNavMenu() {
        val userImage = findViewById<RoundedImageView>(R.id.iv_user)
        val userName = findViewById<TextView>(R.id.tv_user)
        userImage.setImageResource(user.image)
        userName.text = user.name
    }

    private fun initNavMenuItems(navMenuItems: List<NavMenuItem>) {
        val rvMenuItems = binding.navMenu.rvMenuItems
        rvMenuItems.setHasFixedSize(false)
        rvMenuItems.layoutManager = LinearLayoutManager(this)
        rvMenuItems.adapter = NavMenuItemsAdapter(navMenuItems)

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
                    selectNavMenuItemsLogged.deselect(it)
                }
            }
        )

        (binding.navMenu.rvMenuItems.adapter as NavMenuItemsAdapter).selectionTracker =
            selectNavMenuItems
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


    private fun initFragment() {
        supportFragmentManager.apply {
            val fragment = findFragmentByTag(FRAGMENT_TAG)
            val transaction = beginTransaction()
            fragment?.let { transaction.add(R.id.fl_fragment_container, it) }
            transaction.commit()

            if (fragment == null) {
                val fragId = intent?.getIntExtra(FRAGMENT_ID, 0) ?: R.id.item_all_shoes
                getFragment(fragId)?.let { replaceFragment(it) }
            }
        }
    }

    private fun getFragment(fragmentId: Int): Fragment? = when (fragmentId) {
        R.id.item_about -> AboutFragment()
        R.id.item_contact -> ContactFragment()
        R.id.item_privacy_policy -> PrivacyPolicyFragment()
        else -> null
    }

    private fun startActivityByTag(activity: KClass<AccountSettingsActivity>) {
        Intent(this, activity.java).apply {
            putExtra(KEY, user)
        }.run(::startActivity)
    }

    private fun replaceHeaderView(isLogged: Boolean) {
        val navHeaderUserNotLogged = binding.navMenu.navHeaderUserNotLogged.root
        val navHeaderUserLogged = binding.navMenu.navHeaderUserLogged.root
        val rvMenuItemsLogged = binding.navMenu.rvMenuItemsLogged

        if (isLogged) {
            navHeaderUserNotLogged.visibility = GONE
            navHeaderUserLogged.visibility = VISIBLE
            rvMenuItemsLogged.visibility = VISIBLE

            binding.navMenu.navHeaderUserLogged.ivUser.setImageResource(user.image)
            binding.navMenu.navHeaderUserLogged.tvUser.text = user.name
        } else {
            navHeaderUserNotLogged.visibility = VISIBLE
            navHeaderUserLogged.visibility = GONE
            rvMenuItemsLogged.visibility = GONE
        }
    }

    fun updateToolbarTitleInFragment(titleStringId: Int) {
        binding.appBarMain.appBar.toolbar.title = getString(titleStringId)
    }

    inner class SelectObserverNavMenuItems(private val callbackRemoveSelection: () -> Unit) :
        SelectionTracker.SelectionObserver<Long>() {

        override fun onItemStateChanged(key: Long, selected: Boolean) {
            super.onItemStateChanged(key, selected)

            if (!selected) {
                return
            }
            if (isActivityCallInMenu(key)) {
                itemCallActivity(key, callbackRemoveSelection)
            } else {
                itemCallFragment(key, callbackRemoveSelection)
            }
        }
    }

    fun isActivityCallInMenu(key: Long) = when (key) {
        R.id.item_settings.toLong() -> true
        else -> false
    }

    private fun itemCallActivity(
        key: Long,
        callbackRemoveSelection: () -> Unit
    ) {

        callbackRemoveSelection()

        lateinit var intent: Intent

        when (key) {
            R.id.item_settings.toLong() -> {
                intent = Intent(
                    this,
                    AccountSettingsActivity::class.java
                )
                intent.putExtra(User.KEY, user)
            }
        }

        navMenu.saveIsActivityItemFired(
            this,
            true
        )

        startActivity(intent)
    }

    private fun itemCallFragment(key: Long, callbackRemoveSelection: () -> Unit) {

        callbackRemoveSelection()

        navMenu.saveLastSelectedItemFragmentID(this, key)

        if (!navMenu.wasActivityItemFired(this)) {
            val fragment = getFragment(key.toInt())
            if (fragment != null) replaceFragment(fragment)
            else when (key.toInt()) {
                R.id.item_settings -> startActivityByTag(AccountSettingsActivity::class)
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            navMenu.saveIsActivityItemFired(this, false)
        }
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


    override fun onResume() {
        super.onResume()
        if (navMenu.wasActivityItemFired(this)) {
            selectNavMenuItems.select(navMenu.getLastSelectedItemFragmentID(this))
        }
    }
}
