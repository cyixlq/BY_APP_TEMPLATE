package top.cyixlq.network.convert

interface IConvert {
    fun encodeData(data: HashMap<String, Any>, type: String, apiVersion: String): String
    fun decodeData(result: String): String
}