package com.googlecode.zohhak.helper;

import com.googlecode.zohhak.api.Configuration;

public class ConstantConfiguration implements Configuration {

	@Override
	public boolean inheritCoercers() {
		return false;
	}

	@Override
	public Class<?>[] coercers() {
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
