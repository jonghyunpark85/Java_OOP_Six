# Java_OOP_Six

## movie management system with database system

### Problem
You are tasked to modify the movie management system given in Assignment #1 to work with a relational database system. You’re required to connect to and perform queries on a MariaDB database running on your computer. <br/>
A SQL script that creates and populates a table is provided to you. Below are instructions on how to import the SQL script into your installed MariaDB database server. The “movies” table has the following structure:<br/>
Column Name	Data Type<br/>
Id	INT<br/>
duration	INT<br/>
Title	VARCHAR(255)<br/>
Year	YEAR(4)<br/><br/>

The appropriate SQL statements will be sent to and executed on the database server. Depending on the query being performed, the proper method is to be used. If records are retrieved, they will be displayed to the user. Any exceptions that occur because of queries will need to be handled.<br/>
Movies will be retrieved and persisted to the database on-demand. This means that they will not be loaded into memory when the program starts and saved from memory to the database when the program ends. You will not be using any problem domain classes. <br/><br/>
Your project will contain SQL statements that:<br/>
1.	Adds/inserts a new record representing a movie.<br/>
2.	Retrieves records with movies released in a specific year.<br/>
3.	Retrieves records with a list of random movies.<br/>
4.	Deletes a movie given its id.<br/>
### Notes:
•	You can use ORDER BY RAND() to randomize the order of records.<br/>
•	There is no need to specify a value for the ID column of a new movie record. This will be automatically set to the next available ID by the database.<br/>
•	Ensure any results, queries, and connections are closed when they are no longer needed. <br/>

 
