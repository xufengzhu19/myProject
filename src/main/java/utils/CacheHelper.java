package utils;

public class CacheHelper {
    private CacheHelper() {
    }

    public static Object getByCache(String key, CacheHandler handler) throws Exception {
        return getByCache(key, handler, 1800);
    }

    public static Object getByCache(String key, CacheHandler handler, int seconds) throws Exception {
//        Object obj = Init.getSuyunRedisOperator().get(key);
//        if (obj != null) {
//            return obj;
//        }
//        obj = handler.excute();
//        if (obj != null) {
//            Init.getSuyunRedisOperator().set(key, String.valueOf(obj));
//            Init.getSuyunRedisOperator().expire(key, seconds);
//        }
//        return obj;
        return null;
    }
}
