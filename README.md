# Batch Regex File Renamer

## Overview
This program is used to rename all files in a directory (including sub-directories) to match a regex provided by the user. This regex can included a number of file specific attributes (discussed in 

the features section) as well as strings.

![Image](https://raw.githubusercontent.com/Mattie432/Batch-Regex-File-Renamer/master/Images/UserInterface.png)

## Features

* #### Rename files in sub-folders
There is an option to rename files in subfolders as well as files in the root directory, this will rename all files contained in any subfolders inside the root directory.

* #### Regular expression identifiers
Files can be renamed accoring to a regular expression. These expressions can contain a number of attributes of the file being renamed or the directory it is located in. These include:
  1. /d{...} - all directory names upto the root directory.
  2. /d{n} - the nth directory above the file (n is an integer)  
        *Note:* if directory dosent exist then this will be blank in the filename.
  3. /date{curr} - todays date in current timezone format
  4. /date{created} - the date the file was created
  5. /date{modified} - the date the file was last modified
  6. "sometext" - a string of text
       

* #### Realtime feedback
The program will check your regex input in real time and tell you of any errors. It will also provide you with an example of what a renamed file will look like based on an example file.

* #### Operations on the regex identifiers
Operations can be performed on all of the regex identifiers to modify the results. These include:

  1. .replace("str1","str") - replaces all instances of the first string with the second.
  2. .toUpperCase - converts the identifier to upper case.
  3. .toLowerCase - converts the identifier to lower case.
  4. .trim - removes leading and trailing white-space  
  An example: `/date{created}.replace("-","_")`  
  This will replace any "-" in the date string with underscores.



## Updates
If there are any suggestions about additions that could be made to the program please message me about them either here or following the contact links below.


## Links
[Download .jar file](https://github.com/Mattie432/Batch-Regex-File-Renamer/releases)

[Personal website](https://mattie432.com)  
(Contact information can be found here)
