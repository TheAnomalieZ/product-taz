import analysis
import dat
import csv
import pandas as pd
from pandas import rolling_apply
import numpy as np
import matplotlib.pyplot as plt
import math
from sklearn.metrics import roc_curve, auc


def mserror(ts_id,win,**kwargs):
    ts=dat.get_series(ts_id)[:,0]
    tsdf=pd.Series(ts)
    bn=analysis.get_best_net(ts_id)
    mse=lambda win:np.mean(win - bn.predict(np.array(win,dtype='float32')[:,None,None]))**2
    if win==0: #no window. just return all errors at once
        pr= (bn.predict(ts[:,None,None])[:,0,0]-ts)**2;
        return pr
    return \
        rolling_apply(tsdf,
                      win
                      ,mse
                      ,center=True
                      )
name ="App1_gctime"
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


for i in range(0, 100, 10):
    size = i
    aer = mserror(name,i)

    print aer.size
    # aer =np.array( mserror(name,i), dtype=pd.Series)

    # dic = aer.to_dict()
    # scorelist = aer.values.tolist()
    scorelist = aer
    scorelist = [0 if math.isnan(x) else x for x in scorelist]
    print scorelist
    labellist = labeling(270,330, scorelist)
    labellist = relabeling(590,660, labellist)
    labellist = relabeling(920,980, labellist)
    labellist = relabeling(1340,1410, labellist)
    labellist = relabeling(1680,1720, labellist)
    labellist = relabeling(1970,2020, labellist)
    labellist = relabeling(2280,2320, labellist)
    labellist = relabeling(2700,2760, labellist)
    labellist = relabeling(3070,3156, labellist)
    print labellist
    false_positive_rate, true_positive_rate, thresholds = roc_curve(scorelist, labellist,pos_label=1)
    roc_auc = auc(false_positive_rate, true_positive_rate)
    print false_positive_rate
    print true_positive_rate
    plt.scatter(false_positive_rate, true_positive_rate)
    plt.plot(false_positive_rate, true_positive_rate, 'b',
             label=' AUC = %0.2f'% roc_auc)



plt.title('Receiver Operating Characteristic')

plt.legend(loc='lower right')
plt.plot([0,1],[0,1],'r--')
plt.xlim([-0.1,1.1])
plt.ylim([-0.1,1.1])
plt.ylabel('True Positive Rate')
plt.xlabel('False Positive Rate')
plt.show()

