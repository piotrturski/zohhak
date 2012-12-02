package org.zohhak.internal;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.zohhak.api.Configure;
import org.zohhak.api.DefaultConfiguration;
import org.zohhak.api.TestWith;
import org.zohhak.api.runners.ZohhakRunner;
import org.zohhak.helper.ConstantConfiguration.ConstantCoercer;

@RunWith(ZohhakRunner.class)
@Configure(coercer={}, separator="1")
public class ConfigurationResolverTest {

	@TestWith(configuration=DefaultConfiguration.class, value={
		"1, 2"
	})
	public void configMergeTest(int i, int j) {}
	
	@TestWith(configuration=DefaultConfiguration.class, coercer=ConstantCoercer.class, inheritCoercers=false,
		value={"1"
	})
	public void overrideInlinedCoercers(int i) {
		assertThat(i).isEqualTo(7);
	}
	
}
