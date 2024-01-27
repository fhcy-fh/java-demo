package top.fhcy.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanUtils {

    public static <T> List<T> copyProperties(Collection<?> sources, Class<T> targetClassType) {
        List<T> oc = new ArrayList<>(sources == null ? 0 : sources.size());
        if (CollectionUtils.isNotEmpty(sources)) {
            for (Object source : sources) {
                oc.add(BeanUtils.copyProperties(source, targetClassType));
            }
        }
        return oc;
    }

    public static <T> T copyProperties(Object source, Class<T> targetClassType) {
        if (source == null) {
            return null;
        }
        T o = BeanUtils.instantiateClass(targetClassType);
        BeanUtils.copyProperties(source, o);
        return o;
    }

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    public static <T> T instantiateClass(Class<T> classType) {
        return org.springframework.beans.BeanUtils.instantiateClass(classType);
    }
}
