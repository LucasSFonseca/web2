package com.example.exceptions;

public class SwapTransactionException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public SwapTransactionException(String message)
	{
        super(message);
    }
}
