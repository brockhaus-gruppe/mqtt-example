#!/usr/bin/python
import socket
import subprocess
import time
import sys 
import datetime

# defining some values
devId = '00-AF-AA-3F'
cont = 'y'
date = datetime.datetime.now().isoformat() #http://www.saltycrane.com/blog/2008/06/how-to-get-current-date-and-time-in/


def do_send(message):
	# Opening socket 
	client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	client_socket.connect(('127.0.0.1', 4000))
	print("Sending from python at: " + date + ": "+ message)
	client_socket.sendall(message)
	client_socket.close()

while cont == 'y':
	time.sleep(1)
	poNumber = raw_input("poNumber: ")
	#message = '{"senderId" : "' + devId + '", "productionOrderId" : "' + poNumber + '" , "date" : ' + str(date) + '"}'
	message = '{"senderId" : "' + devId + '", "productionOrderId" : "' + poNumber + '" }'
	do_send(message)
	cont = raw_input("\ncontinue: [y/n]")
	
	



