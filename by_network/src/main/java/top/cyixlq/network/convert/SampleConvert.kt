package top.cyixlq.network.convert

import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import top.cyixlq.core.net.exception.CustomNetException
import top.cyixlq.core.utils.CLog
import top.cyixlq.network.ByNetWorkManager
import top.cyixlq.network.bean.BaseResponse
import top.cyixlq.network.config.*
import top.cyixlq.network.utils.NetSecurityUtil
import top.cyixlq.network.utils.jsonToObject
import java.lang.reflect.Type

class SampleConvert: IConvert {

    // 将业务参数加密上传到服务器(其中attach为一些附加参数, 这一部分自己定义所需)，通过ByNetWokManager中setGlobalCustomArgs传入
    // data为业务参数，最终返回的map为包含业务参数的并且带有外层一些通用验证参数的键值对
    override fun encodeData(data: Map<String, Any>, type: String, apiVersion: String, attach: HashMap<String, Any>?): HashMap<String, Any> {
        val paramsString = NetSecurityUtil.dataEncrypt(data)  // 将用户传入的键值对转换成json，加密后变为String
        val params = HashMap<String, Any>() // 重新封装成后台需要的数据格式
        if (attach != null) params.putAll(attach) // 将外层附件验证参数加进来
        params["api_ver"] = apiVersion
        params["type"] = type
        val timeStamp = System.currentTimeMillis() / 100
        params["notify_id"] = timeStamp
        params["time"] = timeStamp
        params["alg"] = ALG
        params["data"] = paramsString
        params["app_version"] = ByNetWorkManager.getVersionName()
        params["lang"] = getLanguage()
        params["app_type"] = APP_TYPE
        val sign = NetSecurityUtil.getMD5Sign(timeStamp.toString(), type, paramsString, CLIENT_SECRETE, timeStamp.toString())
        params["sign"] = sign
        val map = HashMap<String, Any>()
        map["client_id"] = CLIENT_ID
        map["itboye"] = NetSecurityUtil.encodeData(NetSecurityUtil.desEncodeUserParam(params, CLIENT_SECRETE))
        return map
    }

    override fun decodeData(
        responseBody: ResponseBody,
        clazz: Class<*>?,
        typeToken: TypeToken<*>?,
        type: Type?
    ): Any {
        val baseResponse = responseBody.string().jsonToObject<BaseResponse>()
        if (baseResponse.code != RESULT_OK) {
            throw CustomNetException(baseResponse.code, baseResponse.msg)
        }
        val result = baseResponse.data as? String ?: throw CustomNetException(SERVICE_ERR, baseResponse.msg)
        val decodeString = NetSecurityUtil.decodeData(result)
        CLog.t(TAG_RESPONSE).json(decodeString)
        return when {
            clazz != null -> decodeString.jsonToObject(clazz)
            typeToken != null -> decodeString.jsonToObject(typeToken.type)
            type != null -> decodeString.jsonToObject(type)
            else -> throw IllegalArgumentException("clazz，typeToken，type三个参数中必须至少一个不是null")
        }
    }
}