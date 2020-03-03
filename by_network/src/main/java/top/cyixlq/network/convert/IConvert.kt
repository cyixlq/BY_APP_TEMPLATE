package top.cyixlq.network.convert

import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import java.lang.reflect.Type

interface IConvert {
    fun encodeData(data: Map<String, Any>, type: String, apiVersion: String, attach: HashMap<String, Any>?): HashMap<String, Any>
    fun decodeData(responseBody: ResponseBody, clazz: Class<*>?, typeToken: TypeToken<*>?, type: Type?): Any
}