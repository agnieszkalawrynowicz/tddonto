__author__ = 'agalawrynowicz'

import os
import matplotlib.pyplot as plt
import numpy as np

# Open a file
path = "/Users/lawrynka/Desktop/tonesResults-owl1sparql/"
dirs = os.listdir(path)

pathOWL2 = "/Users/lawrynka/Desktop/tonesResults2-sparql/"
dirsOWL2 = os.listdir(pathOWL2)


#adolena.owl;53886000;SubClassOf(<file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#Hospital> <file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#FaceToFaceCommunicationDevice>);1449907994016397000;1449907994028408000;12011000;testSubClassOf;mock;
testSubClassOfOWL1 = []
testSubClassOfOWL2 = []
testDisjointWithOWL1 = []
testDisjointWithOWL2 = []
testEquivalentClassesOWL1 = []
testEquivalentClassesOWL2 = []
testSimpleExistentialQuantificationOWL1 = []
testSimpleExistentialQuantificationOWL2 = []
testSimpleUniversalQuantificationOWL1 = []
testSimpleUniversalQuantificationOWL2 = []
testObjectPropertyDomainOWL1 = []
testObjectPropertyDomainOWL2 = []
testObjectPropertyRangeOWL1 = []
testObjectPropertyRangeOWL2 = []


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
                if tokens[7]=='sparql':
                    testSubClassOfOWL1.append(tokens[5])
            elif tokens[6]=='testDisjointWith':
                if tokens[7]=='sparql':
                    testDisjointWithOWL1.append(tokens[5])
            elif tokens[6]=='testEquivalentClasses':
                if tokens[7]=='sparql':
                    testEquivalentClassesOWL1.append(tokens[5])
            elif tokens[6]=='testSimpleExistentialQuantification':
                if tokens[7]=='sparql':
                    testSimpleExistentialQuantificationOWL1.append(tokens[5])
            elif tokens[6]=='testSimpleUniversalQuantification':
                if tokens[7]=='sparql':
                    testSimpleUniversalQuantificationOWL1.append(tokens[5])
            elif tokens[6]=='testObjectPropertyDomain':
                if tokens[7]=='sparql':
                    testObjectPropertyDomainOWL1.append(tokens[5])
            elif tokens[6]=='testObjectPropertyRange':
                if tokens[7]=='sparql':
                    testObjectPropertyRangeOWL1.append(tokens[5])
       f.close()


for filename in dirsOWL2:
   print(filename)
   if not(filename.__contains__('DS_Store')):
       f = open(pathOWL2+filename, 'r')
       for lines in f:
            #print("kuku"+lines)
            tokens = lines.split(';')
            print(tokens)
            if tokens[6]=='testSubClassOf':
                if tokens[7]=='sparql':
                    testSubClassOfOWL2.append(tokens[5])
            elif tokens[6]=='testDisjointWith':
                if tokens[7]=='sparql':
                    testDisjointWithOWL2.append(tokens[5])
            elif tokens[6]=='testEquivalentClasses':
                if tokens[7]=='sparql':
                    testEquivalentClassesOWL2.append(tokens[5])
            elif tokens[6]=='testSimpleExistentialQuantification':
                if tokens[7]=='sparql':
                    testSimpleExistentialQuantificationOWL2.append(tokens[5])
            elif tokens[6]=='testSimpleUniversalQuantification':
                if tokens[7]=='sparql':
                    testSimpleUniversalQuantificationOWL2.append(tokens[5])
            elif tokens[6]=='testObjectPropertyDomain':
                if tokens[7]=='sparql':
                    testObjectPropertyDomainOWL2.append(tokens[5])
            elif tokens[6]=='testObjectPropertyRange':
                if tokens[7]=='sparql':
                    testObjectPropertyRangeOWL2.append(tokens[5])
       f.close()


for k in testObjectPropertyRangeOWL2:
    print(str(k)+"\n")

owl1all = []
owl2all = []

data = []
for i in range(14):
    data.append([])

for k in testSubClassOfOWL1:
    data[0].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testSubClassOfOWL2:
    data[1].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)
for k in testDisjointWithOWL1:
    data[2].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testDisjointWithOWL2:
    data[3].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)
for k in testEquivalentClassesOWL1:
    data[4].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testEquivalentClassesOWL2:
    data[5].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)
for k in testSimpleExistentialQuantificationOWL1:
    data[6].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testSimpleExistentialQuantificationOWL2:
    data[7].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)
for k in testSimpleUniversalQuantificationOWL1:
    data[8].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testSimpleUniversalQuantificationOWL2:
    data[9].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)
for k in testObjectPropertyDomainOWL1:
    data[10].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testObjectPropertyDomainOWL2:
    data[11].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)
for k in testObjectPropertyRangeOWL1:
    data[12].append(float(k)/1000000000)
    owl1all.append(float(k)/1000000000)
for k in testObjectPropertyRangeOWL2:
    data[13].append(float(k)/1000000000)
    owl2all.append(float(k)/1000000000)

print(data[6])

print('owl1')
for k in owl1all:
    print(k)

print('owl2')
for k in owl2all:
    print(k)


#data = [[2,3,8], [4,5,2,7,8], [1,6,7], [7,8], [7,99,1], [7,100,8]]

fig, ax = plt.subplots()

#plt.setp(plt.axes().get_xticklabels(), visible=True)

plt.yscale('log', nonposy='clip')
#plt.yscale(nonposy='clip')
plt.xticks(fontsize=10)

#suptitle('Support of reused ontology fragments')
plt.xlabel('Language version')
plt.ylabel('Time [s]')
plt.savefig('statsTDDOnto1.png')

plt.subplots_adjust(bottom=0.25)
#plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 14], ['SubClassOf SPARQL', 'SubClassOf ABox', 'DisjointWith SPARQL', 'DisjointWith ABox', 'EquivalentClasses SPARQL', 'EquivalentClasses ABox', 'SimpleExistentialQuantification', 'SimpleExistentialQuantification SPARQL', 'SimpleExistentialQuantification ABox', 'SimpleUniversalQuantification SPARQL', 'SimpleUniversalQuantification ABox','ObjectPropertyDomain SPARQL', 'ObjectPropertyDomain ABox','ObjectPropertyRange SPARQL', 'ObjectPropertyRange ABox'])
plt.xticks([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14], ['SubClassOf OWL', 'SubClassOf OWL2', 'Disjoint OWL', 'Disjoint OWL2', 'Equivalent OWL', 'Equivalent OWL2',  'ExistQuant OWL', 'ExistQuant OWL2', 'UnivQuant OWL', 'UnivQuant OWL2','PropDomain OWL', 'PropDomain OWL2','PropRange OWL', 'PropRange OWL2'])
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


