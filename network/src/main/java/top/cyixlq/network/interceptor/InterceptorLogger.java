package top.cyixlq.network.interceptor;

import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import top.cyixlq.network.utils.CLog;

public class InterceptorLogger implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(@NotNull String message) {
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
            CLog.json("NetWork", message);
        } else {
            CLog.i("NetWork", message);
        }
    }
}
