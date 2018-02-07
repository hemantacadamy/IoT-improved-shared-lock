<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','project');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = 'select otp from otpdata where no=(select count(*) from otpdata)';
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('otp'=>$row[0]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>