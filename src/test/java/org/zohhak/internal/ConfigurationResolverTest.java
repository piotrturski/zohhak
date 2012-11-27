package org.zohhak.internal;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.zohhak.Configure;
import org.zohhak.DefaultConfiguration;
import org.zohhak.TestWith;
import org.zohhak.helper.ConstantConfiguration.ConstantCoercer;
import org.zohhak.runners.MintakaRunner;

@RunWith(MintakaRunner.class)
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
