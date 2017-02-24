import numpy as np
from hmmlearn import hmm
import matplotlib.pyplot as plt
import math as mm
import csv as C
import time
from linked_list import LinkedList
from sklearn.externals import joblib
import sys

class HMMModel(object):
    def readData(self, filename):
        f = open(filename)
        try:
            reader = C.reader(f)
            floats = []
            for row in reader:
                floats.append(map(int, row))
        finally:
            f.close()
        return floats

    #training part
    def trainingHMM(self, trainfile, n1):
        f = open(trainfile)
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

        return model

    def prepareData(self, floats, size):
        dataArray = []
        copyData = []
        count = 0
        for data in floats:
            count = count + 1
            if count == size:
                break
            copyData.append(data)

        for data in floats:
            copyData.append(data)
        return copyData


    def testingHMM(self, filename, n, p, model):
        scorelist = []
        f = open(filename)
        try:
            reader = C.reader(f)
            floats = []
            for row in reader:
                floats.append(map(int, row))
        finally:
            f.close()

        datavalues = self.prepareData(floats, n)


        test_data = np.array(datavalues)
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

    def labeling(self, start, end, scorelist):
        labellist = []
        length = len(scorelist)
        for num in range(0,length):
            if num in range(start, end):
                labellist.append(0)
            else:
                labellist.append(1)
        return labellist

    def relabeling(self, start, end, labellist):
        labellistn = []
        count = 0
        for label in labellist:
            count = count + 1
            if count in range(start, end):
                labellistn.append(0)
            else:
                labellistn.append(label)
        return labellistn


    def generateThreshold(self, scorelist, percent):
        avg = 0
        total = 0
        threshold = 0
        for score in scorelist:
            total = total + score

        avg = total/len(scorelist)
        threshold = avg + avg*percent/100
        return threshold

    def tpfpcalculation(self, scorelist, threshold, labellist):
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

    #roc curve
    def drawROC(self, scorelist, labellist, size):
        roc_x = []
        roc_y = []
        min_score = min(scorelist)
        max_score = max(scorelist)
        threshold = np.linspace(min_score, max_score, 30)
        FP=0
        TP=0
        P = sum(labellist)
        N = len(labellist) - P
        for (i, T) in enumerate(threshold):
            for i in range(0, len(scorelist)):
                if (scorelist[i] > T):
                    if (labellist[i]==1):
                        TP = TP + 1
                    if (labellist[i]==0):
                        FP = FP + 1
            roc_x.append(FP*100/float(N))
            roc_y.append(TP*100/float(P))
            FP=0
            TP=0

        plt.scatter(roc_x, roc_y)
        plt.plot(roc_x, roc_y, label ='Size '+str(size))
        return
