package com.googlecode.zohhak.api;

import static ch.lambdaj.collection.LambdaCollections.with;
import static com.google.common.collect.Maps.newHashMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;

import ch.lambdaj.collection.LambdaList;
import ch.lambdaj.function.convert.Converter;

import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class CollectionsCoercionTest {

	@TestWith(value="[2, 3, 3, 5]", separator=";")
	public void should_coerce_array(Number[] array) {
		assertThat(array).containsExactly(2,3,3,5);
	}

	@TestWith(value="[2, 3, 3, 5]", separator=";")
	public void should_coerce_vararg(Number... vararg) {
		assertThat(vararg).containsExactly(2,3,3,5);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@TestWith(value="[2, 3, 3, 5]", separator=";")
	public void should_coerce_untyped_list(List list) {
		assertThat(list).containsExactly(2,3,3,5);
	}
	
	@TestWith(value="[2, 3, 3, 5]", separator=";")
	public void should_coerce_typed_list(List<Integer> list) {
		assertThat(list).containsExactly(2,3,3,5);
	}
	
	@TestWith(value="[foo: 2, bar: 3, baz:3]", separator="_")
	public void should_coerce_map(Map<String, Integer> map) {
		assertThat(map)
					.containsEntry("foo", 2)
					.containsEntry("bar", 3)
					.containsEntry("baz", 3);
	}
	
	@Coercion
	public Integer[] toIntegerArray(String input) {
		return toList(input).toArray(Integer.class);
	}

	@Coercion
	public LambdaList<Integer> toList(String input) {
		String[] numbers = input.replace("[", "").replace("]", "").split(",");
		
		return with(numbers).convert(new Converter<String, Integer>() {
			
			public Integer convert(String from) {
				return Integer.parseInt(from.trim());
			}
		});
	}

	@Coercion
	public Map<String, Integer> toMap(String input) {
		String[] entries = input.replace("[", "").replace("]", "").split(",");
		
		HashMap<String,Integer> map = newHashMap();
		
		for (String entry : entries) {
			String[] mapEntry = entry.split(":");
			map.put(mapEntry[0].trim(), Integer.parseInt(mapEntry[1].trim()));
		}
		return map;
	}
	
}
