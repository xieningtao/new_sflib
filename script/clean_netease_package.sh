#!/bin/bash
output=$(adb  -s $1 shell pm list package|grep "com.netease"|sed "s/package://g")
arr=(${output// / })  
echo "start uninstall package..."
for package in ${arr[@]}  
do  
	echo "start uninstall package $package"
    adb -s  $1 uninstall $package
done  
echo "finish"