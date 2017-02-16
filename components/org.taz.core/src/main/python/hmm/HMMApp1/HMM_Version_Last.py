import numpy as np
from hmmlearn import hmm
import matplotlib.pyplot as plt
import math as mm
import csv as C
import time
from linked_list import LinkedList
from sklearn.externals import joblib
import sys

n1 = int(sys.argv[1])
n2 = int(sys.argv[2])
n3 = int(sys.argv[3])
#n4 = int(sys.argv[4])

np.random.seed(42)

#training part
#f = open('normallarge.csv')
f = open('no_big_states.csv')
try:
    reader = C.reader(f)
    floats = []
    for row in reader:
        floats.append(map(int, row))
finally:
    f.close()

train_data = np.array(floats)
model = hmm.MultinomialHMM(n_components=n1)
model.fit(train_data)

def testing(filename, n, p):
    scorelist = []
    f = open(filename)
    try:
        reader = C.reader(f)
        floats = []
        for row in reader:
            floats.append(map(int, row))
    finally:
        f.close()

    test_data = np.array(floats)
    #print test_data
    testlist = LinkedList()
    start = False
    stop = False
    count = 0
    numb1 = 0
    for data in test_data:
        testlist.appendLast(data)
        count = count + 1
        if count == n:
            start = True
        if count == p:
            stop = True
        if start and not stop:
            data_list = []
            data_list = testlist.printList()
            final_list = np.array(data_list)
            #print final_list
            value = model.score(np.array(data_list))
            scorelist.append(value)
            testlist.deleteHead()
            numb1 = numb1 + 1
    return scorelist

def labeling(start, end, scorelist):
    labellist = []
    length = len(scorelist)
    for num in range(0,length):
        if num in range(start, end):
            labellist.append(0)
        else:
            labellist.append(1)
    return labellist

def relabeling(start, end, labellist):
    labellistn = []
    count = 0
    for label in labellist:
        count = count + 1
        if count in range(start, end):
            labellistn.append(0)
        else:
            labellistn.append(label)
    return labellistn

def genThreshold(scorelist, percent):
    avg = 0
    total = 0
    threshold = 0
    for score in scorelist:
        total = total + score

    avg = total/len(scorelist)
    threshold = avg + avg*percent/100
    return threshold

def tpfpcalc(scorelist, threshold, labellist):
    tp = 0
    tn = 0
    fp = 0
    fn = 0
    for score, label in zip(scorelist, labellist):
        if score >= threshold and label == 1:
            tp+=1
        elif score < threshold and label == 1:
            fn+=1
        elif score >= threshold and label == 0:
            fp+=1
        elif score < threshold and label == 0:
            tn+=1

    accuracy = float(tp + tn)/float(tp + fn + fp + tn)
    total = float((tp + fn + fp + tn))
    detectionrate = float(tp)/float(tp + fn)
    falsePR = float(fp)/float(fp + tn)
    #accuracy = tp
    return accuracy, detectionrate, falsePR

scorelist0 = testing('normal_states1.csv',n2, n3)
scorelist1 = testing('anomaly3_states.csv',n2, n3)
#labellist2 = labeling(220,n3, scorelist1)
#labellistn2 = relabeling(0, 30, labellist2)
scorelist2 = testing('anomaly_final_states1.csv',n2, n3)
#labellist1 = labeling(180,350, scorelist2)
#labellistn1 = relabeling(0, 30, labellist1)

#th = genThreshold(scorelist0,n4)
#print th
#acc, dt, fpr = tpfpcalc(scorelist1,th,labellistn2)
#print acc, dt, fpr
numb = len(scorelist0)

#graph
# thline = []
# for i in range(0, numb):
#     thline.append(th)
hstatelist = []
for i in range(0, numb):
    hstatelist.append(i)

plt.plot(hstatelist,scorelist0, label = 'normal_big')
plt.plot(hstatelist,scorelist1, label = 'anomaly3')
plt.plot(hstatelist,scorelist2, label = 'anomaly_final')
#plt.plot(hstatelist,thline, label = 'threshold')

plt.xlabel('HMM Model')
plt.ylabel('Probability')
#plt.title('HS = ' + str(n1) + ',    WS = ' + str(n2) + ',   Acc = ' + str(acc) + ',    DR = ' + str(dt) + ',    FPA = ' + str(fpr))
plt.legend()
plt.show()
