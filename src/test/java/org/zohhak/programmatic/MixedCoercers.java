package org.zohhak.programmatic;

import org.zohhak.api.TestWith;
import org.zohhak.helper.SampleType;
import org.zohhak.helper.SecondCoercer;


public class MixedCoercers {

	@TestWith("a")
	public void a_withoutCoercer(SampleType sampleType){}
	
	@TestWith(value="a", coercer=SecondCoercer.class)
	public void b_withCoercer(SampleType sampleType) {}
	
	@TestWith("a")
	public void c_againWithoutCoercer(SampleType sampleType){}
	
}
