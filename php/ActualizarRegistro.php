<?php

    $latitud='"'.$_POST['latitud'].'"';
    $longitud='"'.$_POST['longitud'].'"';
    $nombre='"'.$_POST['ciudad'].'"';

    $ins= "INSERT into `coordenadas` set `latitud`=$latitud,`longitud`=$longitud,`nombre`=$nombre";

    define('DBHost', '127.0.0.1');
    define('DBName', 'basurapp');
    define('DBUser', 'root');
    define('DBPassword', '');
    require("pdo/src/PDO.class.php");
    $DB = new Db(DBHost, DBName, DBUser, DBPassword);
    $rta = $DB->query($ins);
   
 

