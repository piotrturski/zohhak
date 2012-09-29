package net.piotrturski.mintaka.internal.junit;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

public class OrParentFilterTest {

	//comes from IDE. parameters to shouldRun comes from methods' descriptions
	Filter filter = mock(Filter.class);
	OrParentFilter orParentFilter = OrParentFilter.decorate(filter);
	
	@Test
	public void factoryTest() {
		
		OrParentFilter decorated1 = OrParentFilter.decorate(filter);
		OrParentFilter decorated2 = OrParentFilter.decorate(filter);
		
		assertThat(decorated1).isNotSameAs(decorated2);
	}
	
	@Test
	public void filteringTest() {
		// given
		Description parent = Description.createSuiteDescription("method(class)");
		Description child = Description.createSuiteDescription("method [2](class)");
		// when
		when(filter.shouldRun(parent)).thenReturn(true);
		when(filter.shouldRun(child)).thenReturn(false);
		// then
		assertThat(orParentFilter.shouldRun(parent)).isTrue();
		assertThat(orParentFilter.shouldRun(child)).isTrue();
	}
	
	@Test
	public void describe() {
		when(filter.describe()).thenReturn("all tests");
		
		assertThat(orParentFilter.describe()).startsWith("all tests ").contains(" with").contains(" without").contains(" parameters");
	}
	
}
