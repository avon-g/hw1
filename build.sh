#!/bin/bash
LOG="/developer/application/setup.log"
date > $LOG
echo "try to clone project" >> $LOG
rm -rf ./hw1
git clone https://github.com/avon-g/hw1.git
if [  -d ./hw1 ]; then
    echo "project clone done" >> $LOG
else
    echo "clone Error" >> $LOG
    exit -1
fi

cd ./hw1
if [  -f ./sales.txt ]; then
    echo "sales.txt found" >> $LOG
else
    echo "File sales.txt not found. Trying to download" >> $LOG
    wget https://raw.githubusercontent.com/isicju/javacore/lesson1_hm/sales.txt
fi
echo "java compile" >> $LOG
javac -cp ".:jackson-annotations-2.16.0.jar:jackson-core-2.16.0.jar:jackson-databind-2.16.0.jar:log4j-api-2.12.4.jar:log4j-core-2.12.4.jar:" ./*.java
echo "make jar" >> $LOG
jar -cvfm hw1.jar manifest.txt *.class log4j2.properties
#cp ./hw1.jar ..
if [  -f ../hw1.jar ]; then
    echo "build finished OK. Use run.sh to run" >> $LOG
else
    echo "build Error!" >> $LOG
fi
