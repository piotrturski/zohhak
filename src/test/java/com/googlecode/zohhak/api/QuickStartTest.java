package com.googlecode.zohhak.api;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;

@RunWith(ZohhakRunner.class)
public class QuickStartTest {

	@TestWith({
        "2, 1,	3",
        "3, 5,	8"
    })
    public void should_add_numbers(int addend1, int addend2, int result) {
		
        assertThat(addend1 + addend2).isEqualTo(result);
    }
}
