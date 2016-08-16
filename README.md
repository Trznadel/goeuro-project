## GoEuro project
This is 'Java Developer Test' implementation for GoEuro company. <br />
You can find more details about this task here: https://github.com/goeuro/dev-test <br />
Project is based on Maven. The main class is com.goeuro.Application <br />
To run project please download and run uber jar goeuro-uber.jar and enter cities names, divided by whitespaces e.g. <br />

java -jar goeuro-uber.jar berlin dublin herne <br />

If you don't give any name, then program will ask you directly for it. You can also build uber jar by yourself, by running  normal Maven build. Your uber jar will have name: goeuro-1.0-SNAPSHOT-shaded.jar. <br />
You can specify the CSV file locations by changing 'csvPath' property value in config.properties file, in resources. The  default location is .\target\locations.csv. Default logs location is .\logs\log.txt
