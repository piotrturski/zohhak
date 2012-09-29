package net.piotrturski.mintaka.helper;

public class CoercerWithSampleType {

	public SampleType toSampleType(String input) {
		SampleType sampleType = new SampleType();
		sampleType.value = input;
		return sampleType;
	}
	
}
