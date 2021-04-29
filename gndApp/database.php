<?php
require "database_config.php";


class database
{

	public $connect;
    public $data;
    private $sql;
    protected $host;
	protected $port;
	protected $db_name;
    protected $db_user;
    protected $db_pass;
	
	public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new database_config();
        $this->host = $dbc->host;
		$this->port = $dbc->port;
		$this->db_name = $dbc->db_name;
        $this->db_user = $dbc->db_user;
        $this->db_pass = $dbc->db_pass;
    
	}
	
	function dbConnect()
    {
		$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
        $this->connect = pg_connect($conn_string);
        return $this->connect;
    }
	
	function prepareData($data)
    {
        return pg_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }
	
	function login($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
		$result = pg_query($this->connect, $this->sql);
        $row = pg_fetch_assoc($result);
		
        if (pg_num_rows($result) != 0) 
		{
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) 
			{
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }
	
	function signup($table, $username, $email, $password)
    {
        $username = $this->prepareData($username);
		$email = $this->prepareData($email);
        $password = $this->prepareData($password);
		
        $password = password_hash($password, PASSWORD_DEFAULT);
		
        $this->sql =
            "INSERT INTO " . $table . " (username, email, password) VALUES ('" . $username . "','" . $email . "','" . $password . "')";
        
		if (pg_query($this->connect, $this->sql)) 
		{
            return true;
        } else return false;
    }
	
	function resetPassword($email, $token)
    {
		$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
		$conn = pg_connect($conn_string);
		
		$this->sql =
            "INSERT INTO password_reset(email,token) VALUES('" . $email . "','" . $token . "')";
		
		if (pg_query($conn, $this->sql)) 
		{
            return true;
        } else return false;
    }
	
	function tokenVerify($email,$newPassword)
    {
		$conn_string = "host=localhost port=5432 dbname=postgres user=postgres password=licenta1234";
		$conn = pg_connect($conn_string);
		$expireTime = date("Y-m-d H:i:s",strtotime(date("Y-m-d H:i:s")." + 5 minutes"));
		
		$this->sql =
            "SELECT token,timezone FROM reset_password WHERE email='" . $email . "'";
        

		if (pg_query($conn, $this->sql)) 
		{
			while ($row = mysql_fetch_array($result)) 
			{
				$token = $row['token'];
				$time = $row['expire_date'];
			}
			
			if($token!="" && $time > $expireTime)
			{
				echo '<script>alert("Token has expired. Do another password request from App.")</script>';
				die();
			}else
			{
				$this->sql =
					"UPDATE reset_password SET password='" . $newPassword . "' WHERE email='" . $email . "'";
				if (pg_query($conn, $this->sql))
				{
					
				}
			}
        }
    }
	
	function isDerivable($emailAddress)
	{
		$api_key = "3170dec1aa1e4422b916a2b09241b87b";

		$ch = curl_init();

		curl_setopt_array($ch, [
			CURLOPT_URL => "https://emailvalidation.abstractapi.com/v1?api_key=$api_key&email=$emailAddress",
			CURLOPT_RETURNTRANSFER => true,
			CURLOPT_FOLLOWLOCATION => true
		]);
		
		$response = curl_exec($ch);

		curl_close($ch);

		$data = json_decode($response, true);
		
		if($data['deliverability'] === "DELIVERABLE" && $data["is_free_email"]["value"] === true && $data["is_disposable_email"]["value"] === true)
		{
		return true;
		} else return false;
	}
	
	
}

?>