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
n4 = int(sys.argv[4])

np.random.seed(42)

#training part
f = open('normallarge.csv')
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

#roc curve
def drawROC(score, y, size):
    roc_x = []
    roc_y = []
    min_score = min(score)
    max_score = max(score)
    thr = np.linspace(min_score, max_score, 30)
    FP=0
    TP=0
    P = sum(y)
    N = len(y) - P
    for (i, T) in enumerate(thr):
        for i in range(0, len(score)):
            if (score[i] > T):
                if (y[i]==1):
                    TP = TP + 1
                if (y[i]==0):
                    FP = FP + 1
        roc_x.append(FP*100/float(N))
        roc_y.append(TP*100/float(P))
        FP=0
        TP=0

    plt.scatter(roc_x, roc_y)
    plt.plot(roc_x, roc_y, label ='Size '+str(size))



scorelist0 = testing('normal1_states.csv',n2, n3)

for i in range(1, 6):
    size = i*10
    scorelist2 = testing('ano3_states.csv',size, n3)
    labellist1 = labeling(180,350, scorelist2)

    drawROC(scorelist2, labellist1, size)
#
plt.xlim(-2, 101)
plt.ylim(-2, 101)
plt.xlabel('FPR')
plt.ylabel('TPR')
plt.legend()
plt.show()
# #
# numb = len(scorelist0)
# #graph
# hstatelist = []
# scorelist2 = testing('ano3_states.csv',n2, n3)
# labellist1 = labeling(180,350, scorelist2)
# for i in range(0, numb):
#     hstatelist.append(i)
#
# plt.plot(hstatelist,scorelist0, label = 'normal1')
# plt.plot(hstatelist,scorelist1, label = 'ano1')
#
# plt.xlabel('HMM Model')
# plt.ylabel('Probability')
# plt.title('HS = ' + str(n1) + ',    WS = ' + str(n2) + ',   Acc = ' + str(acc) + ',    DR = ' + str(dt) + ',    FPA = ' + str(fpr))
# plt.legend()
# plt.show()
