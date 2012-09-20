package net.piotrturski.mintaka;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Coercion {

	/* start with string. add everything that starts from string. 
	 * while true (not converted)
	 * 		check if we have matching tuple (if we reached tartgetType). generics?!
	 * 			if yes - try to use it without exception and result
	 * 		add everything that starts from the end of existing tuples and remove all tuples from that iteration (shortest)
	 * end of while
	 * throw coercion failed
	 */
	
	public BigInteger coerce(BigDecimal input) {
		return input.toBigIntegerExact();
	}
	
	public BigDecimal coerce(String toCoerce) {
		return new BigDecimal(toCoerce);
	}
	
}
