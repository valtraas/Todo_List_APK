<?php
require "../koneksi.php";

if($_SERVER['REQUEST_METHOD'] === 'POST'){
    $id = $_POST['id'];
    $status = $_POST['status'];
    $isComplete = $_POST['is_complete'];
    $table = "tb_todo";

    $query = "UPDATE $table SET status = ?, is_complete =? WHERE id = ?";
    $stmt = $koneksi->prepare($query);
    $stmt->bind_param("sii",$status,$isComplete,$id);

    if($stmt->execute()){
        echo json_encode(["message"=>"Status updated"]);
    }else{
        echo json_encode(["error"=>"update failed"]);
    }
    $stmt->close();
}else{
    echo json_encode(["error"=>"Invalid request"]);

}