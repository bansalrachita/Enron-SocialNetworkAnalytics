# Introduction
## Centrality
Centrality refers to relative measure of importance of a node in the graph. Centrality measures are important to analyze and find how influential a person is in social networks.  
### Measures of Centrality
-	Degree Centrality
-	Closeness Centrality
-	Betweenness Centrality
-	Eigenvector Centrality

## Degree Centrality

It’s the most intuitive notion of centrality. Node with the highest degree is most important. It gives the index of exposure to what is flowing in the network. Central actor is most likely to hear a gossip.

In a directed graph, it’s given by

	- Inbound centrality = sum of inbounds to a Node
	- Outbound centrality = sum of out-bounds from a Node

## Betweenness Centrality
Betweenness centrality of a node ‘u’ is the ratio of the shortest paths between all other nodes that pass through u.

In a graph, it’s given by:-

 - CB (u) =∑_(s≠v≠t)st(u)/σst) 

CB (u) = Betweenness centrality of node u
σst (u)  = number of shortest paths between (s, t) that passes through u 
σst = number of shortest paths between (s, t)	

## Closeness Centrality
A node is considered important if it is relatively close to all other nodes. It’s a measure of how long it will take to spread information from node ‘u’ to all other nodes. 
Farness is the sum of a node’s distances to all other nodes. 
Closeness is the inverse of Farness. 

It’s given by:-
- CC (u) =1/∑_(v≠u)▒(σ(u,v)) 

## Eigenvector Centrality
It’s the measure of influence of a node in a network. Connections to high scoring nodes contribute more. “An important node is connected to important neighbor” which means a node has high score if connected to many nodes are themselves well connected.   Power iteration is one of the eigenvector algorithm.
## Centralization of Network
Measure of how central its most central node is in relation to how central all other nodes are. Measures the extent to which network revolves around a single node.

It’s given by:-
- CD (u) =∑_(i=1)^n▒(|(Cd(n)-Cd(i))/((N-1)(N-2))|) 
  
## Enron Email Dataset
Enron was founded in 1985. It started as energy business and gas firm which later expanded to other projects becoming the 7th largest business organization in the USA over 15 years. Enron declared bankruptcy in December of 2001 which was followed by several investigations. During the investigations Enron Email Dataset consisting of around 6000,000 emails was made public on the web. The dataset was purchased by Lesie Kaelbling at MIT and was later posted by Prof. William W. Cohen at CMU. 

## Organization of the Dataset
The current version has 517,431 mails organized into 150 folders. The folder name is given as employee last name followed by a hyphen and first letter of the first name. For example, “allen-p” is named after Phillip K. Allen. Each employee folder consists of multiple subfolders, such as, “inbox”, “deleted_items”, “_sent_mail”, “discussion_threads” and others created by the employee. The data is dated from its glory to collapse, Nov. 2008 to June 2002. 

Dataset was downloaded from: https://www.cs.cmu.edu/~./enron/

## Analysis Methodology 
The first step consist of finding the centrality measures like degree, closeness, betweenness and eigenvector centrality to find who has received most emails and who is the most active person. Which pairs are communicating most frequently, this information is important to understand communication patterns. This can also be helpful to cluster people into cohesive subgroups in which people talk to each other more intensively than with outsiders.

In the content analysis, the word count method is used to count the frequency of a bag of keywords in each email. Since these emails are sent by a group of people, the frequency of the bag of keywords can be arrogated on individual level. As a result, each individual has a pattern of word usage. It is interesting to detect relationship between people’s characteristics and their communication frequency. For example, if two people having similar usage of words do they communicate frequently.  

## Properties of Email Data: Data Extracted for Analysis
An Internet email message is divided into two parts the header and body. The header contains structured data namely, From, To, Subject, date and Time. The body contains unstructured data including the content and sometimes a signature.

This information was extracted to establish who talks to who and build a network of all the people who communicate with each other keeping the information such as the sender, receiver, Cc, bcc, subject and first 250 characters of the body. The rest of the information has been ignored or not used in this analysis. 

## Cleaning Email Dataset: Procedure and Problems faced
Like other raw forms of data, the Enron email dataset is noisy and needed to be cleaned. In general the email data had three problems,
>>	Duplicate email addresses exist in the dataset. These are aliases, labeling the same person. Moreover one person can have various emails from domain in addition to his or her organizational one for example, yahoo, Hotmail etc. When people of who send and receive emails are of interest in SNA, mislabeling might lead to incorrect results.

>>	Duplicate email messages exist. For example if A sends an email to B, the mail would be in ‘outbox’ of A and ‘Inbox’ of B. Also, if there are multiple recipients, all of them will have a copy of the email. Duplicate Emails must be removed or word frequency is of concern, also the frequency of communication between the sender and recipients is of concern as well.

>>	Content of the email is difficult to extract. The email content is generally mingled with signature and special characters in the header which take unnecessary space and not a part of the analysis. For example, hyphens in the subject headers.

## Data Analysis using Social Network Analysis Methods
### Centrality Analysis
A series of centrality measures that describe the ‘importance’ of a person are computed and reviewed. Centrality measures how much a node is involved in the network. Involvement includes both sending and receiving mails. Adjacency matrix was used as a graph notation to represent relationship between the nodes which a n x n matrix and each cell is a 0 or 1, in which, 1 represent a link from node in the row to the node in the column.

### Degree Centrality  

Degree of a node in a directional relationship is the In-degree and the Out-degree incident to and from itself to other nodes respectively. Freeman approach was used to determine the degree which considers which calculates links to and from form a node.
A node with high degree centrality signifies more visibility in the network, which is also recognized as an active member and a major channel of information in the network. Since they have many connections, they have many choices in satisfying their needs and hence are less dependent on others.

 ### Top 10 nodes with most In-degree and Out-degree


    - ID	34	33	73	16	76	72	65	35	32	50
	- InDeg	45	41	36	35	34	31	31	30	28	27
	- ID	34	74	33	68	16	58	76	30	114	69
	- OutDeg	78	71	41	38	37	35	33	33	12	30
 
 ### Top 10 nodes having highest closeness

     - ID	34	74	33	16	30	65	58	76	26	68
	- Close	0.7772	0.7488	0.6558	0.6184	0.603 0.6027	0.6002	0.5906	0.5797	0.579
	
### Top five Eigen values in the Enron dataset

    - Eigen Values	20.65	11.12	9.50	9.08	8.52



### Top 10 Nodes having highest Eigenvector centrality
    - ID	34	74	33	30	16	41	39	31	35



