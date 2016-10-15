package pl.pwr.common.service.filter;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Evelan on 15/10/2016.
 */
public class WulgarMessageFilter implements MessageFilter {

    @Override
    public String doFilter(String text) {
        throw new NotImplementedException();
    }
}
