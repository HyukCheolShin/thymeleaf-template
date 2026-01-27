package kr.co.aia.dmd.common.map;

import org.springframework.jdbc.support.JdbcUtils;
import java.util.HashMap;
import java.util.Map;

public class CamelCaseMap extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    @Override
    public Object put(String key, Object value) {
        return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        for (Map.Entry<? extends String, ? extends Object> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
}
