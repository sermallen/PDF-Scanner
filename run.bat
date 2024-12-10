@echo off
javac -d bin -cp lib\pdfbox-app-3.0.3.jar src\*.java
java -cp bin;lib\pdfbox-app-3.0.3.jar PDF_Scanner %1