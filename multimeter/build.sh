#!/bin/bash
echo ----------------------------------------------------------------------
echo Building pi4jmultimeter 
echo Clean before
rm -rf mantarget
echo Create directory
mkdir mantarget
echo Copy manifest
cp ./manifest.mf mantarget/
echo Compile
find -name "*.java" > sources.txt
javac -d mantarget @sources.txt
rm sources.txt
cd mantarget
echo Package
jar -cfm pi4jmultimeter.jar manifest.mf mk/* com/* org/*
echo Clean after
rm -rf mk
rm -rf com
rm -rf org
rm -f manifest.mf
cd ..
echo Finished!
echo ----------------------------------------------------------------------

