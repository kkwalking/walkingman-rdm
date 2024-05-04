package com.kelton.walkingmanrdm.ui.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author zhouzekun
 * @Date 2024/5/2 21:09
 */
public class GlobalObjectPool {

    public static final String PRIMARY_STAGE = "primary_stage";

    private static final Map<String, Object> pool = new HashMap<>();

    public static void register(String name, Object obj) {
        if (pool.containsKey(name)) {
            throw new RuntimeException(name + " had been registered!");
        }
        pool.put(name, obj);
    }

    public static Object getBy(String name) {
        if (!pool.containsKey(name)) {
            throw new RuntimeException(name + " not found in pool!");
        }
        return pool.get(name);
    }
}
