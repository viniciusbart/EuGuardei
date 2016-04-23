<?php

include_once 'connection.php';
header('Content-Type: application/json');	
	class GetAll {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function getByIdUser($id_user)
		{
			$query = "SELECT * FROM `item` WHERE id_user='$id_user'";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$list = array();
				//looping through all the records fetched
				while($row = mysqli_fetch_array($result)){
					//Pushing into array created 
					array_push($list,array(
					"desc_item"=>$row['desc_item'],
					"date_item"=>$row['date_item'],
					));
				}
				//Displaying the array in json format 
				echo json_encode(array('list'=>$list));
				mysqli_close($this -> connection);
			}else{
				$json['error'] = ' No results ';
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$getall = new GetAll();
	if(isset($_POST['id_user'])){
		$id_user = $_POST['id_user'];
		if(!empty($id_user)){
			$getall-> getByIdUser($id_user);
		}else{
			$json['error'] = 'id_user is empty';
			echo json_encode($json);
		}
		
	}

?>