#!/bin/bash

read -p "Do you wish to restore the database to a prior version ? YES or NO : " bool;
if [ $bool = "YES" ] || [ $bool = "y" ] || [ $bool = "yes" ] ; then
#Directory of the backups
DATABASE_NAME="KASUDB";
BACKUP_DIR="/home/kasu/backup";
#Initialize the array with the number of directory in the index 0
directory[0]=$(ls -l $BACKUP_DIR | grep -c ^d)
nb=1
echo "Listing of all ${directory[0]} the backups : ";
for i in $(ls -d $BACKUP_DIR/*); do
directory[${nb}]=${i%%/};
echo "$nb : ${directory[$nb]}";
nb=$(($nb+1));
done

read -p "Which backup do you wish to restore ? Enter the number : " number;
if [ $number -lt 1 ] || [ $number -gt ${directory[0]} ] ; then
echo "Wrong Number. Closing script ...";
else
echo "Backup $number : ${directory[$number]} of database $DATABASE_NAME will be restored";
echo "Restoring database ... Please Wait...";
#Command Line to restore the database
sudo mongorestore --db $DATABASE_NAME --drop ${directory[$number]}/$DATABASE_NAME/
echo "End of Restoration ";
fi
else
echo "Closing script ..."
fi