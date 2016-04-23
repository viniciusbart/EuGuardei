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
		
		public function does_user_exist($email,$password,$nome)
		{
			$query = "SELECT * FROM `usuario` where email='$email' and senha = '$password' ";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$json['exists'] = ' This e-mail '.$email.' already exists';
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$query = "insert into `usuario` (email,senha,nome) values ('$email','$password','$nome')";
				$inserted = mysqli_query($this -> connection, $query);
				if($inserted == 1 ){
					$json['success'] = 'Acount created';
				}else{
					$json['error'] = ' fail insert ';
				}
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['email'],$_POST['password'],$_POST['nome'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		$nome = $_POST['nome'];
		
		if(!empty($email) && !empty($password) && !empty($nome)){
			
			$encrypted_password = md5($password);
			$user-> does_user_exist($email,$encrypted_password,$nome);

		}else{
			$json['both'] = 'you must type both inputs';
			echo json_encode($json);
		}
		
	}

?>