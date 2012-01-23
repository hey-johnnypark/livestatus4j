# livestatus4j #
Java Client Library for [mk_livestatus](http://mathias-kettner.de/checkmk_livestatus.html) by Matthias Kettner

## About ##
Livestatus is a broker module for for [Nagios](http://nagios.org) which is a system-monitoring application. It enables the user to query information about the statuses of the objects directly out of the nagios core using lql (Livestatus Query Language). 

## Features ##
* querying of nagios objects snd statuses directly over the network using a lightweight protocol
* implemented so far: Tables (HOSTS, SERVERICES), Columns, Filters

## Getting Started ##
* Download the latest release

		wget https://github.com/hey-johnnypark/livestatus4j/zipball/master

* Extract the project
	
		tar xvf livestatus4j-xxx.zip

* Include the .jar file in your Java Buildpath 

* Use it!

		import static foo.bar.livestatus.LiveStatusTable.HOSTS;
		import foo.bar.livestatus.LiveStatusConn;
		import foo.bar.livestatus.LiveStatusQuery;
		import foo.bar.livestatus.LiveStatusResult;
		import foo.bar.livestatus.LiveStatusResultEntry;

		LiveStatusConn lsc = new LiveStatusConn("nagios-host", 6557); //set up the tcp connection
		LiveStatusQuery lsq = new LiveStatusQuery(HOSTS).addColumn("address").addColumn("name"); //create the query and add specific columns you like to query
		LiveStatusResult lsr= lsc.query(lsq); //lsq is an Array of LiveStatusResultEntry instances 
		LiveStatusResultEntry lsre= lsr.get(0); //retrieves the first entry
		
		System.out.println("HOST_NAME: " + lsre.get("name")); //prints the host name
		System.out.println("HOST_ADDRESS: " + lsre.get("address")); //prints the host address
		System.out.println("Results count: " + lsr.size()); //how many entries have been retrieved in total




