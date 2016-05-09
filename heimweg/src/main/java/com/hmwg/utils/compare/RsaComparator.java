package com.hmwg.utils.compare;

import java.util.Comparator;

/**
 * Created by eric_qiantw on 16/4/1.
 */
public class RsaComparator implements Comparator<Object> {
    @Override
    public int compare(Object lhs, Object rhs) {
        return lhs.toString().toLowerCase().compareTo(rhs.toString().toLowerCase());
    }
}
