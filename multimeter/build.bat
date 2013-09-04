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
javac -d mantarget -cp libs\* src\main\java\mk\hsilomedus\multimeter\*.java
cd mantarget
echo Copy libs
copy ..\libs\*.jar .\
echo Package
jar -cfm pi4jmultimeter.jar manifest.mf mk\hsilomedus\multimeter\*.class
echo Clean after
rmdir /S /Q mk
del manifest.mf
cd ..
echo Finished!
echo ----------------------------------------------------------------------
@echo on