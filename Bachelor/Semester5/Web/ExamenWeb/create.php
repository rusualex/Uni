<?php
include("config.php");

// Create connection
$conn = new mysqli($host, $user, $pass, $databaseName);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

// sql to create table
$sql = "CREATE TABLE produse (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
descriere VARCHAR(30) NOT NULL,
poze VARCHAR(30) NOT NULL,
)";

if ($conn->query($sql) === TRUE) {
    echo "Table MyGuests created successfully";
} else {
    echo "Error creating table: " . $conn->error;
}

$conn->close();
?>