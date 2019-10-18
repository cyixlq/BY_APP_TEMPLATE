package top.cyixlq.byapptemplate.main

import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.VersionData

data class MainViewState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
    val addressList: List<AddressItem>? = null,
    val versionData: VersionData? = null,
    val musicUrl: String? = null
) {
    companion object {
        fun create(
            isLoading: Boolean = false,
            throwable: Throwable? = null,
            addressList: List<AddressItem>? = null,
            versionData: VersionData? = null,
            musicUrl: String? = null
        ): MainViewState =
            MainViewState(isLoading, throwable, addressList, versionData, musicUrl)
    }
}