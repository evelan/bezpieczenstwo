package pl.pwr.common.service.filter;

/**
 * Created by Evelan on 15/10/2016.
 */
public interface MessageFilterObserver {
    void registerMessageFilter(MessageFilter messageFilter);
    void unregisterMessageFilter(MessageFilter messageFilter);
}