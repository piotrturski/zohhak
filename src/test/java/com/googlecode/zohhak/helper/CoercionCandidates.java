package com.googlecode.zohhak.helper;

public class CoercionCandidates {

	public void voidReturn(String param) {}
	
	public boolean noParams() {return true;}
	
	public boolean tooManyParams(String param1, String param2) {return true;}
	
	public boolean wrongParamType(long param) {return true;}
	
	public boolean validCoercion(String param) {return true;}
	
}
