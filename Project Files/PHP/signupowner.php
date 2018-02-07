<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','project');
 
$con = mysqli_connect(HOST,USER,PASS,DB);

$data=json_decode(file_get_contents('php://input'),true);

print_r($data);

$id=$data['id'];

$name=$data['name'];

$email=$data['email'];

$password=$data['password'];

$contact=$data['contact'];

$lock_no=$data['lock_no'];
 
$sql = "insert into ownerprofile values('$id','$name','$password','$email','$contact','$lock_no')";

$sql1= "insert into logininformation values('$id','$password','owner')";
 
$res = mysqli_query($con,$sql);

$res1 = mysqli_query($con,$sql1);

mysqli_close($con);
?>
	