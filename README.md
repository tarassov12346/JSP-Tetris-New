mvn -DsuiteXml=testng-test.xml  clean test

mvn -Dlog4j.configuration=file:C:\JavaProjects\jsp-tetris-new\src\test\resourses\log4j.properties clean test

mvn -Dlog4j.configuration=file:C:\JavaProjects\jsp-tetris-new\src\test\resourses\log4j.properties -DsuiteXml=testng-test.xml clean test