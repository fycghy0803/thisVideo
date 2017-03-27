package com.lxht.emos.data;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * Created by yulifan on 16/7/11.
 */
public class EmosDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return HandleDataSource.getDataSource();
    }
}

class HandleDataSource {
    public static final ThreadLocal<String> holder = new ThreadLocal<String>();
    public static void setDataSource(String datasource) {
        holder.set(datasource);
    }

    public static String getDataSource() {
        return holder.get();
    }
}