package net.piotrturski.mintaka.helper;

public enum SampleEnum {

	VALUE_1,
	ONE_OF_ENUM_VALUES("with param"),
	LAST_VALUE;
	
	SampleEnum() {}
	SampleEnum(String param) {}
	
}
