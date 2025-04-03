<?php 
require "../koneksi.php";

$table = "tb_todo";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $id = $_POST['id'];

    $query = $koneksi->prepare("DELETE FROM $table  WHERE id=?");
    $query->bind_param("i",$id);

    if($query->execute()){
        echo json_encode(['message'=>"List deleted"]);
    }else{
        echo json_encode(['message'=>"Failed delete list"]);
    }
    $query->close();
}