<?php
require "../koneksi.php";

$result = $koneksi->query("SELECT * FROM tb_todo");
$todoList = [];

while ($row = $result->fetch_assoc()) {
    $todoList[] = $row;
}

echo json_encode($todoList);
?>
