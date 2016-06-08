___author__ = 'agalawrynowicz'


import os
import matplotlib.pyplot as plt
import numpy as np

clusters1 = []
clusters2 = []
clusters3 = []
clusters4 = []

f = '/Users/lawrynka/Desktop/axiomcounts_owl2_ver2.txt'

with open (f, "r") as inp:
    lines=inp.read().split('\n')
    #lines=inp.read().split(',')
    print(lines)
    for line in lines:
        tokens = line.split(';')
        print('ll'+tokens[0])
        if tokens[2]=='1':
            clusters1.append(tokens[0])
            print('appendowal')
        elif tokens[2]=='2':
            clusters2.append(tokens[0])
        elif tokens[2]=='3':
            clusters3.append(tokens[0])
        elif tokens[2]=='4':
            clusters4.append(tokens[0])

for k in clusters4:
    print("clust:"+str(k)+"\n")

# Open a file
path = "/Users/lawrynka/Desktop/tonesResults2-owlapi/"
dirs = os.listdir(path)

#adolena.owl;53886000;SubClassOf(<file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#Hospital> <file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#FaceToFaceCommunicationDevice>);1449907994016397000;1449907994028408000;12011000;testSubClassOf;mock;
testMock1 = []
testSparql1 = []
testMock2 = []
testSparql2 = []
testMock3 = []
testSparql3 = []
testMock4 = []
testSparql4 = []

testC1 = []
testC2 = []
testC3 = []
testC4 = []


#testSubClassOfMock = []
#testSubClassOfSparql = []
#testDisjointWithMock = []
#testDisjointWithSparql = []
#testEquivalentClassesMock = []
#testEquivalentClassesSparql = []
#testSimpleExistentialQuantificationMock = []
#testSimpleExistentialQuantificationSparql = []
#testSimpleUniversalQuantificationMock = []
#testSimpleUniversalQuantificationSparql = []
#testObjectPropertyDomainMock = []
#testObjectPropertyDomainSparql = []
#testObjectPropertyRangeMock = []
#testObjectPropertyRangeSparql = []


# This would print all the files and directories
for filename in dirs:
#   print(filename)
   if not(filename.__contains__('DS_Store')):
       f = open(path+filename, 'r')
       for lines in f:
            #print("kuku"+lines)
            tokens = lines.split(';')
           # print(tokens)
            if clusters1.__contains__(tokens[0]):
                if tokens[7]=='owlapi':
                    testMock1.append(tokens[5])
                else:
                    testSparql1.append(tokens[5])
                testC1.append(tokens[1])
            elif clusters2.__contains__(tokens[0]):
                if tokens[7]=='owlapi':
                    testMock2.append(tokens[5])
                else:
                    testSparql2.append(tokens[5])
                testC2.append(tokens[1])
            elif clusters3.__contains__(tokens[0]):
                if tokens[7]=='owlapi':
                    testMock3.append(tokens[5])
                else:
                    testSparql3.append(tokens[5])
                testC3.append(tokens[1])
            elif clusters4.__contains__(tokens[0]):
                if tokens[7]=='owlapi':
                    testMock4.append(tokens[5])
                else:
                    testSparql4.append(tokens[5])
                testC4.append(tokens[1])

       f.close()

for k in testSparql4:
    print(str(k)+"\n")

data = []
for i in range(12):
    data.append([])

for k in testC1:
    data[0].append(float(k)/1000000000)
for k in testMock1:
    data[1].append(float(k)/1000000000)
for k in testSparql1:
    data[2].append(float(k)/1000000000)
for k in testC2:
    data[3].append(float(k)/1000000000)
for k in testMock2:
    data[4].append(float(k)/1000000000)
for k in testSparql2:
    data[5].append(float(k)/1000000000)
for k in testC3:
    data[6].append(float(k)/1000000000)
for k in testMock3:
    data[7].append(float(k)/1000000000)
for k in testSparql3:
    data[8].append(float(k)/1000000000)
for k in testC4:
    data[9].append(float(k)/1000000000)
for k in testMock4:
    data[10].append(float(k)/1000000000)
