package com.googlecode.zohhak.programmatic;


import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.helper.SampleEnum;
import com.googlecode.zohhak.helper.SampleType;


public class BadParameterProcessing {

	@TestWith("a")
	public void typeWithoutCoercion(SampleType sampleType) {}
	
	@TestWith("not a boolean")
	public void wrongBooleanFormat(boolean b){}
	
	@TestWith("not a valid enum")
	public void wrongEnumFormat(SampleEnum b){}
	
	@TestWith("1, 2")
	public void tooManyParameters(int i) {}
	
	@TestWith("1")
	public void tooFewParameters(int i, int j) {}
}
