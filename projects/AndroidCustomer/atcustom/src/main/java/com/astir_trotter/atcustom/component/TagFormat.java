/**
 * @author - Nicklass Fransson
 * @contact - nicklass.fransson@gmail.com
 * @date - 2/7/17
 */
package com.astir_trotter.atcustom.component;

import java.util.LinkedHashMap;
import java.util.Map;

public class TagFormat {
    private static final String TAG = TagFormat.class.getSimpleName();

    public static TagFormat from(String format) {
        return new TagFormat(format);
    }

    private final String format;
    private final Map<String, Object> tags = new LinkedHashMap<String, Object>();

    public TagFormat(String format) {
        this.format = format;
    }

    public TagFormat with(String key, Object value) {
        tags.put("\\{" + key + "\\}", value);
        return this;
    }

    public String format() {
        String formatted = format;
        for (Map.Entry<String, Object> tag : tags.entrySet()) {
            // bottleneck, creating temporary String objects!
            formatted = formatted.replaceAll(tag.getKey(), tag.getValue().toString());
        }
        return formatted;
    }
}
