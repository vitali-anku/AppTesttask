package com.testtask.apptesttask.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.testtask.apptesttask.R
import com.testtask.apptesttask.presentation.main.MainPresenter
import com.testtask.apptesttask.ui.about_me.AboutMeFragment
import com.testtask.apptesttask.ui.characters.CharactersFragment
import com.testtask.apptesttask.ui.global.BaseFragment
import com.testtask.apptesttask.ui.likecharacters.LikeCharactersFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), MvpView{

    override var layoutRes: Int = R.layout.fragment_main

    private var containRes: Int = R.id.mainContainer

    private var currentTabTag: String = CHARACTERS

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                    .apply {
                        add(containRes, createFragmentByTag(ABOUT), ABOUT)
                        add(containRes, createFragmentByTag(LIKECHARACTERS), LIKECHARACTERS)
                        add(containRes, createFragmentByTag(CHARACTERS), CHARACTERS)
                    }.commitNow()
        }
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(savedInstanceState==null){

            childFragmentManager.beginTransaction().apply {

                findFragmentByTag(LIKECHARACTERS)?.let {
                    hide(it)
                    it.userVisibleHint = false
                }

                findFragmentByTag(ABOUT)?.let {
                    hide(it)
                    it.userVisibleHint = false
                }

            }.commit()
        }

        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.tab_chatacters -> onBottomMenuItemClick(CHARACTERS)
                    R.id.tab_like_chatacters -> onBottomMenuItemClick(LIKECHARACTERS)
                    R.id.tab_about_me -> onBottomMenuItemClick(ABOUT)
                }
                true
            }

    private fun onBottomMenuItemClick(tag: String){
        if(tag != currentTabTag){
            childFragmentManager.beginTransaction().apply {
                findFragmentByTag(currentTabTag)?.let {
                    hide(it)
                    it.userVisibleHint = false
                }

                findFragmentByTag(tag)?.let {
                    show(it)
                    it.userVisibleHint = true
                }
            }.commit()

            currentTabTag = tag
        }
    }

    private fun findFragmentByTag(tag: String) = when (tag) {
        CHARACTERS -> childFragmentManager.findFragmentByTag(CHARACTERS)
        LIKECHARACTERS -> childFragmentManager.findFragmentByTag(LIKECHARACTERS)
        ABOUT -> childFragmentManager.findFragmentByTag(ABOUT)

        else -> throw UnsupportedOperationException("Oyoyoyoi")
    }

    private fun createFragmentByTag(tag: String): Fragment = when (tag) {
        CHARACTERS -> CharactersFragment()
        LIKECHARACTERS -> LikeCharactersFragment()
        ABOUT -> AboutMeFragment()
        else -> throw UnsupportedOperationException("Oyoyoyoi")
    }

    companion object {
        private const val CHARACTERS = "characters"
        private const val LIKECHARACTERS = "like_characters"
        private const val ABOUT = "about_me"
    }
}