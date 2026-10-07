package com.egabi.cjexec.view.beans.security;

import java.util.HashMap;
import java.util.Map;

/** Added by M.Salah **/
public class UserPrevMap extends HashMap {
    public UserPrevMap(Map map) {
        super(map);
    }

    public UserPrevMap() {
        super();
    }

    public UserPrevMap(int i) {
        super(i);
    }

    public UserPrevMap(int i, float f) {
        super(i, f);
    }


    public Boolean get(Object key) {
        if (key != null) {
            if (super.get(key.toString()) == null)
                return false;
            else
                return true;
        }
        return false;
    }

    public Object put(String key) {
        return super.put(key, key);
    }

    public static void main(String[] args) {
        Map m = new HashMap();
        m.put(1, 1);
        System.err.println(m.get(1));
        System.err.println(m.get(12));
    }

}
