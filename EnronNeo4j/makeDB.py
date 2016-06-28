import sys, random
from py2neo import neo4j, Graph, rel, Node, Relationship
import csv

if len(sys.argv) != 3:
        print ("Parameters required: Nodefile linkFile")
        sys.exit(1)

nodeDataFile, linkDataFile = sys.argv[1:]

with open(nodeDataFile) as f:
	reader = csv.reader(f)
	next(reader, None)
	employees=[tuple(line) for line in reader]

f.close()

with open(linkDataFile) as f:
	reader = csv.reader(f)
	next(reader, None)
	relations=[tuple(line) for line in reader]

f.close()

employee_entries =[]

for link in relations:
	employee_entries.append({"links": link})

graph = Graph("http://neo4j:Neo4j@localhost:7474/db/data/")
graph.cypher.execute("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r") # deleting existing data

#print (employees)
for emp in employees:
	graph.create(Node("Employee", id=emp[0], email=str(emp[1]), group=1))
	#print(emp[0])	

for link in relations:	
	node1 = graph.find_one("Employee", "id", link[0])
	node2 = graph.find_one("Employee", "id", link[1])
	#print(node1)
	graph.create( Relationship( node1, "EMAIL", node2, weight=link[2]))
	
