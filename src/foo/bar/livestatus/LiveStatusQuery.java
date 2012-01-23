package foo.bar.livestatus;

import java.util.ArrayList;

public class LiveStatusQuery
{
	String queryString = "";
	LiveStatusTable table = null;
	ArrayList<String> columns = null;
	ArrayList<String> filters = null;
	boolean keepAlive = false;

	public LiveStatusQuery()
	{
		columns = new ArrayList<String>();
		filters = new ArrayList<String>();
	}

	public LiveStatusQuery(LiveStatusTable table)
	{
		this();
		this.table = table;
	}

	public LiveStatusQuery(boolean keepAlive)
	{
		this();
		this.keepAlive = keepAlive;
	}

	public LiveStatusQuery(LiveStatusTable table, boolean keepAlive)
	{
		this(table);
		this.keepAlive = keepAlive;
	}

	public LiveStatusQuery addColumn(String e)
	{
		this.columns.add(e);
		return this;
	}

	public boolean addFilter(String e)
	{
		return this.filters.add(e);
	}

	public void setTable(LiveStatusTable lst)
	{
		this.table = lst;
	}

	public String getLql()
	{
		StringBuilder query = new StringBuilder("");

		query.append("GET ").append(table).append("\n");

		if (columns.size() != 0)
		{
			query.append("Columns: ");

			for (String column : columns)
			{
				query.append(column).append(" ");
			}

			String temp = query.toString();
			query = new StringBuilder(temp.trim());
			query.append("\n");
		}

		if (filters.size() != 0)
		{
			for (String filter : filters)
			{
				query.append("Filter: ").append(filter).append("\n");
			}
		}

		if (keepAlive)
		{
			query.append("KeepAlive: on\n");
			query.append("ResponseHeader: fixed16\n");
		}

		query.append("\n");
		return query.toString();
	}

	@Override
	public String toString()
	{
		return getLql();
	}

}
