<?php

include_once 'connection.php';
header('Content-Type: application/json');	
	class User {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function does_user_exist($email,$password)
		{
			$query = "SELECT * FROM `usuario` where email='$email' and senha = '$password' ";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$json['success'] = ' Welcome '.$email;
				
				$row = mysqli_fetch_row($result);
				$json['id_user'] = $row[0];
				echo json_encode($json);

				mysqli_close($this -> connection);
			}else{
				$json['error'] = ' wrong password ';
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['email'],$_POST['password'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		if(!empty($email) && !empty($password)){
			
			$encrypted_password = md5($password);
			$user-> does_user_exist($email,$encrypted_password);
			
		}else{
			$json['both'] = 'you must type both inputs';
			echo json_encode($json);
		}
		
	}














?>