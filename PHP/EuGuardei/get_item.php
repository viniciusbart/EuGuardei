<?php

include_once 'connection.php';
header('Content-Type: application/json');	
	class GetOne {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function getByIdItem($id_item)
		{
			$query = "SELECT * FROM `item` WHERE id_item='$id_item'";
			$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$list = array();
				//looping through all the records fetched
				while($row = mysqli_fetch_array($result)){
					//Pushing into array created 
					array_push($list,array(
					// "id_item"=>$row['id_item'],
					"desc_item"=>$row['desc_item'],
					"local_item"=>$row['local_item'],
					"date_item"=>$row['date_item']
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
	
	
	$getone = new GetOne();
	if(isset($_POST['id_item'])){
		$id_item = $_POST['id_item'];
		if(!empty($id_item)){
			$getone-> getByIdItem($id_item);
		}else{
			$json['error'] = 'id_item is empty';
			echo json_encode($json);
		}
		
	}

?>