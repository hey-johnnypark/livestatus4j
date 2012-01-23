package foo.bar.livestatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Sets up a connection to a livestatus instance via network socket
 * 
 * @author Andreas Finke
 * 
 */
public class LiveStatusConn
{
	String host = null;
	Integer port = null;
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;

	/**
	 * Creates an instance. Before you can use it, the open() method must be
	 * called. The instance is reusable by using the open() and close() methods
	 * 
	 * @param host
	 *            The host the connection should be established to
	 * @param port
	 *            The port for the TCP connection
	 */
	public LiveStatusConn(String host, Integer port)
	{
		this.host = host;
		this.port = port;
	}

	/**
	 * Tries to open a connection using the given host and port attributes
	 * 
	 * @throws IOException
	 *             see {@link IOException}
	 */
	private void open() throws IOException
	{
		socket = new Socket(host, port);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	/**
	 * Tries to open a connection using the given host and port attributes
	 * 
	 * @throws IOException
	 *             see {@link IOException}
	 */
	private void close() throws IOException
	{
		in.close();
		out.close();
		socket.close();
		socket = null;
		out = null;
		in = null;
	}

	/**
	 * Runs a {@link LiveStatusQuery}
	 * 
	 * @param lsq
	 *            The query that should be run
	 * @return The qery result as an instance of {@link LiveStatusResult}
	 * @throws IOException
	 *             If there was a problem while reading the TCP stream
	 */
	public LiveStatusResult query(LiveStatusQuery lsq) throws IOException
	{
		open();
		LiveStatusResult lsr = new LiveStatusResult();

		out.println(lsq);

		String stringBuff = "";
		int counter = 0;
		ArrayList<String> columns = lsq.columns;

		
		while ((stringBuff = in.readLine()) != null)
		{
			LiveStatusResultEntry lsre = new LiveStatusResultEntry();
			if ((columns.size() == 0) && counter == 0)
			{
				columns = new ArrayList<String>(Arrays.asList(stringBuff.split(";")));
			}
			else
			{
				String[] split = stringBuff.split(";");

				Integer i = Integer.valueOf(0);
				for (String column : columns)
				{
					lsre.put(column, split[i.intValue()]);
					i = Integer.valueOf(i.intValue() + 1);
				}

				lsr.add(lsre);
			}
			counter++;
		}
		close();
		return lsr.size() != 0 ? lsr : null;
	}
}
