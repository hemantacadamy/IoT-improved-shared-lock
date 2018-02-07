<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','project');
 
$con = mysqli_connect(HOST,USER,PASS,DB);

$data=json_decode(file_get_contents('php://input'),true);

print_r($data);
 
$sql = "select * from ownerprofile";//track
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('id'=>$row[0],
'name'=>$row[1],
'email'=>$row[2],
'password'=>$row[3],
'contact'=>$row[4],
'lock_no'=>$row[5]
));
}
 
echo json_encode(array("result"=>$result));
 

mysqli_close($con);
?>
	