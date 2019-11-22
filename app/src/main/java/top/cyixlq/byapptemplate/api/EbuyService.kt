package top.cyixlq.byapptemplate.api

import io.reactivex.Flowable
import top.cyixlq.byapptemplate.bean.VersionData
import top.cyixlq.network.annotion.Param
import top.cyixlq.network.annotion.Type

interface EbuyService {

    @Type("BY_Config_version")
    fun getVersionData(@Param appid: String): Flowable<VersionData>
}