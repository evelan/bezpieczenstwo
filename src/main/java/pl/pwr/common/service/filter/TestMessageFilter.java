package pl.pwr.common.service.filter;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Evelan on 15/10/2016.
 */
public class TestMessageFilter implements MessageFilter {

    @Override
    public String doFilter(String text) {
        return StringUtils.replace(text, "test", "****");
    }
}
