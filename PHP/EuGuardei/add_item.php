<?php

include_once 'connection.php';
header('Content-Type: application/json');
	class Item {
		
		private $db;
		private $connection;
		
		function __construct() {
			$this -> db = new DB_Connection();
			$this -> connection = $this->db->getConnection();
		}
		
		public function add_item($desc,$local,$date,$id_user,$id_group)
		{	
			//QUERY DATA
			$query = "INSERT INTO `item` (`id_item`, `desc_item`, `local_item`, `date_item`, `id_user`, `id_group`) VALUES (NULL, '$desc', '$local', '$date', '$id_user', '$id_group')";
			//EXECUTE QUERY STORE THE RESULT
			$inserted = mysqli_query($this -> connection, $query);
			if($inserted == 1 ){
				$json['success'] = ' Item inserted successfully ';
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$json['error'] = ' fail insert ';
				echo json_encode($json);
				mysqli_close($this -> connection);
			}
			
		}
		
	}
	
	
	$item = new Item();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$desc = $_POST['desc'];
		$local = $_POST['local'];
		$date = $_POST['date'];
		$id_user = $_POST['id_user'];
		$id_group = $_POST['id_group'];

		
		if(!empty($desc) && !empty($local) && !empty($date) && !empty($id_user)){
			
			$item-> add_item($desc,$local,$date,$id_user,$id_group);

		}else{
			$json['both'] = 'you must type all inputs';
			echo json_encode($json);
		}
		
	}

?>