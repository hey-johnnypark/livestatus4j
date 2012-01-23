package foo.bar.livestatus;

public enum LiveStatusTable {
		HOSTS ("hosts"),
		SERVICES ("services");
		
		private String command;
		
		LiveStatusTable(String command)
		{
			this.command = command;
		}
		
		@Override
		public String toString()
		{
			return this.command; 
		}
}
