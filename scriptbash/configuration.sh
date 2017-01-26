#!/bin/bash

DATABASE_NAME=KASUDB;
CONFIG_DB_NAME=config;
USERS_DB_NAME=users;

while [ -z $x ] || [ $x != '9' ] ;
do
echo "";
echo "---------------------";
echo "0 : Display the mail configuration";
echo "1 : Change the Mail configuration";
echo "2 : Add an admin";
echo "3 : Remove an admin";
echo "4 : List all the admin mails";
echo "Other : Exit";
echo "--------------------";
echo "";
read -p "Which action do you wish to do ? " x;

case $x in

0)
mongo localhost/$DATABASE_NAME --eval "db.${CONFIG_DB_NAME}.find({email : \"mail\"},{mail:1,pass:1,host:1,port:1}).shellPrint();"
;;
1)
mongo localhost/$DATABASE_NAME --eval "db.${CONFIG_DB_NAME}.find({email : \"mail\"}).shellPrint();"
read -p "Enter MAIL ex: kasukasu@gmail.com :" mail;
read -p "Enter PASSWORD : " pass;
read -p "Enter smtp host ex: smtp.gmail.com : " host;
read -p "Enter smtp port ex: 465 : " port;
mongo localhost/$DATABASE_NAME --eval "db.${CONFIG_DB_NAME}.update({\"email\":\"mail\"},{\$set:{\"mail\":\"$mail\",\"pass\":\"$pass\",\"host\":\"$host\",\"port\":\"$port\"}},{upsert:true});"
;;
2)
read -p "Enter the mail of the user : " mail;
mongo localhost/$DATABASE_NAME --eval "db.${USERS_DB_NAME}.update({\"email\":\"$mail\"},{\$set:{\"isAdmin\":\"true\"}});"
;;

3)
read -p "Enter the mail of the user : " mail;
mongo localhost/$DATABASE_NAME --eval "db.${USERS_DB_NAME}.update({\"email\":\"$mail\"},{\$set:{\"isAdmin\":\"false\"}});"
;;
4)echo "Admins of the web application : ";
mongo localhost/$DATABASE_NAME --eval "db.${USERS_DB_NAME}.find({ isAdmin : \"true\"},{email : 1}).shellPrint();"
echo "";
;;

*)
x=9;
 echo "Exiting Script...";
;;
esac

done
