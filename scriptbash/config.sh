#!/bin/bash

DATABASE_NAME=KASUDB;
CONFIG_DB_NAME=config;
USERS_DB_NAME=users;

echo "1 : Change the Mail";
echo "2 : Add an admin";
echo "3 : Remove an admin";
echo "4 : List all the admin mails";
read -p "Which action do you wish to do ? " x;

case $x in
1) 
mongo localhost/$DATABASE_NAME --eval "db.${CONFIG_DB_NAME}.findOne();"
read -p "Enter MAIL ex: kasukasu@gmail.com" mail;
read -p "Enter PASSWORD" pass;
mongo localhost/$DATABASE_NAME --eval "db.${CONFIG_DB_NAME}.update({\"email\":\"$mail\"},{\$set:{\"mail\":\"$mail\"},{\"pass\":\"$pass\"}});"
;;
2)
read -p "Enter the mail of the user" mail;
mongo localhost/$DATABASE_NAME --eval "db.${USERS_DB_NAME}.update({\"email\":\"$mail\"},{\$set:{\"isAdmin\":\"true\"}});"
;;

3)
read -p "Enter the mail of the user" mail;
mongo localhost/$DATABASE_NAME --eval "db.${USERS_DB_NAME}.update({\"email\":\"$mail\"},{\$set:{\"isAdmin\":\"false\"}});"
;;
4)echo "Admins of the web application : ";
mongo localhost/$DATABASE_NAME --eval "db.${CONFIG_DB_NAME}.find({ isAdmin : \"true\"};"
;;
*) echo "Wrong values";
;;
esac
