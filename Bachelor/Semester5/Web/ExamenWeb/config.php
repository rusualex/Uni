<?php
$host = "localhost";
$user = "root";
$pass = "";
$databaseName = "book_library";
$tableName = "produse";


// Create connection
$conn = new mysqli($host, $user, $pass, $databaseName);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
?>