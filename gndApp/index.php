<?php

	$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
	$conn = pg_connect($conn_string);

	if (!$conn) 
	{
	  echo "An error occurred.\n";
	  exit;
	}
	
	$id = 1;
	
	//$id = (int)$_POST['id'];

	//$itemid = $_POST["itemid"];
	
		
	//$result = pg_query($conn,"SELECT titleName,describelocation,imagelink FROM arucomarkers,images WHERE arucomarkers.id = '" .$itemid. "' AND images.arucoid = '" .$itemid. "'");
	
	
	
	$result = pg_query($conn,"SELECT titleName,describelocation from locations WHERE id = " . $id . "");
	
	
	
	
	//$result = pg_query($conn,"SELECT titleName,describelocation FROM locations WHERE id = " . $itemid ."");
	
	
	
	
	$img = pg_query($conn,"SELECT imagelink FROM images WHERE locations_id = " . $id . "");
	
	$num_of_images = pg_num_rows($img);
	$images_arr = array();
	
	while($row = pg_fetch_assoc($result))
	{
		$rows = $row;
	}
	
	
	while($imgRow = pg_fetch_assoc($img))
	{
		$images_arr['images'][] = $imgRow;
	}

	 header('Content-Type:application/json');
	
	$jsonObj = json_encode($rows, JSON_PRETTY_PRINT);
	$jsonImgObjName = json_encode($images_arr, JSON_PRETTY_PRINT + JSON_UNESCAPED_SLASHES);
	
	
	$test = '[' . $jsonObj . $jsonImgObjName .']';
	
	//$jsonImg = json_encode($imgRows, JSON_UNESCAPED_SLASHES);
	//$test = trim($jsonObj,'}') . "," .trim($jsonImg,'[{]'). ",{" . '"num_of_images":' . '"' . $num_of_images . '"}';
	
	$search = "}{";
	$trimmed = str_replace($search, ',', $test) ;
	echo $trimmed;


?>



