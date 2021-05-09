<?php
require "database.php";


	$db = new database();
	if (isset($_POST['user']) && isset($_POST['newpassw'])) {
		 if ($db->dbConnect()) {
			if ($db->ChangePassword($_POST['user'], $_POST['newpassw'])) {
				echo "Change Password Success";
			} else echo "Change Password Failed";
		} else echo "Error: Database connection";
	} else echo "All fields are required";
	
?>