<?php
require "../koneksi.php";

$table = "tb_todo";

if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $todo = $_POST['todo'];
    $status = $_POST['status'];
    $isComplete = $_POST['is_complete'];

    $query = $koneksi->prepare("INSERT INTO $table (todo,status,is_complete) VALUES (?,?,?)");
    $query->bind_param("ssi", $todo,$status,$isComplete); 

    if ($query->execute()) {
        echo json_encode(['message' => "Successfully added new list"]);
    } else {
        echo json_encode(['message' => "Failed added new list"]);
    }
    $query->close();
}
