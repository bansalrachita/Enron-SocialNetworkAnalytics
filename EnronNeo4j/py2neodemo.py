import random

doc_entries = []


people = ["Sirena_Trombly","Larraine_Mahlum","Jeanelle_Munyon","Franchesca_Dulmage",
	  "Chassidy_Chico","Marilyn_Philson","Stephaine_Jeremiah","Alvaro_Stanek",
	  "Andreas_Roundy","Marcella_Eberhart","Anthony_Even","Shin_Fishback",
	  "Shyla_Chu","Daniella_Surratt","Alina_Vidal","Elia_Silveria",
	  "Lilliam_Rady","Doug_Weisbrod","Shani_Lish","Billye_Hitchings"]

#Organizations
orgs = ["Wal_Mart_Stores","Nokia","Audi","PizzaHut",
	"Apple","Phillips","General_Motors","Ford_Motor",
	"General_Electric","Tesla","Airtel","Reliance",
	"Morgan_Stanley","Bloomberg","Factset","EA_Games",
	"Hewlett_Packard","JP_Morgan","Costco","FedEx",
	"Bank_of_America","Cardinal_Health","IBM","Kroger",
	"Nike","Puma","Vodaphone","KingFisher",
	"Wells_Fargo","Boeing"]

# Locations
gpes = ["Alabama","Alaska","Arizona","Arkansas",
	"California","Colorado","Connecticut","Delaware",
	"Florida","Georgia","Hawaii","Idaho",
	"Iowa","Kansas","Kentucky","Louisiana",
	"Maine","Maryland","Massachusetts","Michigan",
	"Minnesota","Mississippi","Missouri","Nevada",
	"Ohio","Oklahoma","Tennessee","Texas",
	"Utah","Vermont","Virginia","Washington",
	"Wisconsin","Wyoming"]

# Document type
docs = ["fraudulent", "fortune_100", "pennywise", "sold", "startup", "broke"]

for doc in docs:
	doc_entries.append({
			   "people": [random.choice(people) for x in range(0,7)],
			   "orgs": [random.choice(orgs) for x in range(0,5)],
			   "gpes": [random.choice(gpes) for x in range(0,4)]
			       })

from py2neo import Graph
graph = Graph("http://neo4j:Neo4j@localhost:7474/db/data/")
graph.cypher.execute("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r") # deleting existing data
create_str = []
create_str+=["({0}:gpe {{ name:'{1}' }})".format(gpe,gpe) for gpe in gpes]
create_str+=["({0}:org {{name:'{1}'}})".format(org,org) for org in orgs]
create_str+=["({0}:person {{name:'{1}'}})".format(person,person) for person in people]
create_str+=["({0}:doc {{name:'{1}'}})".format(doc,doc) for doc in docs]
graph.cypher.execute("create "+",".join(create_str)) #inserting individual entities

for doc_name, entry in zip(docs, doc_entries):
    #associating gepes
    for gpe in entry["gpes"]:
    	graph.cypher.execute('MATCH (n:gpe {{name:"{0}"}}),(d:doc {{name:"{1}"}}) create (n)-[:indoc]->(d)'.format(gpe,doc_name))
    #associating orgs
    for org in entry["orgs"]:
    	graph.cypher.execute('MATCH (n:org {{name:"{0}"}}),(d:doc {{name:"{1}"}}) create (n)-[:indoc]->(d)'.format(org,doc_name))
    #associating people
    for person in entry["people"]:
    	graph.cypher.execute('MATCH (n:person {{name:"{0}"}}),(d:doc {{name:"{1}"}}) create (n)-[:indoc]->(d)'.format(person,doc_name))

