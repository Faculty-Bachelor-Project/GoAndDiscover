<?php
require "database.php";
		
		$db = new database();
		$newPassword = $_POST['newPassword'];
		$emailAddress = $_POST['email'];
		
		if(isset($newPassword) || isset($emailAddress))
		{
			if($db->updatePassword($emailAddress,$newPassword) && $db->deleteOTPCode($emailAddress))
			{
				echo "Password updated";
				
			}
			else
			{
				echo "Error";
			}
		}	
?>