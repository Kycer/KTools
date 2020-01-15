package site.kycer.project.ktool.cache;

import org.junit.jupiter.api.Test;
import site.kycer.project.ktool.cache.cache.Cache;
import site.kycer.project.ktool.cache.enums.ExpirationType;

/**
 * @author Kycer
 * @version 1.0.0
 * @date 2020-01-15
 */
class CacheBuilderTest {

    @Test
    void testFIFO() {
        Cache<String, Integer> cache = CacheBuilder.newBuilder().expiration(ExpirationType.FIFO).size(3).scanSeconds(1L).build();
        for (int i = 0; i < 20; i++) {
            cache.put("key" + i, i);
            System.out.println(cache.getAll());
        }
    }

    @Test
    void testLRU() {
        Cache<String, Integer> cache = CacheBuilder.newBuilder().expiration(ExpirationType.LRU).size(3).scanSeconds(1L).build();
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        System.out.println(cache.get("a"));
        System.out.println(cache.getAll());
        cache.put("d", 4);
        System.out.println(cache.getAll());
        System.out.println(cache.get("a"));
        System.out.println(cache.getAll());
        System.out.println(cache.get("b"));
        System.out.println(cache.getAll());
        System.out.println(cache.get("b"));
        for (int i = 0; i < 20; i++) {
            cache.put("key" + i, i);
            System.out.println(cache.get("a"));
            System.out.println(cache.getAll());
        }
    }
}