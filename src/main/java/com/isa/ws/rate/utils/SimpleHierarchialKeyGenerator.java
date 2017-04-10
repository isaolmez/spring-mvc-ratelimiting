package com.isa.ws.rate.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Basic implementation of HierarchialKeyGenerator API
 * 
 * @author isa
 *
 */
@Component("hierarchial")
public class SimpleHierarchialKeyGenerator implements HierarchialKeyGenerator {

	private String prefix = "default";

	@Value("${memcached.keySeperator}")
	private String seperator = "-";

	@Override
	public SimpleHierarchialKeyGenerator withPrefix(String category) {
		this.prefix = category;
		return this;
	}

	@Override
	public SimpleHierarchialKeyGenerator withSeperator(String seperator) {
		this.seperator = seperator;
		return this;
	}

	@Override
	public String toKey(String str) {
		return prefix + seperator + str;
	}

	@Override
	public String toKey(Object o) {
		if (o != null) {
			return prefix + seperator + o.hashCode();
		}

		return prefix + seperator;
	}
}
