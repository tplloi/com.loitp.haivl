package com.loitp.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.albumonly.GalleryCorePhotosOnlyFrm
import com.core.utilities.LScreenUtil
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.google.android.material.tabs.TabLayout
import com.loitp.R
import com.loitp.model.Flickr
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer
import kotlinx.android.synthetic.main.activity_menu.*
import kotlin.collections.ArrayList

@LogTag("loitppMenuActivity")
@IsFullScreen(false)
class MenuActivity : BaseFontActivity() {

    companion object {
        const val KEY_LAST_PAGE = "KEY_LAST_PAGE"
    }

    private val listFlickr = ArrayList<Flickr>()
    private var currentPage = 0

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

        //setup data
        listFlickr.add(Flickr("Độc đáo thú vị", Constants.FLICKR_ID_VN_DOCDAOTHUVI))
        listFlickr.add(Flickr("Cosplay", Constants.FLICKR_ID_COSPLAY))
        listFlickr.add(Flickr("Hại não", Constants.FLICKR_ID_HAINAO))
        listFlickr.add(Flickr("Bạn có biết", Constants.FLICKR_ID_VN_BANCOBIET))
        listFlickr.add(Flickr("Cung hoàng đạo", Constants.FLICKR_ID_VN_CUNGHOANGDAOFUNTFACT))
        listFlickr.add(Flickr("Hehehoro", Constants.FLICKR_ID_VN_CUNGHOANGDAOHEHEHORO))
        listFlickr.add(Flickr("Devvui", Constants.FLICKR_ID_VN_DEVVUI))
        listFlickr.add(Flickr("Funny manga", Constants.FLICKR_ID_VN_FUNNYMANGA))
        listFlickr.add(Flickr("Màn hình", Constants.FLICKR_ID_VN_FUNNYMANHINH))
        listFlickr.add(Flickr("Funny thể thao", Constants.FLICKR_ID_VN_FUNNYTHETHAO))
        listFlickr.add(Flickr("Hài hước", Constants.FLICKR_ID_VN_HAIHUOC))
        listFlickr.add(Flickr("Status vui", Constants.FLICKR_ID_VN_STTVUI))
        listFlickr.add(Flickr("Status đểu chất", Constants.FLICKR_ID_VN_STTDEUCHAT))
        listFlickr.add(Flickr("Troll", Constants.FLICKR_ID_VN_TROLL))
        listFlickr.add(Flickr("Truyện bựa", Constants.FLICKR_ID_VN_TRUYENBUA))
        listFlickr.add(Flickr("Truyện ngắn", Constants.FLICKR_ID_VN_TRUYENNGAN))
        listFlickr.add(Flickr("Tuổi thơ dữ dội", Constants.FLICKR_ID_VN_TUOITHODUDOI))
        listFlickr.add(Flickr("Ảnh chế", Constants.FLICKR_ID_VN_ANHCHESACHGIAOKHOA))
        listFlickr.add(Flickr("Ảnh theo tên", Constants.FLICKR_ID_VN_ANHTHEOTEN))

        listFlickr.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it.title))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                logD("onTabSelected " + tabLayout.selectedTabPosition)
                showFragment()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        currentPage = LSharedPrefsUtil.instance.getInt(KEY_LAST_PAGE, 0)
        tabLayout.postDelayed({
            tabLayout.getTabAt(currentPage)?.select()
        }, 100)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            LSharedPrefsUtil.instance.putInt(KEY_LAST_PAGE, tabLayout.selectedTabPosition)
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showLongInformation(msg = getString(R.string.press_again_to_exit), isTopAnchor = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    private fun showFragment() {
        val flickr = listFlickr[tabLayout.selectedTabPosition]

        val frm = GalleryCorePhotosOnlyFrm()
        val bundle = Bundle()
        bundle.putString(Constants.SK_PHOTOSET_ID, flickr.flickrId)
        frm.arguments = bundle
        LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = frm,
                isAddToBackStack = false
        )
    }

}
