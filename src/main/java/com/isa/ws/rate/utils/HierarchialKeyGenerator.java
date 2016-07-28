package com.isa.ws.rate.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Basic implementation of IHierarchialKeyGenerator API
 * 
 * @author isa
 *
 */
@Component("hierarchial")
public class HierarchialKeyGenerator implements IHierarchialKeyGenerator {

	private String prefix = "default";

	@Value("${memcached.keySeperator}")
	private String seperator = "-";

	public HierarchialKeyGenerator withPrefix(String category) {
		this.prefix = category;
		return this;
	}

	public HierarchialKeyGenerator withSeperator(String seperator) {
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

	/**
	 * Simple test to compare performance
	 */
	public static void main(String[] args) {
		HierarchialKeyGenerator gen = new HierarchialKeyGenerator().withPrefix("test");
		StringBuffer buffer = new StringBuffer();
		String prefix = "test";
		String seperator = "::";
		String s;
		long limit = Long.MAX_VALUE / 1000_000_000;
		for (int k = 0; k < 10; k++) {
			long t1 = System.currentTimeMillis();
			for (long i = 0; i < limit; i += 1000) {
				buffer = new StringBuffer();
				buffer.append(prefix).append(seperator).append("abc" + i).toString();
			}
			System.out.println(System.currentTimeMillis() - t1);

			t1 = System.currentTimeMillis();
			for (long i = 0; i < limit; i += 1000) {
				s = "test" + "::" + "xyz" + i;
			}
			System.out.println(System.currentTimeMillis() - t1);

			t1 = System.currentTimeMillis();
			for (long i = 0; i < limit; i += 1000) {
				s = prefix + seperator + "xyz" + i;
			}
			System.out.println(System.currentTimeMillis() - t1);

			t1 = System.currentTimeMillis();
			for (long i = 0; i < limit; i += 1000) {
				gen.toKey("pqr" + i);
			}
			System.out.println(System.currentTimeMillis() - t1);
			System.out.println("------");
		}
	}
}
