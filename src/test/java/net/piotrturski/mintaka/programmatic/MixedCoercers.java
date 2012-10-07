package net.piotrturski.mintaka.programmatic;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.CoercerWithSampleType;
import net.piotrturski.mintaka.helper.SampleType;

public class MixedCoercers {

	@TestWith("a")
	public void a_withoutCoercer(SampleType sampleType){}
	
	@TestWith(value="a", coercer=CoercerWithSampleType.class)
	public void b_withCoercer(SampleType sampleType) {}
	
	@TestWith("a")
	public void c_againWithoutCoercer(SampleType sampleType){}
	
}
