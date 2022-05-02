## Plagerism CMD Project 


Example Command Line:
```java -jar AutoGrader.jar "C:\Users\JohnB\Documents\GradingEnvironment\Testing\online.c" "C:\Users\JohnB\Documents\GradingEnvironment\CFiles" -o:"output.csv"```

The first argument takes in the file to compare everything against

The second argument takes in the directory of c files to check

The optional o argument takes in where to output the csv to

The optional b argument acts as a base file that subtracts everything from (for instance, if you provided example code)

##




# AutoGrader

To use: Create root directory and create main.c, CMakeLists.txt, and two directories (one to hold the c files, one to hold the grading files)

Required programs:
    CMake
    Java
    Make (Probaly comes with CMake)

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
    
    
