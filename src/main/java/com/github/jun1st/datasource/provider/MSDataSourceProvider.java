package com.github.jun1st.datasource.provider;

import javax.sql.DataSource;
import java.util.Map;

public interface MSDataSourceProvider {

    DataSource loadMaster();

    Map<String, DataSource> loadSlaves();

}
