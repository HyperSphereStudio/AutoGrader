# AutoGrader

To use: Create root directory and create main.c, CMakeLists.txt, and two directories (one to hold the c files, one to hold the grading files)

For instance

root
     main.c
     CMakeLists.txt
     Cfiles/
     GradingFiles/
     
     
To launch: 
    Grab a copy of the autograder.class file from build\classes\java\main\AutoGrader.class and move to the root directory
    Create a batch file with the following information in the root directory
    
    ```batch
       java AutoGrader MyOutputExe.exe MyCFilesDir MyGradedNotesDir
    ```

    Run the batch
    
    
