package semantics;

public class SemanticException extends Exception
{
	String	message;
	int	line;
	int	offset;

	public SemanticException(String msg, int l, int o)
	{
        super( msg);
		message = msg;
		line = l;
		offset = o;
	}

	public String toString()
	{
		String s = "Error on line " + line + "(" + offset + "): " + message;

		return s;
	}
}
