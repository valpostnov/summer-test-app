package com.postnov.android.summerschoolapp.data.exception;

/**
 * Created by platon on 15.08.2016.
 */
public class DataSourceFetchingException extends Exception
{
    private static final String DEFAULT_ERROR_MESSAGE = "Что-то пошло не так, перезапустите приложение.";

    public DataSourceFetchingException()
    {
        super();
    }

    public DataSourceFetchingException(String message)
    {
        super(message);
    }

    public DataSourceFetchingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DataSourceFetchingException(Throwable cause)
    {
        super(DEFAULT_ERROR_MESSAGE, cause);
    }
}
