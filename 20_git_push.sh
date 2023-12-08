#!/bin/bash
git add *.bat
git add *.sh
git add hw1.iml
git add jackson-annotations-2.16.0.jar
git add jackson-core-2.16.0.jar
git add jackson-databind-2.16.0.jar
git add log4j-api-2.12.4.jar
git add log4j-core-2.12.4.jar
git add log4j2.properties
git add Main.java
git add manifest.txt
git add Sales.java
git add Seller.java
git add Sellers.java
git add SendMail.java

git commit -m "hosting1"
git branch -M main
git remote add origin https://github.com/avon-g/hw1.git
git push -u origin main