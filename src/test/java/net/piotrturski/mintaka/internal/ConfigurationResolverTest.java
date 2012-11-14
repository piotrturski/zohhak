package net.piotrturski.mintaka.internal;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.Configure;
import net.piotrturski.mintaka.DefaultConfiguration;
import net.piotrturski.mintaka.TestWith;
import net.piotrturski.mintaka.helper.ConstantConfiguration.ConstantCoercer;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

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
