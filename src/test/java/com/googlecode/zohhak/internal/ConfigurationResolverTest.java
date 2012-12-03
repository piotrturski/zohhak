package com.googlecode.zohhak.internal;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.DefaultConfiguration;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.helper.ConstantConfiguration.ConstantCoercer;

@RunWith(ZohhakRunner.class)
@Configure(coercers={}, separator="1")
public class ConfigurationResolverTest {

	@TestWith(configuration=DefaultConfiguration.class, value={
		"1, 2"
	})
	public void configMergeTest(int i, int j) {}
	
	@TestWith(configuration=DefaultConfiguration.class, coercers=ConstantCoercer.class, inheritCoercers=false,
		value={"1"
	})
	public void overrideInlinedCoercers(int i) {
		assertThat(i).isEqualTo(7);
	}
	
}
