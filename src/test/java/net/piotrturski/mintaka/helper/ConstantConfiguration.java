package net.piotrturski.mintaka.helper;

import net.piotrturski.mintaka.Configuration;

public class ConstantConfiguration implements Configuration {

	@Override
	public boolean inheritCoercers() {
		return false;
	}

	@Override
	public Class<?>[] coercer() {
		return new Class<?>[]{ConstantCoercer.class};
	}

	@Override
	public String separator() {
		return null;
	}

	@Override
	public String stringBoundary() {
		return null;
	}

	public static class ConstantCoercer {
		
		public int to7(String input) {
			return 7;
		}
		
	}
	
}
