package top.cyixlq.byapptemplate.bean

import com.google.gson.annotations.SerializedName

data class VersionData(
    val desc: String, // first fly,fresh sky
    val down: String, // http://
    val exchange_rate: ExchangeRate,
    val icon: Int, // 0
    @SerializedName("lang_support")
    val langSupport: LangSupport,
    @SerializedName("lib_area")
    val libArea: Int, // 1565775640
    @SerializedName("lib_cate")
    val libCate: Int, // 1565773707
    val name: String, // ebuy_and_1.0.0
    val version: String // 1.0.0
)

data class ExchangeRate(
    val cambo: String, // 581.1601
    val en: String, // 1
    @SerializedName("zh-cn")
    val zhCn: String // 6.9117
)

data class LangSupport(
    val cambo: String, // ភាសាខ្មែរ
    val en: String, // English
    @SerializedName("zh-cn")
    val zhCn: String // 简体中文
)