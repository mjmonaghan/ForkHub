package com.github.mobile;

import com.google.inject.Key;

import java.util.Map;

/**
 * Created by matthew on 2/13/17.
 */

public abstract class MapFactory {

    private Map<Key<?>, Object> objectMap;

    public MapFactory(){
        Map<Key<?>, Object> objectMap = makeMap();
    }

    abstract protected Map<Key<?>, Object> makeMap();

    public Object get(Object o) {
        return objectMap.get(o);
    }

    public void put(Key k, Object o){
        objectMap.put(k,o);
    }

    public Map<Key<?>, Object> getMap(){
        return objectMap;
    }
}
