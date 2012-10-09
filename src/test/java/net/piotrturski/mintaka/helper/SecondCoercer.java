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
	
	public int toIntFromBinary(String binaryInt) {
		assertThat(binaryInt).startsWith("b");
		String plainBinary = binaryInt.substring(1);
		return Integer.parseInt(plainBinary, 2);
	}
	
}
