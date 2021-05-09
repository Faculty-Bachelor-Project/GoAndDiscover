<?php
require "database.php";

	$db = new database();
	
	
	if (isset($_POST['user']) && isset($_POST['email']) && isset($_POST['pass'])) {
		
		$isEmailDeliverable = $db->isDerivable($_POST['email']);
		
		if(filter_var($_POST['email'], FILTER_VALIDATE_EMAIL) === false)
		{
			echo "invalid format";
		}
		
		else if($isEmailDeliverable === true)
		{
			//Email address is valid so populate database with user's values
			if ($db->dbConnect()) {
				if ($db->signUp("users", $_POST['user'], $_POST['email'], $_POST['pass'])) {
					echo "Sign Up Success";
				} else echo "Sign up Failed";
			} else echo "Error: Database connection";	
		}else echo "Email address is invalid";
			
	} else echo "All fields are required";
	
	
	
?>