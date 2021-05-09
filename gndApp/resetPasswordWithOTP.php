<?php

		require "database.php";

		$db = new database();
		$otp_code = $_POST['otp_code'];
		
		$emailAddress = $db->getEmailAddress($otp_code);
		
		if(isset($otp_code))
		{
			if($otp_code == $db->OTPCodeVerify($emailAddress))
			{
				echo "Verification success";
			}
			else
			{
				echo "Verification failed" . $db->OTPCodeVerify($emailAddress);
			}
		}
		
?>