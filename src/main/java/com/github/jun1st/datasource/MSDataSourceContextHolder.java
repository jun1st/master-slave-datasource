package com.github.jun1st.datasource;

/**
 * @author fengde
 */
public final class MSDataSourceContextHolder {

    private static final ThreadLocal<String> LOOKUP_KEY_HOLDER = new ThreadLocal<>();

    private MSDataSourceContextHolder() {
    }

    public static String getDataSourceLookupKey() {
        return LOOKUP_KEY_HOLDER.get();
    }

    public static void setDataSourceLookupKey(String dataSourceLookupKey) {
        LOOKUP_KEY_HOLDER.set(dataSourceLookupKey);
    }

    public static void clearDataSourceLookupKey() {
        LOOKUP_KEY_HOLDER.remove();
    }
}
