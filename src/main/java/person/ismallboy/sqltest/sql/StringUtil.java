package person.ismallboy.sqltest.sql;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ismallboy
 * @date 2017/11/19
 */
public class StringUtil {
    public static boolean isEmpty(String string) {
        if (string != null) {
            string = string.trim();
        }
        return StringUtils.isEmpty(string);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
