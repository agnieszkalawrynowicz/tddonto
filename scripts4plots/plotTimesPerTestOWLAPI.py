__author__ = 'agalawrynowicz'

import os
import matplotlib.pyplot as plt
import numpy as np

# Open a file
path = "/Users/lawrynka/Desktop/tonesResults2-owlapi/"
dirs = os.listdir(path)

#adolena.owl;53886000;SubClassOf(<file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#Hospital> <file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#FaceToFaceCommunicationDevice>);1449907994016397000;1449907994028408000;12011000;testSubClassOf;mock;
testSubClassOfMock = []
testSubClassOfSparql = []
testDisjointWithMock = []
testDisjointWithSparql = []
testEquivalentClassesMock = []
testEquivalentClassesSparql = []
testSimpleExistentialQuantificationMock = []
testSimpleExistentialQuantificationSparql = []
testSimpleUniversalQuantificationMock = []
testSimpleUniversalQuantificationSparql = []
testObjectPropertyDomainMock = []
testObjectPropertyDomainSparql = []
testObjectPropertyRangeMock = []
testObjectPropertyRangeSparql = []


# This would print all the files and directories
for filename in dirs:
   print(filename)
   if not(filename.__contains__('DS_Store')):
       f = open(path+filename, 'r')
       for lines in f:
            #print("kuku"+lines)
            tokens = lines.split(';')
            print(tokens)
            if tokens[6]=='testSubClassOf':
                if tokens[7]=='owlapi':
                    testSubClassOfMock.append(tokens[5])
                else:
                    testSubClassOfSparql.append(tokens[5])
            elif tokens[6]=='testDisjointWith':
                if tokens[7]=='owlapi':
                    testDisjointWithMock.append(tokens[5])
                else:
                    testDisjointWithSparql.append(tokens[5])
            elif tokens[6]=='testEquivalentClasses':
                if tokens[7]=='owlapi':
                    testEquivalentClassesMock.append(tokens[5])
                else:
                    testEquivalentClassesSparql.append(tokens[5])
            elif tokens[6]=='testSimpleExistentialQuantification':
                if tokens[7]=='owlapi':
                    testSimpleExistentialQuantificationMock.append(tokens[5])
                else:
                    testSimpleExistentialQuantificationSparql.append(tokens[5])
            elif tokens[6]=='testSimpleUniversalQuantification':
                if tokens[7]=='owlapi':
                    testSimpleUniversalQuantificationMock.append(tokens[5])
                else:
                    testSimpleUniversalQuantificationSparql.append(tokens[5])
            elif tokens[6]=='testObjectPropertyDomain':
                if tokens[7]=='owlapi':
                    testObjectPropertyDomainMock.append(tokens[5])
                else:
                    testObjectPropertyDomainSparql.append(tokens[5])
            elif tokens[6]=='testObjectPropertyRange':
                if tokens[7]=='owlapi':
                    testObjectPropertyRangeMock.append(tokens[5])
                else:
                    testObjectPropertyRangeSparql.append(tokens[5])
       f.close()

for k in testObjectPropertyRangeSparql:
    print(str(k)+"\n")

data = []
for i in range(14):
    data.append([])

for k in testSubClassOfMock:
    data[0].append(float(k)/1000000000)
for k in testSubClassOfSparql:
    data[1].append(float(k)/1000000000)
for k in testDisjointWithMock:
    data[2].append(float(k)/1000000000)
for k in testDisjointWithSparql:
    data[3].append(float(k)/1000000000)
for k in testEquivalentClassesMock:
    data[4].append(float(k)/1000000000)
for k in testEquivalentClassesSparql:
    data[5].append(float(k)/1000000000)
for k in testSimpleExistentialQuantificationMock:
    data[6].append(float(k)/1000000000)
for k in testSimpleExistentialQuantificationSparql:
    data[7].append(float(k)/1000000000)
for k in testSimpleUniversalQuantificationMock:
    data[8].append(float(k)/1000000000)
for k in testSimpleUniversalQuantificationSparql:
    data[9].append(float(k)/1000000000)
for k in testObjectPropertyDomainMock:
    data[10].append(float(k)/1000000000)
