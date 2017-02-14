package com.github.mobile.accounts;

import com.github.mobile.MapFactory;
import com.google.inject.Key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by matthew on 2/13/17.
 */

public class ScopeMapFactory extends MapFactory {

    private Map<Key<?>, Object> scopeMap;

    @Override
    protected Map<Key<?>, Object> makeMap(){
        scopeMap = new ConcurrentHashMap<>();
        return scopeMap;
    }

}
