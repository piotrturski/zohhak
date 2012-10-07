package net.piotrturski.mintaka.helper;

import static org.fest.assertions.api.Assertions.assertThat;

public class SecondCoercer {

	public SampleType toSampleType(String input) {
		SampleType sampleType = new SampleType();
		sampleType.value = input;
		return sampleType;
	}
	
	public int toIntFromHex(String hexInt) {
		assertThat(hexInt).startsWith("0x");
		String plainHex = hexInt.substring(2);
		return Integer.parseInt(plainHex, 16);
	}
	
}