for k in testObjectPropertyDomainSparql:
    data[11].append(float(k)/1000000000)
for k in testObjectPropertyRangeMock:
    data[12].append(float(k)/1000000000)
for k in testObjectPropertyRangeSparql:
    data[13].append(float(k)/1000000000)

print(data[6])

#data = [[2,3,8], [4,5,2,7,8], [1,6,7], [7,8], [7,99,1], [7,100,8]]

fig, ax = plt.subplots()

#plt.setp(plt.axes().get_xticklabels(), visible=True)

plt.yscale('log', nonposy='clip')
#plt.yscale(nonposy='clip')
plt.xticks(fontsize=10)

#suptitle('Support of reused ontology fragments')
plt.xlabel('Test Type')
plt.ylabel('Time [s]')
plt.savefig('statsTDDOnto1.png')

plt.subplots_adjust(bottom=0.25)
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 14], ['SubClassOf SPARQL', 'SubClassOf ABox', 'DisjointWith SPARQL', 'DisjointWith ABox', 'EquivalentClasses SPARQL', 'EquivalentClasses ABox', 'SimpleExistentialQuantification', 'SimpleExistentialQuantification SPARQL', 'SimpleExistentialQuantification ABox', 'SimpleUniversalQuantification SPARQL', 'SimpleUniversalQuantification ABox','ObjectPropertyDomain SPARQL', 'ObjectPropertyDomain ABox','ObjectPropertyRange SPARQL', 'ObjectPropertyRange ABox'])
plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], ['SubClassOf OWL API', 'SubClassOf SPARQL', 'Disjoint OWL API', 'Disjoint SPARQL', 'Equivalent OWL API', 'Equivalent SPARQL',  'ExistQuant OWL API', 'ExistQuant SPARQL', 'UnivQuant OWL API', 'UnivQuant SPARQL','PropDomain OWL API', 'PropDomain SPARQL','PropRange OWL API', 'PropRange SPARQL'])
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], ['SubClassOf ABox', 'SubClassOf SPARQL', 'Disjoint ABox', 'Disjoint SPARQL', 'Equivalent ABox', 'Equivalent SPARQL',  'Exist ABox', 'Exist SPARQL', 'Univ ABox', 'UnivQuant SPARQL','PropertyDomain ABox', 'PropertyDomain SPARQL','PropertyRange ABox', 'PropertyRange SPARQL'])
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], ['$C \sqsubseteq D$ ABox', 'SubClassOf SPARQL', 'Disjoint ABox', 'Disjoint SPARQL', 'Equivalent ABox', 'Equivalent SPARQL',  'Exist ABox', 'Exist SPARQL', 'Univ ABox', 'UnivQuant SPARQL','PropertyDomain ABox', 'PropertyDomain SPARQL','ObjectPropertyRange ABox', 'ObjectPropertyRange SPARQL'])


#ax.set_xticks((1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14))
#ax.set_xticklabels(('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k', 'l','m', 'n'))

#plt.axes().set_xticklabels(['SubClassOf SPARQL', 'SubClassOf ABox', 'DisjointWith SPARQL', 'DisjointWith ABox', 'EquivalentClasses SPARQL', 'EquivalentClasses ABox',  'SimpleExistentialQuantification SPARQL', 'SimpleExistentialQuantification ABox', 'SimpleUniversalQuantification SPARQL', 'SimpleUniversalQuantification ABox','ObjectPropertyDomain SPARQL', 'ObjectPropertyDomain ABox','ObjectPropertyRange SPARQL', 'ObjectPropertyRange ABox'])
#plt.xticks(rotation=80)

plt.xticks(rotation='vertical')


#plt.xticks(labels= ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k', 'l','m', 'n'], rotation='vertical')

box = plt.boxplot(data, patch_artist=True)

colors = ['lightblue', 'blue', 'lightblue', 'blue', 'lightblue', 'blue', 'lightblue', 'blue', 'lightblue', 'blue', 'lightblue', 'blue', 'lightblue', 'blue']
for patch, color in zip(box['boxes'], colors):
    patch.set_facecolor(color)

#plt.boxplot(data)


plt.show()


