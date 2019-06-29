package top.cyixlq.network.interceptor;

import okhttp3.logging.HttpLoggingInterceptor;
import top.cyixlq.network.utils.CLog;

public class InterceptorLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
            CLog.json(message);
        } else {
            CLog.i(message);
        }
    }
}
