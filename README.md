# BiBiCWL
BiBiCWL is a tool for converting BiBiApps (BiBiServ applications) into CWL Command Line Tools.

## Compiling the project

The project can be compiled using Gradle.

```
gradle build
```

The two executable gradlew and gradlew.bat which are shipped with the project can be used if Gradle is not installed on the system. (I did not test this on other systems.)

An artifact of the project will be placed in the build/libs/ directory.


## Running BiBiCWL

You can run BiBiCWL as a command line application or as a GUI application.

To start the GUI use the command:
```
java -jar bibicwl.jar -g
```

In order to use BiBiCWL as a command line application the following options are available:

```
-i --input          Path to the BiBiApp tool description. (required)
-o --output         Path to the output directory. (required)
-q --noShellQuote   suppress the use of shellQuotes in the CWL CommandlineTool
-p --optionalInput  With this option enabled, InputFiles will be flagged as
                    optional, so that they don't have to be specified in the
                    CWL job file. However, the underlying program might still
                    require those inputs and running the program without them
                    might result in an error.
-a --arrayFileInputs  If this option is used, file inputs of the generated CWLTool
                    will be set to be array inputs in order to allow multiple
                    input files for one input field.
                    Currently there is no equivalent in the BiBiApp tool
                    description so that this option would have to be ticked
                    manually.
                    This option is only applied to inputs if their ID has the 
                    suffix "_array", "_zip" or "_list" because otherwise other
                    inputs in the CWL Tool would be affected, too.
-s --itemSeparator  The itemSeparator is used together with the arrayFileInputs
                    option. This option allows for the definition of a string
                    which separates the elements of an arrayFileInput.
```

## Examples

Example BiBiAppToolDescriptions can be found in the testsuite/bibiapp_tool_description/ directory.
Their converted CWL counterparts can be found in the testsuite/cwlTools/ directory.
The 16S rRNA analysis pipeline which was created as a proof of concept for the modularity CWL offers, can be found in the testsuite/cwlWorkflows/ directory.
As a starting point, job files are included, too. However, the file paths specified in them have to be changed.
