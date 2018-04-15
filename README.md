# DeustoBox
Repository for Team BSPQ18-E6

Inserts data in DB
mvn exec:java -Pload

Runs the server
mvn exec:java -Pserver

Runs the client
mvn exec:java -Pclient

RMI FOR MAC USERS
rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false &
