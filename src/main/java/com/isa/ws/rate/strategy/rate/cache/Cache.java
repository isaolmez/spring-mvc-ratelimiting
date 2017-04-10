package com.isa.ws.rate.strategy.rate.cache;

public interface Cache<TKey, TValue, TBoolean> {

    TBoolean set(TKey key, TValue value);

    TBoolean set(TKey key, TValue value, int ttl);

    TBoolean delete(TKey key);

    TValue get(TKey key);
}
