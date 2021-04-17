<?php
require "database.php";



	$db = new database();
	if (isset($_POST['user']) && isset($_POST['pass'])) {
		if ($db->dbConnect()) {
			if ($db->logIn("users", $_POST['user'], $_POST['pass'])) {
				echo "Login Success";
			} else echo "Username or Password wrong";
		} else echo "Error: Database connection";
	} else echo "All fields are required";


?>


