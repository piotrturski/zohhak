package net.piotrturski.mintaka.programmatic;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.SecondCoercer;
import net.piotrturski.mintaka.helper.SampleType;

public class MixedCoercers {

	@TestWith("a")
	public void a_withoutCoercer(SampleType sampleType){}
	
	@TestWith(value="a", coercer=SecondCoercer.class)
	public void b_withCoercer(SampleType sampleType) {}
	
	@TestWith("a")
	public void c_againWithoutCoercer(SampleType sampleType){}
	
}
