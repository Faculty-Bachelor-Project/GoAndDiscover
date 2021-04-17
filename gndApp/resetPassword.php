<!DOCTYPE HTML>

<html>
<head>

<style type="text/css">
	
	.grandParentContaniner{
		display:table; height:100%; margin: 0 auto;
		margin-top:9%;
	}
	
	input[type=password], password {
	  width: 100%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	}
	
	input[type=text], text {
	  width: 100%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  display: inline-block;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  box-sizing: border-box;
	}
	
	input[type=submit] {
	  width: 100%;
	  background-color: #5bad51;
	  color: white;
	  padding: 14px 20px;
	  margin: 8px 0;
	  border: none;
	  border-radius: 4px;
	  cursor: pointer;
	}

	.parentContainer{
		display:table-cell;  padding:65px; border:1px solid;width:300px;
	}
 h1{
  font-size:29px;
  text-align:center;
 }
</style>
<title>Reset Password</title> 

</head>
<body>

<script>
	function myFunction() {
	  var x = document.getElementById("password");
	  var y = document.getElementById("confirm_pass");
	  if (x.type === "password" && y.type === "password") {
		x.type = "text";
		y.type = "text";
	  } else {
		x.type = "password";
		y.type = "password";
	  }
	}
</script> 

<div class="grandParentContaniner">
	<div class="parentContainer">
	
		<h1>Reset Password</h1>
		
		<?php

		require "database.php";

			$newPassword = $_POST['password'];
			$newPasswordConfirmation = $_POST['confirm_pass'];

			if ( isset( $email ) ) 
			{
				$db = new database();
				tokenVerify($emailAddress,$newPassword);
			}


		?>
	
	<br>
		<form method = "post">
		  <!--<label for="fname">Password:</label><br> -->
		  Password:<input type="password" id="password" name="password"><br><br>

		  Confirm password:<input type="password" id="confirm_pass" name="confirm_pass"><br>
		  
		  <input type="checkbox" onclick="myFunction()">Show Password<br><br>
		  
		  <input type="submit" value="Reset Password">
		</form> 
	</div>
</div>
</body>
</html>