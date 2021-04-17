<?php

	// Create connection
	$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
	$conn = pg_connect($conn_string);

	if (!$conn) 
	{
	  echo "An error occurred.\n";
	  exit;
	}
	
	//$itemid = $_POST["itemid"];
	
	$img = pg_query($conn,"SELECT imagelink FROM images where locations_id = 1");
	
	while($imgRow = pg_fetch_assoc($img))
	{
		$imgRows[] = $imgRow;
	}
	
	$jsonImg = json_encode($imgRows, JSON_UNESCAPED_SLASHES);
	
	echo $jsonImg;
	//echo '{"images":' . $jsonImg . "}";
	//echo trim($jsonImg,'[]');;
	//echo "{" . '"imgArray:"' . " " . $jsonImg . "}";


?>