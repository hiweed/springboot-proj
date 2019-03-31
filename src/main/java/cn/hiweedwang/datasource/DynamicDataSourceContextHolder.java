package cn.hiweedwang.datasource;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();
    private static final List<String> dataSourceIds = new ArrayList();

    public static void setDataSourceId(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceId() {
        return (String) contextHolder.get();
    }

    public static void clearDataSourceId() {
        contextHolder.remove();
    }

    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }

    public static List<String> getDataSourceIds() {
        return dataSourceIds;
    }
}
