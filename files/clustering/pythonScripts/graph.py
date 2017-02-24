import csv
import pandas as pd
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


# f = open('heap-duration-meta/heap_gcpause_meta_results_10_1_v1.csv')
f = open('../anomaly_scores/heap_duration/anomaly_score_20170223_114602.csv')
try:
    reader = csv.reader(f)
    floats = []
    for row in reader:
        floats.append(map(float, row))
finally:
    f.close()
ylist =floats
xlist =[]
for i in range(0,len(ylist)):
    xlist.append(i)
print len(ylist)
print len(xlist)
# plt.scatter(xlist,ylist,'r')
plt.plot(xlist,ylist,'r')
# plt.ylim(0,2.6949353326644214)
# plt.axhline(y=1.6437148061807538)
scorelist = ylist

# labellist = labeling(280,365, scorelist)
# labellist = labeling(249,326, scorelist)



labellist = labeling(448,574, scorelist)
labellist = relabeling(1118,1388, labellist)
labellist = relabeling(1906,2037, labellist)
labellist = relabeling(2672,2889, labellist)
labellist = relabeling(3386,3501, labellist)
labellist = relabeling(3832,3888, labellist)
labellist = relabeling(4348,4497, labellist)
labellist = relabeling(5315,5605, labellist)
labellist = relabeling(6131,6623, labellist)


# plt.scatter(xlist,labellist,'b')
plt.plot(xlist,labellist,'b')
# plt.xlim(150,400)



# plt.xlim(150,400)
plt.legend()
plt.xlabel('time')
plt.ylabel('error')

plt.show()
