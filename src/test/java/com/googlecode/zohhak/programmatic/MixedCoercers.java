package com.googlecode.zohhak.programmatic;


import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.helper.SampleType;
import com.googlecode.zohhak.helper.SecondCoercer;


public class MixedCoercers {

	@TestWith("a")
	public void a_withoutCoercer(SampleType sampleType){}
	
	@TestWith(value="a", coercers=SecondCoercer.class)
	public void b_withCoercer(SampleType sampleType) {}
	
	@TestWith("a")
	public void c_againWithoutCoercer(SampleType sampleType){}
	
}
