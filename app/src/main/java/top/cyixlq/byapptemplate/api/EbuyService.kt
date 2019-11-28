package top.cyixlq.byapptemplate.api

import io.reactivex.Flowable
import io.reactivex.Observable
import top.cyixlq.byapptemplate.bean.AddressItem
import top.cyixlq.byapptemplate.bean.HotKey
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.network.annotion.Param
import top.cyixlq.network.annotion.Params
import top.cyixlq.network.annotion.Type

interface EbuyService {

    @Type("BY_Config_version")
    fun getVersionData(@Params appid: HashMap<String, String>): Flowable<VersionData>

    @Type("BY_Address_all")
    fun getAddressList(@Param("sid") sid: String): Flowable<List<AddressItem>>

    @Type("BY_Store_hotKeys")
    fun getKotKeys(@Param("size") size: Int): Observable<List<HotKey>>
}