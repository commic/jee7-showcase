package com.mycompany.control;


public class ValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5078142785512224863L;
	
	final private String[] violations;
	
	public ValidationException(String message) {
		super(message);
		violations = null;
	}
	
	public ValidationException(String[] violations) {
		super("There are some validation violations");
		this.violations = violations;
	}

	public String[] getViolations() {
		return violations;
	}
	
	public void printViolations() {
		for(String m: violations) {
			System.out.println(m);
		}
	}

}
