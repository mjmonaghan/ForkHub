package com.github.mobile.core.commit;

import com.github.mobile.MapFactory;
import com.google.inject.Key;

import java.util.Map;
import java.util.HashMap;
/**
 * Created by matthew on 2/13/17.
 */

public class CommitsMapFactory extends MapFactory{

    private Map<Key<?>, Object> repoCommitsMap;

    @Override
    protected Map<Key<?>, Object> makeMap(){
        repoCommitsMap = new HashMap<Key<?>, Object>();
        return repoCommitsMap;
    }
}
