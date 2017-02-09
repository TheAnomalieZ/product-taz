import analysis
import dat
import csv
import pandas as pd
from pandas import rolling_apply
import numpy as np
import matplotlib.pyplot as plt
import math

def mserror(ts_id,win,**kwargs):
    ts=dat.get_series(ts_id)[:,0]
    tsdf=pd.Series(ts)
    bn=analysis.get_best_net(ts_id)
    mse=lambda win:np.mean(win - bn.predict(np.array(win,dtype='float32')[:,None,None]))
    if win==0: #no window. just return all errors at once
        pr= (bn.predict(ts[:,None,None])[:,0,0]-ts)**2;
        return pr
    return \
        rolling_apply(tsdf,
        win
                      ,mse
                      ,center=True
        )
name ="gcstate"
# wins = [10]
# er=[mserror(name,awin) for awin in wins ]

# for aeri0,aer in enumerate(er):
#     print aeri0
#     print aer.shape
#     dic = aer.to_dict()
#     list1 = dic.values()
#     list2= [list1[x:x+1] for x in xrange(0, len(list1), 1)]
#     # print list1
#     # list = aer.values.tolist()
#     print "mani"
#     file = open("filename.csv", "wb")
#     writer = csv.writer(file)
#     # aer.to_csv(file, sep='\t')
#     writer.writerows(list2)
#     file.close()


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
    print min_score
    print max_score

    thr = np.linspace(min_score, max_score, 50)
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
    plt.plot(roc_x, roc_y, label ='Size '+str(size))


for i in range(10, 100, 10):
    size = i
    aer = mserror(name,i)
    dic = aer.to_dict()
    scorelist = dic.values()
    scorelist = [0 if math.isnan(x) else x for x in scorelist]
    # print scorelist
    # scorelist= [list1[x:x+1] for x in xrange(0, len(list1), 1)]
    labellist = labeling(180,350, scorelist)
    print labellist

    drawROC(scorelist, labellist, i)

plt.xlim(-2, 101)
plt.ylim(-2, 101)
plt.xlabel('FPR')
plt.ylabel('TPR')
plt.legend()
plt.show()