for k in testSparql4:
    data[11].append(float(k)/1000000000)

print(data[6])

#data = [[2,3,8], [4,5,2,7,8], [1,6,7], [7,8], [7,99,1], [7,100,8]]

fig, ax = plt.subplots()

#plt.setp(plt.axes().get_xticklabels(), visible=True)

plt.yscale('log', nonposy='clip')
#plt.yscale(nonposy='clip')
plt.xticks(fontsize=10)

#suptitle('Support of reused ontology fragments')
plt.xlabel('Number of axioms')
plt.ylabel('Time [s]')
plt.savefig('statsTDDOnto3.png')

plt.subplots_adjust(bottom=0.25)
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 14], ['SubClassOf SPARQL', 'SubClassOf ABox', 'DisjointWith SPARQL', 'DisjointWith ABox', 'EquivalentClasses SPARQL', 'EquivalentClasses ABox', 'SimpleExistentialQuantification', 'SimpleExistentialQuantification SPARQL', 'SimpleExistentialQuantification ABox', 'SimpleUniversalQuantification SPARQL', 'SimpleUniversalQuantification ABox','ObjectPropertyDomain SPARQL', 'ObjectPropertyDomain ABox','ObjectPropertyRange SPARQL', 'ObjectPropertyRange ABox'])
plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12], ['0-$10^2$ (Ontology classification)', '0-$10^2$ (OWL API)', '0-$10^2$ (SPARQL)', '$10^2$-$10^3$ (Ontology classification)', '$10^2$-$10^3$ (OWL API)', '$10^2$-$10^3$ (SPARQL)', '$10^3$-$10^4$ (Ontology classification)', '$10^3$-$10^4$ (OWL API)', '$10^3$-$10^4$ (SPARQL)', '$>10^4$ (Ontology Classification)', '$>10^4$ (OWL API)', '$>10^4$ (SPARQL)'])
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], ['SubClassOf ABox', 'SubClassOf SPARQL', 'Disjoint ABox', 'Disjoint SPARQL', 'Equivalent ABox', 'Equivalent SPARQL',  'Exist ABox', 'Exist SPARQL', 'Univ ABox', 'UnivQuant SPARQL','PropertyDomain ABox', 'PropertyDomain SPARQL','PropertyRange ABox', 'PropertyRange SPARQL'])
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], ['$C \sqsubseteq D$ ABox', 'SubClassOf SPARQL', 'Disjoint ABox', 'Disjoint SPARQL', 'Equivalent ABox', 'Equivalent SPARQL',  'Exist ABox', 'Exist SPARQL', 'Univ ABox', 'UnivQuant SPARQL','PropertyDomain ABox', 'PropertyDomain SPARQL','ObjectPropertyRange ABox', 'ObjectPropertyRange SPARQL'])


#ax.set_xticks((1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14))
#ax.set_xticklabels(('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k', 'l','m', 'n'))

#plt.axes().set_xticklabels(['SubClassOf SPARQL', 'SubClassOf ABox', 'DisjointWith SPARQL', 'DisjointWith ABox', 'EquivalentClasses SPARQL', 'EquivalentClasses ABox',  'SimpleExistentialQuantification SPARQL', 'SimpleExistentialQuantification ABox', 'SimpleUniversalQuantification SPARQL', 'SimpleUniversalQuantification ABox','ObjectPropertyDomain SPARQL', 'ObjectPropertyDomain ABox','ObjectPropertyRange SPARQL', 'ObjectPropertyRange ABox'])
#plt.xticks(rotation=80)

plt.xticks(rotation='vertical')


#plt.xticks(labels= ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k', 'l','m', 'n'], rotation='vertical')

box = plt.boxplot(data, patch_artist=True)

colors = ['orange', 'lightblue', 'blue', 'orange', 'lightblue', 'blue', 'orange', 'lightblue', 'blue', 'orange', 'lightblue', 'blue']
for patch, color in zip(box['boxes'], colors):
    patch.set_facecolor(color)

#plt.boxplot(data)


plt.show()



