package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;


public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);

    public static void copyNotEmptyProperties(Object dest, Object orig)
            throws IllegalAccessException, InvocationTargetException {
        BeanUtilsBean.getInstance().copyProperties(dest, orig);
    }


    public static void copyNotEmptyPropertiesQuietly(Object dest, Object orig) {
        try {
            BeanUtilsBean.getInstance().copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("fail to copyNotEmptyPropertiesQuietly. error : {}", e.getMessage(), e);
        }
    }
}
