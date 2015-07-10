#!/usr/bin/python
import subprocess
import time
import sys 
import datetime

#poNumber = 'PO-1234567ABCD'
devId = 'AAA111222333'
cont = 'y'
date = datetime.datetime.now() #http://www.saltycrane.com/blog/2008/06/how-to-get-current-date-and-time-in/

# for python 2 java communication see:
# https://docs.python.org/2/library/ipc.html
# http://stackoverflow.com/questions/14762048/subprocess-call-in-python-to-invoke-java-jar-files-with-java-opts

# Opening pipe using the tiny java class
#p = subprocess.Popen(["java", "NFCReceiver"], stdin=subprocess.PIPE)

# Opening pipe using the executable jar
p = subprocess.Popen(["java", '-jar', 'mqtt-example-0.0.1-SNAPSHOT.jar'], stdin=subprocess.PIPE)

def do_send(message):	
	print("Sending from python: "+ message)
	p.stdin.write(message)
	p.stdin.flush()

while cont == 'y':
	time.sleep(1)
	poNumber = raw_input("poNumber: ")
	# message = '{"senderId" : "' + devId + '", "productionOrderId" : "' + poNumber + '" , "date" : ' + str(date) + '"}'
	message = '{"senderId" : "' + devId + '", "productionOrderId" : "' + poNumber + '" }'
	do_send(message)
	cont = raw_input("\ncontinue: [y/n]")
	
	



