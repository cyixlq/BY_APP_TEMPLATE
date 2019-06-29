package top.cyixlq.network.convert

import top.cyixlq.network.NetWorkManager
import top.cyixlq.network.config.*
import top.cyixlq.network.utils.NetSecurityUtil

class SampleConvert: IConvert {

    // 将业务参数加密上传到服务器
    override fun encodeData(data: HashMap<String, Any>, type: String, apiVersion: String): String {
        val paramsString = NetSecurityUtil.dataEncrypt(data)  // 将用户传入的键值对转换成json，加密后变为String
        val params = HashMap<String, Any>() // 重新封装成后台需要的数据格式
        params["api_ver"] = apiVersion
        params["type"] = type
        val timeStamp = System.currentTimeMillis() / 100
        params["notify_id"] = timeStamp
        params["time"] = timeStamp
        params["alg"] = ALG
        params["data"] = paramsString
        params["app_version"] = NetWorkManager.getInstance().getVersionName()
        params["lang"] = getLanguage()
        params["app_type"] = APP_TYPE
        val sign = NetSecurityUtil.getMD5Sign(timeStamp.toString(), type, paramsString, CLIENT_SECRETE, timeStamp.toString())
        params["sign"] = sign
        return NetSecurityUtil.encodeData(NetSecurityUtil.desEncodeUserParam(params, CLIENT_SECRETE))
    }

    // 将服务端返回的String解密成json字符串返回
    override fun decodeData(result: String): String {
        return NetSecurityUtil.decodeData(result)
    }
}