import analysis
import dat
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


aer = mserror(name,30)
dic = aer.to_dict()
scorelist = dic.values()
scorelist = [0 if math.isnan(x) else x for x in scorelist]

roc_x = [x for x in range(len(scorelist))]
# plt.scatter(roc_x, roc_y)
plt.plot(roc_x, scorelist,'r')
# plt.xlim(150,400)

labellist = labeling(270,330, scorelist)
labellist = relabeling(590,660, labellist)
labellist = relabeling(920,980, labellist)
labellist = relabeling(1340,1410, labellist)
labellist = relabeling(1680,1720, labellist)
labellist = relabeling(1970,2020, labellist)
labellist = relabeling(2280,2320, labellist)
labellist = relabeling(2700,2760, labellist)
labellist = relabeling(3070,3156, labellist)

plt.plot(roc_x, labellist,'b')

plt.ylim(0,20)
plt.legend()
plt.xlabel('time')
plt.ylabel('error')
plt.show()
