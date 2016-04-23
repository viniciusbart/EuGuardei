<?php

include_once 'connection.php';
header('Content-Type: application/json');	
	class Update {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function updateByIdItem($id_item,$desc,$local,$date)
		{
			$query = "UPDATE `item` SET `desc_item`='$desc', `local_item`='$local', `date_item`='$date' WHERE `item`.`id_item`='$id_item'";
			$inserted = mysqli_query($this -> connection, $query);
			if($inserted == 1 ){
				$json['success'] = 'The item has been updated';
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$json['error'] = ' Update fail ';
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$update = new Update();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$id_item = $_POST['id_item'];
		$desc = $_POST['desc_item'];
		$local = $_POST['local_item'];
		$date = $_POST['date_item'];

		if(!empty($id_item)){
			$update-> updateByIdItem($id_item,$desc,$local,$date);
		}else{
			$json['error'] = 'id_item is empty';
			echo json_encode($json);
		}
		
	}

?>