import csv
import pandas as pd
from pandas import rolling_apply
import numpy as np
import matplotlib.pyplot as plt
import math


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
    print min_score[0]
    print max_score[0]

    thr = np.linspace(min_score[0], max_score[0], 100)
    FP=0
    TP=0
    P = sum(y)
    N = len(y) - P
    for (i, T) in enumerate(thr):
        for i in range(0, len(score)):
            if (score[i] < T):
                if (y[i]==1):
                    TP = TP + 1
                if (y[i]==0):
                    FP = FP + 1
        roc_x.append(FP*100/float(N))
        roc_y.append(TP*100/float(P))
        FP=0
        TP=0

    plt.scatter(roc_x, roc_y)
    print roc_y
    area= np.trapz(roc_y,roc_x)/10000
    if area<0:
        area*=-1
    print area
    plt.plot(roc_x, roc_y,  label ='Attribute combination'+str(size)+'  AUC:'+str(area) )


# for i in range(1, 2):
    # f = open('heap-duration-longest/heap_gcpause_longest_results_'+str(i*10)+'_'+str(i)+'_v1.csv')
    # f = open('heap-duration-longest-meta/heap_meta_gcpause_longest_results_'+str(i*10)+'_'+str(i)+'_v2.csv')
    # f = open('heap-duration-meta/heap_gcpause_meta_results_'+str(i*10)+'_'+str(i)+'_v1.csv')
    # f = open('heap_duration_old_gap/v2/heap_duration_old_gap_results_'+str(100+i*10)+'_'+str(i)+'_v2.csv')
    # f = open('../minpoint_test/clustering_attributes_20170219_132644/minPointTest_'+str(390+i*10)+'_20170219_132644.csv')
f = open('../anomaly_scores/anomaly/test2/heap_duration_20170223_151516/anomaly_score_20170223_151516.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 1)

f = open('../anomaly_scores/anomaly/test2/heap_duration_gap_20170223_151518/anomaly_score_20170223_151518.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 2)

f = open('../anomaly_scores/anomaly/test2/heap_duration_meta_20170223_151517/anomaly_score_20170223_151517.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 3)

f = open('../anomaly_scores/anomaly/test2/heap_duration_old_20170223_151516/anomaly_score_20170223_151516.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 4)

f = open('../anomaly_scores/anomaly/test2/heap_duration_old_gap_20170223_151518/anomaly_score_20170223_151518.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 5)

f = open('../anomaly_scores/anomaly/test2/heap_duration_old_meta_20170223_151515/anomaly_score_20170223_151515.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 6)


#
f = open('../anomaly_scores/anomaly/test2/heap_meta_20170223_151517/anomaly_score_20170223_151517.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 7)

f = open('../anomaly_scores/anomaly/test2/heap_old_20170223_151516/anomaly_score_20170223_151516.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
scorelist = floats
labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)

# labellist = labeling(448,574, scorelist)
# labellist = labeling(448,574, scorelist)
# labellist = relabeling(1118,1388, labellist)
# labellist = relabeling(1906,2037, labellist)
# labellist = relabeling(2672,2889, labellist)
# labellist = relabeling(3386,3501, labellist)
# labellist = relabeling(3832,3888, labellist)
# labellist = relabeling(4348,4497, labellist)
# labellist = relabeling(5315,5605, labellist)
# labellist = relabeling(6131,6623, labellist)
print labellist
drawROC(scorelist, labellist, 8)



plt.xlim(-2, 101)
plt.ylim(-2, 101)
plt.xlabel('FPR')
plt.ylabel('TPR')
plt.legend()
plt.show()





