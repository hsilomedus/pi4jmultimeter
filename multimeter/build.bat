@echo off

echo ----------------------------------------------------------------------
echo Building pi4jmultimeter 

echo Clean before
rmdir /S /Q mantarget

echo Create directory
mkdir mantarget

echo Copy manifest
copy manifest.mf mantarget\

echo Compile
REM javac -d mantarget -cp libs\* src\main\java\mk\hsilomedus\multimeter\*.java
dir /s /B *.java > sources.txt
javac -d mantarget @sources.txt
del sources.txt

cd mantarget

REM echo Copy libs
REM copy ..\libs\Java-WebSocket-1.3.0.jar .\

echo Package
dir /s /B *.class > sources.txt
jar -cfm pi4jmultimeter.jar manifest.mf mk/* com/* org/*
del sources.txt

echo Clean after
rmdir /S /Q mk
rmdir /S /Q com
rmdir /S /Q org
del manifest.mf

cd ..

echo Finished!
echo ----------------------------------------------------------------------
@echo on