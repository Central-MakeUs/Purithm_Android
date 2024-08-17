package com.cmc.purithm.common.base

import androidx.navigation.NavController

/**
 * Feature 모듈안에 있는 기능들의 화면 전환을 위함
 * FragmentContainerView가 존재하는 Activity에 해당 interface를 구현
 * App 모듈의 MainActivity 참조
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
interface NavigationAction {
    fun navigateLogin()
    fun navigateOnBoarding()
    fun navigateHome()
    fun navigateTermOfService()
    fun navigateFilterItem(filterId: Long, popUpTo : Boolean)
    fun navigateReviewWrite(navigateType : Int, filterName: String, filterId : Long, thumbnail : String)
    fun navigateArtistFilter(artistId : Long)
    fun navigateFilterValue(filterId : Long)
    fun popBackStackAfterWriteReview(type : Int)
    fun navigateMyReviewHistory(filterId : Long, reviewId : Long, thumbnail : String, filterName : String)
}