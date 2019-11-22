package top.cyixlq.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.WildcardType;
import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import top.cyixlq.network.annotion.ApiVersion;
import top.cyixlq.network.annotion.Param;
import top.cyixlq.network.annotion.Type;

public class ByNet {

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (o, method, args) -> {
            final Type typeAnnotation = method.getAnnotation(Type.class);
            if (typeAnnotation == null) {
                throw new IllegalArgumentException("You must add @Type annotation in " + clazz.getSimpleName());
            }
            final String type = typeAnnotation.value();
            final Class<?> returnType = method.getReturnType();
            final HashMap<String, Object> params = new HashMap<>();
            for (Object arg : args) {
                Class<?> argClass = arg.getClass();
                final Param paramAnnotation = argClass.getAnnotation(Param.class);
                if (paramAnnotation != null) {
                    String paramName = paramAnnotation.value();
                    if (paramName.isEmpty()) {
                        paramName = argClass.getName();
                    }
                    params.put(paramName, arg);
                }
            }
            final RetrofitClient client = RetrofitClient.create();
            final ApiVersion apiVersionAnnotation = method.getAnnotation(ApiVersion.class);
            final java.lang.reflect.Type responseType = getParameterUpperBound((ParameterizedType) method.getGenericReturnType());
            if (apiVersionAnnotation != null)
                client.setApiVersion(apiVersionAnnotation.value());
            client.setType(type).setParams(params);
            if (returnType == Observable.class) {
                return client.executeObservable(null, null, responseType);
            }
            if (returnType == Flowable.class) {
                return client.executeFlowable(null, null, responseType);
            }
            throw new IllegalArgumentException(method.getName() + ": the returnType must be Observable or Flowable");
        });
    }

    private static java.lang.reflect.Type getParameterUpperBound(ParameterizedType type) {
        java.lang.reflect.Type[] types = type.getActualTypeArguments();
        java.lang.reflect.Type paramType = types[0];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }
}
