package com.isa.ws.rate.config;

import org.ehcache.CacheManager;
import org.ehcache.ValueSupplier;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expiry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GeneralCacheConfiguration{

    private final RateLimitingProperties rateLimitingProperties;

    public GeneralCacheConfiguration(RateLimitingProperties rateLimitingProperties) {
        this.rateLimitingProperties = rateLimitingProperties;
    }

    @Bean
    public CacheManager ehCacheCacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("rate",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Integer.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10000, EntryUnit.ENTRIES)
                                .offheap(10, MemoryUnit.MB))
                        .withExpiry(new TimeFromCreationExpiry(Duration.of(rateLimitingProperties.getPeriod(), TimeUnit.SECONDS)))
        )
                .build(true);
        return cacheManager;
    }

    private static class TimeFromCreationExpiry implements Expiry<Object, Object> {

        private final Duration create;
        private final Duration access;
        private final Duration update;

        public TimeFromCreationExpiry(Duration create) {
            this.create = create;
            this.access = null;
            this.update = null;
        }

        @Override
        public Duration getExpiryForCreation(Object key, Object value) {
            return create;
        }

        @Override
        public Duration getExpiryForUpdate(Object key, ValueSupplier<?> oldValue, Object newValue) {
            return update;
        }

        @Override
        public Duration getExpiryForAccess(Object key, ValueSupplier<?> value) {
            return access;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final TimeFromCreationExpiry that = (TimeFromCreationExpiry) o;

            if (access != null ? !access.equals(that.access) : that.access != null) return false;
            if (create != null ? !create.equals(that.create) : that.create != null) return false;
            if (update != null ? !update.equals(that.update) : that.update != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = create != null ? create.hashCode() : 0;
            result = 31 * result + (access != null ? access.hashCode() : 0);
            result = 31 * result + (update != null ? update.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + "{" +
                    "create=" + create +
                    ", access=" + access +
                    ", update=" + update +
                    '}';
        }
    }
}
