package org.zohhak.internal.coercing;

final class Result {

	private Object result;
	private final boolean succeeded;
	public static final Result FAILURE = new Result(); 
	
	public static Result success(Object result) {
		return new Result(result);
	}
	
	private Result(Object result) {
		this.result = result;
		succeeded = true;
	}

	private Result() {
		this.succeeded = false;
	}
	
	public boolean succeeded() {
		return succeeded;
	}
	
	public Object getResult() {
		return result;
	}
	
}
