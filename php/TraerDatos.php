<?php

//if(isset($_POST['submit'])){

    //$latitud='"'.$_POST['latitud'].'"';
    //$longitud='"'.$_POST['longitud'].'"';
    //$nombre='"'.$_POST['ciudad'].'"';
    /*$ciudad='"'.$_GET['ciudad'].'"';
    $pais='"'.$_GET['pais'].'"';
    $urlfoto='"'.$_GET['urlfoto'].'"';
    $id='"'.$_GET['id'].'"';*/

    

   // $ins= "INSERT into `coordenadas` set `latitud`=$latitud,`longitud`=$longitud,`nombre`=$nombre";
    //echo $ins;

    $insmostar= 'SELECT latitud,longitud FROM coordenadas ORDER by id DESC LIMIT 1 ';
    //echo $insmostar;


    //echo $ins;
    define('DBHost', '127.0.0.1');
    define('DBName', 'basurapp');
    define('DBUser', 'root');
    define('DBPassword', '');
    require("pdo/src/PDO.class.php");
    $DB = new Db(DBHost, DBName, DBUser, DBPassword);
    $rta = $DB->query($insmostar);
   
    
   // $id = $DB->lastInsertId();
     
    echo json_encode($rta[0]);




//}




