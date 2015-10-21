#!/usr/bin/python

# ------------------------------------------------
# Test VOSpace service python script
# ------------------------------------------------

import requests, json, getpass, zipfile, io, time
import xml.dom.minidom
from io import StringIO
from io import BytesIO
from xml.dom.minidom import parse

# Request password from command-line
user = input('User: ');
pssd = getpass.getpass('Password: ')
base_url = input('Host: ');

#node_url: url to ask for nodes
node_url = base_url + "servlet/nodes/" + user
#transfer_url: url to ask for transfer in asynchronous way
transfer_url = base_url + "servlet/transfers/async"
#test_path is the path to the xml files to send to VOSpace
test_path = "/"
#node_a: relative path to container A
node_a = "/A"
#node_b: relative path to container B
node_b = "/B"

#Removing A
print ("Removing Container A.............................................")
delete_req = requests.delete(node_url + node_a, auth=(user, pssd))
if delete_req.ok:
	print (delete_req.text)
	print ("Done, Ok.........................................................");
print ("")

# Creating Container A
print ("Creating container A.............................................")
create_input = open(test_path + 'container_a.xml'); #change for your own xml file name
create_req = requests.put(node_url + node_a, create_input, auth=(user, pssd));
if create_req.ok:
	print (create_req.text)
	print ("Done, Ok.........................................................")
print ("")

#Creating Container B
print ("Creating container B.............................................")
create_input = open(test_path + 'container_b.xml'); #change for your own xml file name
create_req = requests.put(node_url + node_b, create_input, auth=(user, pssd));
if create_req.ok:
	print (create_req.text)
	print ("Done, Ok.........................................................")
print ("")

#Querying A
print ("Querying container A.............................................")
query_request = requests.get(node_url + node_a, auth=(user, pssd))
if query_request.ok:
	print (query_request.text)
	print ("Done, Ok.........................................................");
print ("")

#Querying B
print ("Querying container B.............................................")
query_request = requests.get(node_url + node_b, auth=(user, pssd))
if query_request.ok:
	print (query_request.text)
	print ("Done, Ok.........................................................");
print ("")

#Move B into A -> A/B
print ("Moving B into A..................................................")
filename = 'transfer_b_to_a.xml' #change for your own xml file name
filepath = test_path + filename
files = {'file': (filename, open(filepath, 'rb'))}
move_request = requests.post(transfer_url + "?PHASE=RUN", files=files, auth=(user, pssd))
if move_request.ok:
	print (move_request.text)
	print ("Done, Ok.........................................................");
print (move_request.text)

time.sleep(10) #wait till the job is completed

#Querying A
print ("Querying container A.............................................")
query_request = requests.get(node_url + node_a, auth=(user, pssd))
if query_request.ok:
	print (query_request.text)
	print ("Done, Ok.........................................................");
print ("")

#Sharing A with someone
print ("Sharing container A..............................................")
sharing_input = open(test_path + 'container_a_modified.xml') #change for your own xml file name
share_req = requests.post(node_url + node_a, sharing_input, auth=(user, pssd))
if share_req.ok:
	print (share_req.text)
	print ("Done, Ok.........................................................");
print ("");

#Upload file to A/xxx
print ("Uploading file into A..................................................")
filename = 'transfer_push_to_a.xml' #change for your own xml file name
filepath = test_path + filename
files = {'file': (filename, open(filepath, 'rb'))}
upload_request = requests.post(transfer_url + "?PHASE=RUN", files=files, auth=(user, pssd))
if upload_request.ok:
	print (upload_request.text)
	print ("Done, Ok.........................................................");

time.sleep(3) #wait till the job is completed

# Open XML document using minidom parser
DOMTree = xml.dom.minidom.parseString(upload_request.text)
collection = DOMTree.documentElement

jobId_element = collection.getElementsByTagName('uws:jobId')[0]
jobId = jobId_element.childNodes[0].data

print ("%s" % jobId)
upload_file = {'file': open(test_path + 'test.zip', 'rb')} #change for your own file name to be uploaded
upload_url = base_url + "servlet/data/" + user + "/" + jobId
upload_post = requests.post(upload_url, files=upload_file, auth=(user, pssd))
if upload_post.ok:
	print ("Done, Ok.........................................................");

print ("Deleting jobId from the list of jobs.............................")
upload_del_url = transfer_url + "/" + jobId
delete_post = requests.delete(upload_del_url, auth=(user, pssd))
if delete_post.ok:
	print (upload_request.text)
	print ("Done, Ok.........................................................");

#Download Container A
print ("Downloading file into A................................................")
filename = 'transfer_download_a.xml' #change for your own xml file name
filepath = test_path + filename
files = {'file': (filename, open(filepath, 'rb'))}
download_request = requests.post(transfer_url + "?PHASE=RUN", files=files, auth=(user, pssd))
if download_request.ok:
        print (download_request.text)
        print ("Done, Ok.........................................................");

# Open XML document using minidom parser
DOMTree = xml.dom.minidom.parseString(download_request.text)
collection = DOMTree.documentElement
jobId_element = collection.getElementsByTagName('uws:jobId')[0]
jobId = jobId_element.childNodes[0].data

time.sleep(5); #wait till the job is completed

#save content to file
download_url = base_url + "servlet/data/" + user + "/" + jobId
download_get = requests.get(download_url, auth=(user, pssd), stream=True)
if download_get.ok:
	f = open("/home/snieto/Desktop/VOSpace/python/test.zip", "wb") #change for your own file to be downloaded
	f.write(download_get.content)
	f.close()
	print ("Done, Ok.........................................................");

time.sleep(5) #wait till the job is completed

print ("Deleting jobId from the list of jobs.............................")
upload_del_url = transfer_url + "/"  +  jobId
delete_post = requests.delete(upload_del_url, auth=(user, pssd))
if delete_post.ok:
	print (upload_request.text)
	print ("Done, Ok.........................................................");

