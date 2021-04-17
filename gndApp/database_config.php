<?php

class database_config
{
	
	public $host;
	public $port;
	public $db_name;
	public $db_user;
	public $db_pass;
	
	public function __construct()
	{
		$this->host = "localhost";
		$this->port = "5432";
		$this->db_name = "postgres";
		$this->db_user = "postgres";
		$this->db_pass = "licenta1234";
	}
}

?>