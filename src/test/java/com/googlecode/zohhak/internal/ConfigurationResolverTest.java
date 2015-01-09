package com.googlecode.zohhak.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

import com.googlecode.zohhak.api.Configure;
import com.googlecode.zohhak.api.DefaultConfiguration;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.googlecode.zohhak.helper.ConstantConfiguration.ConstantCoercer;
import com.googlecode.zohhak.testutils.JUnitLauncher;

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
	
	@Test
	public void should_fail_when_cannot_instantiate_configuration() {
		Result result = JUnitLauncher.runWithZohhak(TestWithUninstantiableConfigurationSample.class);
		assertThat(result.getFailures().get(0).getException())
							.isInstanceOf(IllegalArgumentException.class)
							.hasMessageContaining("annot instantiate configuration class")
							.hasMessageContaining(UninstantiableConfiguration.class.getName());
	}
	
}

class TestWithUninstantiableConfigurationSample {
	
	@TestWith(value="a", configuration=UninstantiableConfiguration.class)
	public void should_fail_when_cant_instantiate_configuration(String s) {}
}

class UninstantiableConfiguration extends DefaultConfiguration {
	
	private UninstantiableConfiguration(int i) {}
}