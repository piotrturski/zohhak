package net.piotrturski.mintaka.programmatic;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.SampleEnum;
import net.piotrturski.mintaka.helper.SampleType;

public class BadParameterProcessing {

	@TestWith("a")
	public void typeWithoutCoercion(SampleType sampleType) {}
	
	@TestWith("not a boolean")
	public void wrongBooleanFormat(boolean b){}
	
	@TestWith("not a valid enum")
	public void wrongEnumFormat(SampleEnum b){}
}
