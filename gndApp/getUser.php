<?php

	// Create connection
	$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=admin";
	$conn = pg_connect($conn_string);

	if (!$conn) 
	{
	  echo "An error occurred.\n";
	  exit;
	}

	$user = $_POST['user'];
	$result = pg_query($conn,"SELECT email,progresbar from users WHERE username = '" . $user . "'");
	
	while($row = pg_fetch_assoc($result))
	{
		$rows['user'][]= $row;
	}
	
	$jsonObj = json_encode($rows);
	
	echo '[' . $jsonObj . ']';

	
?>

