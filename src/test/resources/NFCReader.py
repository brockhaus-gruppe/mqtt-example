#!/usr/bin/python

import nxppy
import time
import socket

mac = open('/sys/class/net/eth0/address').read()
mifare = nxppy.Mifare()

# function to write the data in the NFC tag. The info is saved from the block 15 on.
def write_tag(info):
	list = [info[i:i+4] for i in range(0, len(info),4)]
	for i in range(0, len(list)):
		mifare.write_block(15+i,list[i])

# function to read the data written in the NFC tag
def read_tag():
	print(mifare.read_block(15) + mifare.read_block(19))

def do_send(message):
	# Opening socket 
	client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	client_socket.connect(('127.0.0.1', 4000))
	print("Sending from python: "+ message)
	client_socket.sendall(message)
	client_socket.close()

# Print card UIDs as they are detected
while True:
    try:
        devId = mifare.select()
        print("Card UID: " + devId)
	
	print("Reading 4 blocks (4 bytes each one) from block #6")
	block4Bytes = mifare.read_block(6)	
	poNumber = block4Bytes.decode("ascii")[1:]
	print("Card Info on Block 6: "+ poNumber)
	print("MAC address: " + mac)
	write_tag(mac)
	read_tag()
	

        message = '{"senderId" : "' + devId + '", "productionOrderId" : "' + poNumber + '" }'
        do_send(message)    
        

    except nxppy.SelectError:
        # SelectError is raised if no card is in the field.
        pass

    time.sleep(1)


