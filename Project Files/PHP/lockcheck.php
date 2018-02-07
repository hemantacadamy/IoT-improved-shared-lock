<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','project');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = 'select * from locks';
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('lock_no'=>$row[0],
'isbn_no'=>$row[1]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>