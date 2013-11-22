package com.googlecode.zohhak.helper;

import com.googlecode.zohhak.api.Configuration;

public class ConstantConfiguration implements Configuration {

	public boolean inheritCoercers() {
		return false;
	}

	public Class<?>[] coercers() {
		return new Class<?>[]{ConstantCoercer.class};
	}

	public String separator() {
		return null;
	}

	public String stringBoundary() {
		return null;
	}

	public static class ConstantCoercer {
		
		public int to7(String input) {
			return 7;
		}
		
	}
	
}
