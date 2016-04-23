<?php

include_once 'connection.php';
header('Content-Type: application/json');	
	class Delete {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function deleteByIdItem($id_item)
		{
			$query = "DELETE FROM `item` WHERE `item`.`id_item`='$id_item'";
			$inserted = mysqli_query($this -> connection, $query);
			if($inserted == 1 ){
				$json['success'] = 'The item has been deleted';
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$json['error'] = ' Delete fail ';
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$delete = new Delete();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$id_item = $_POST['id_item'];

		if(!empty($id_item)){
			$delete-> deleteByIdItem($id_item);
		}else{
			$json['error'] = 'id_item is empty';
			echo json_encode($json);
		}
		
	}

?>