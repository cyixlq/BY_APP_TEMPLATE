package top.cyixlq.byapptemplate.main

import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.BannerBean
import top.cyixlq.byapptemplate.bean.VersionData

data class MainViewState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
    val addressList: List<AddressItem>? = null,
    val versionData: VersionData? = null,
    val musicUrl: String? = null,
    val banners: List<BannerBean>? = null
)