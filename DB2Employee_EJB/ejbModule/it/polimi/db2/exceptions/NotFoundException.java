package it.polimi.db2.exceptions;

public class NotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}
