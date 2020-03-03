package top.cyixlq.network.bean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import top.cyixlq.network.RetrofitClient;
import top.cyixlq.network.annotion.ApiVersion;
import top.cyixlq.network.annotion.Arg;
import top.cyixlq.network.annotion.Param;
import top.cyixlq.network.annotion.Params;
import top.cyixlq.network.annotion.Type;
import top.cyixlq.network.annotion.Url;

import static top.cyixlq.network.config.NetWorkConfigKt.API_VERSION;

public class ServiceMethod {

    private final ParameterHandler[] parameterHandlers;
    private final Method method;
    private final RetrofitClient client;

    private ServiceMethod(Builder builder) {
        parameterHandlers = builder.parameterHandlers;
        method = builder.method;
        client = builder.client;
    }

    public Object invoke(Object[] args) {
        final int argumentsCount = args == null ? 0 : args.length;
        if (argumentsCount != parameterHandlers.length) {
            throw new IllegalArgumentException(method.getName() + "方法参数个数("
                    + argumentsCount + ")与获取到的被注解参数个数(" + parameterHandlers.length + ")不一致");
        }
        for (int i = 0; i < parameterHandlers.length; i++) {
            parameterHandlers[i].apply(client, args[i]);
        }
        final Class clazz = method.getReturnType();
        final java.lang.reflect.Type responseType = getParameterUpperBound((ParameterizedType) method.getGenericReturnType());
        if (clazz == Observable.class) {
            return client.executeObservable(null, null, responseType);
        }
        if (clazz == Flowable.class) {
            return client.executeFlowable(null, null, responseType);
        }
        throw new IllegalArgumentException(method.getName() + "方法的返回类型必须是Observable<T>或者Flowable<T>");
    }

    // 获取泛型中的类型对象，摘自Retrofit
    private java.lang.reflect.Type getParameterUpperBound(ParameterizedType type) {
        java.lang.reflect.Type[] types = type.getActualTypeArguments();
        java.lang.reflect.Type paramType = types[0];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    public static class Builder {

        private final RetrofitClient client;
        private final Method method;
        private final Annotation[][] parameterAnnotationsArray;
        private ParameterHandler[] parameterHandlers;

        public Builder(RetrofitClient client, Method method) {
            this.client = client;
            this.method = method;
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        @NotNull
        public ServiceMethod build() {
            // 处理方法的注解（此框架作用于方法上的注解只有@Type和@ApiVersion）
            parseMethodAnnotation(method);
            // Service方法中参数的个数
            final int parameterCount = parameterAnnotationsArray.length;
            parameterHandlers = new ParameterHandler[parameterCount];
            for(int p = 0; p < parameterCount; p++) {
                // 获取第p个参数上面的所有注解，所以是一个数组
                final Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
                if (parameterAnnotations == null || parameterAnnotations.length == 0) {
                    throw new IllegalArgumentException(method.getName() + "：方法所有形参必须加上@Param、@Params或@Arg注解");
                }
                parameterHandlers[p] = parseParameter(p, parameterAnnotations);
            }
            return new ServiceMethod(this);
        }

        @NotNull
        private ParameterHandler parseParameter(int p, Annotation[] parameterAnnotations) {
            ParameterHandler result = null;
            for (Annotation parameterAnnotation : parameterAnnotations) {
                ParameterHandler annotationAction = parseParameterAnnotation(parameterAnnotation);
                // 如果这个注解不支持那就下一个
                if (annotationAction == null) continue;
                // 找到了一个支持的注解
                result = annotationAction;
            }
            // 全部注解遍历完，还是没有一个支持的注解
            if (result == null) {
                throw new IllegalArgumentException(method.getName() + "第" + (p + 1) + "个方法形参必须加上@Param、@Params或@Arg注解");
            }
            return result;
        }

        // 解析参数注解 @Param或者@Params
        @Nullable
        private ParameterHandler parseParameterAnnotation(Annotation parameterAnnotation) {
            if (parameterAnnotation instanceof Param) {
                Param param = (Param) parameterAnnotation;
                return new ParameterHandler.Param(param.value());
            } else if (parameterAnnotation instanceof Params) {
                return new ParameterHandler.Params();
            } else if (parameterAnnotation instanceof Arg) {
                Arg arg = (Arg) parameterAnnotation;
                return new ParameterHandler.Arg(arg.value());
            }
            return null;
        }

        private void parseMethodAnnotation(Method method) {
            // 处理方法体上的Type注解
            final Type typeAnnotation = method.getAnnotation(Type.class);
            if (typeAnnotation == null) throw new IllegalArgumentException(method.getName() +"方法必须要有一个@Type注解");
            client.setType(typeAnnotation.value());

            // 处理方法体上的ApiVersion注解
            final ApiVersion apiVersionAnnotation = method.getAnnotation(ApiVersion.class);
            if (apiVersionAnnotation != null && !apiVersionAnnotation.value().isEmpty()) {
                client.setApiVersion(apiVersionAnnotation.value());
            } else {
                client.setApiVersion(API_VERSION);
            }

            // 处理方法体上的Url注解
            final Url urlAnnotation = method.getAnnotation(Url.class);
            if (urlAnnotation != null && !urlAnnotation.value().isEmpty()) {
                client.setUrl(urlAnnotation.value());
            }
        }

    }
}
