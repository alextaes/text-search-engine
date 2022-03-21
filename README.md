# Adevinta Search Engine

Reads all the text files in the given directory, building an in memory representation of the
files and their contents, and then give a command prompt at which interactive searches can be
performed.

The search should take the words given on the command prompt and return a list of the top 10
(max) matching filenames in rank order, giving the rank score against each match.

## Software
* Java 8 ([JDK not JRE](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html))
* SBT [sbt](https://www.scala-sbt.org/download.html)
* Scala 2.15
* Apache Spark [spark-submit](https://spark.apache.org/downloads.html)

## Install and set up
1. Download and extract the project.
2. From the main folder run the command `sbt clean package`.
3. Inside the folder `/target/scala-2.12` a jar file would have been created by the name `adevinta-search-engine_2.12-1.0.0.jar`.
4. To run the application you must use the `spark-submit` command as it follows:
`spark-submit \
   --class com.adevinta.searchengine.SearchApp \
   {PATH_TO_JAR}/adevinta-search-engine_2.12-1.0.0.jar \
   {PATH_TO_FILES}`

## Things to consider
* The `spark-submit` must receive an argument with the files directory.
* You must enter your search words separated by a blank space.
* Word search is case insensitive.
* To exit the application, text `quit` in the `search>` prompt.

## Example
```
$ spark-submit \
--class com.adevinta.searchengine.SearchApp \
./adevinta-search-engine_2.12-1.0.0.jar \
./files
12 files read in directory: /adevinta/files
search> cats to dogs not
test2.txt : 75%
test1.txt : 50%
test8.txt : 25%
test4.txt : 25%
test3.txt : 25%
test9.txt : 25%
test11.txt : 0%
test5.txt : 0%
test10.txt : 0%
test6.txt : 0%
search> quit
$
```