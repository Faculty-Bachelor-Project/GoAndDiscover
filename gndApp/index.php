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
	
	//$result = pg_query($conn,"SELECT titleName,describelocation,imagelink FROM arucomarkers,images WHERE arucomarkers.id = '" .$itemid. "' AND images.arucoid = '" .$itemid. "'");
	$result = pg_query($conn,"SELECT titleName,describelocation from locations WHERE id = 1");
	
	//$result = pg_query($conn,"SELECT titleName,describelocation FROM locations WHERE id = " . $itemid ."");
	$img = pg_query($conn,"SELECT imagelink FROM images WHERE locations_id = 1");
	$num_of_images = pg_num_rows($img);
	
	while($row = pg_fetch_assoc($result))
	{
		$rows = $row;
	}
	
	while($imgRow = pg_fetch_assoc($img))
	{
		$imgRows[] = $imgRow;
	}
	
	$jsonObj = json_encode($rows, JSON_UNESCAPED_SLASHES);
	$jsonImg = json_encode($imgRows, JSON_UNESCAPED_SLASHES);
	
	//echo '{"images":' .$jsonObj . ',{"images":' . $jsonImg ."}";
	
	//$test = trim($jsonObj,'}') . "," .trim($jsonImg,'[{]'). ",{" . '"num_of_images":' . '"' . $num_of_images . '"}';
	
	//$search = "},{";
	//$trimmed = str_replace($search, ',', $test) ;
	//echo $trimmed;
	echo '[' . $jsonObj . ',' . str_replace('[' ,'' , $jsonImg) . "<br><br>";
	
	// //Generate a random string.
	// $token = openssl_random_pseudo_bytes(16);

	// //Convert the binary data into hexadecimal representation.
	// $token = bin2hex($token);

	// //Print it out for example purposes.
	// echo $token;

	$length = 16; // Adjust length to fit your new paranoia level. 16 is probably a sane default and the same length as md5 (if you are migrating from a method that uses it)
	$token = bin2hex(random_bytes($length)); // bin2hex output is url safe.
	
	echo $token . "<br><br>";
	
	
	
	//-----------------------------------------------------------------------------------------------------------//
	//de modificat ip-ul de la linkurile din baza de date a imaginilor printr-o functie ce le va modifica automat//
	//-----------------------------------------------------------------------------------------------------------//
?>



