package net.piotrturski.mintaka.helper;

public enum SampleEnum {

	VALUE_1,
	ANOTHER_VALUE("with param"),
	LAST_VALUE;
	
	SampleEnum() {}
	SampleEnum(String param) {}
	
}
