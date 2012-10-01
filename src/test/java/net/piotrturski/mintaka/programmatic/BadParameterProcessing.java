package net.piotrturski.mintaka.programmatic;

import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.SampleType;

public class BadParameterProcessing {

	@TestWith("a")
	public void typeWithoutCoercion(SampleType sampleType) {}
	
	@TestWith("not a boolean")
	public void wrongBooleanFormat(boolean b){}
}
