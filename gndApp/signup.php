<?php
require "database.php";


	$db = new database();
	if (isset($_POST['user']) && isset($_POST['email']) && isset($_POST['pass'])) {
		if ($db->dbConnect()) {
			if ($db->signUp("users", $_POST['user'], $_POST['email'], $_POST['pass'])) {
				echo "Sign Up Success";
			} else echo "Sign up Failed";
		} else echo "Error: Database connection";
	} else echo "All fields are required";
	
	
	
?>