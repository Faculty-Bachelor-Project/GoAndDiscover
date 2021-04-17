<?php

require 'PHPMailer/PHPMailerAutoload.php';
require "database.php";


	$db = new database();
	$emailAddress = $_POST['email'];
	$token = bin2hex(random_bytes(16));

	if (isset($emailAddress)) 
	{			
		if($db->resetPassword($emailAddress,$token))
		{
			$mail = new PHPMailer;
			$mail->Host = 'smtp.gmail.com';
			$mail->Port=587;
			$mail->isSMTP();
			$mail->SMTPAuth=true;
			$mail->SMTPSecure='tls';

			// prenume: Go&Discover 
			// nume: 	App
			$mail->Username='gndappcontact@gmail.com';
			$mail->Password='candfrodatastr3su';
			$mail->setFrom('gndappcontact@gmail.com','Password reset');
			$mail->addAddress($emailAddress);
			$mail->addReplyTo('gndappcontact@gmail.com');
			$mail->isHTML(true);
			$mail->Subject='Reset Password';

			$mail->Body = "We've received a request to reset the password for this email address.\r\nTo change your password click on the link below: ".
							<<<EOF
							<html>
							<body>
							<a href="http://localhost/gndapp/resetPassword.php?token=$token">http://localhost/gndapp/resetPassword.php?token=$token</a>
							</body>
							</html>
							EOF .
						"Please do not reply to this email. If you have any questions, please contact us at gndappcontact@gmail.com";

			if(!$mail->send())
			{
				echo "Something went wrong" . "<br><br>";
				echo $mail->ErrorInfo;
			}else
			{
				echo "Email sent successfully";
			}
		}
	}
?>