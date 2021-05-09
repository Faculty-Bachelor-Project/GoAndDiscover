<?php

$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
$conn = pg_connect($conn_string) or die(exit);

/*$sql_create = "CREATE TABLE locations
(
id INT GENERATED ALWAYS AS IDENTITY,
titleName varchar(255),
describelocation varchar(8000),
PRIMARY KEY(id)
)
";
*/

// $sql_create = "CREATE TABLE images
// (
// image_id INT GENERATED ALWAYS AS IDENTITY,
// imageLink varchar(255),
// locations_id int,
// PRIMARY KEY(image_id),
// CONSTRAINT fk_locations FOREIGN KEY(locations_id)
// REFERENCES locations(id)
// )
// ";

// $sql_create = "CREATE TABLE IF NOT EXISTS password_reset (
        // ID INT GENERATED ALWAYS AS IDENTITY,
        // email VARCHAR(255),
        // selector CHAR(16),
        // token CHAR(64),
        // expires BIGINT(20),
		// PRIMARY KEY(ID),
    // )";	
	
	
	$sql_create = "CREATE TABLE password_reset (
        ID INT GENERATED ALWAYS AS IDENTITY,
        email VARCHAR(255),
        otp_code VARCHAR(5),
		expire_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
		PRIMARY KEY(ID)
    )
	";	


$retval = pg_query($conn, $sql_create);
if(! $retval )
{
  die('Could not create table: ' . exit);
}
echo "Table created successfully\n";


?>