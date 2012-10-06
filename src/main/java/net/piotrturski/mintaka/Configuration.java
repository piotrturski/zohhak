package net.piotrturski.mintaka;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.internal.coercing.DefaultCoercer;

public class Configuration {

	
	private List<Class<?>> coercers = new ArrayList<Class<?>>(1);
	
	public Configuration() {
		coercers.add(defaultCoercer());
	}
	
	public void addCoercers(Class<?>[] additionalCoercers) {
		coercers.addAll(asList(additionalCoercers));
		
	}
	
	protected Class<?> defaultCoercer() {
		return DefaultCoercer.class;
	}

	public List<Class<?>> getCoercers() {
		return coercers;
	}

}
