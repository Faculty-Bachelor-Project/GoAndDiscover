<?php

$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
$conn=pg_connect($conn_string) or die(exit);


 // $sql_update = "UPDATE images SET imagelink = 'http://192.168.0.164/AplicatieLicenta/imagini/turnul_alb_distanta.jpg' WHERE image_id = 5;";

 // $retval = pg_query($conn, $sql_update);
 // if(! $retval )
 // {
   // die('Could not update data: ' . exit);
 // }
 // echo "Data updated successfully\n";
 
 
 	$sql_create = "CREATE TABLE users
	(
	id INT GENERATED ALWAYS AS IDENTITY,
	username varchar(25),
	email varchar(25),
	password varchar(255),
	progressBar int,
	PRIMARY KEY(id)
	)
	";


	$retval = pg_query($conn, $sql_create);
	if(! $retval )
	{
	  die('Could not create table: ' . exit);
	}
	echo "Table created successfully\n";



?>