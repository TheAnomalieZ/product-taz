import analysis
import sys
import csv
import pandas as pd
from pandas import rolling_apply
import numpy as np
import matplotlib.pyplot as plt
import math

filepath = str(sys.argv[1])
id       = str(sys.argv[2])

def getSeries(*args,**kwargs):
    kwargs.setdefault('dtype','float32')
    print filepath
    return np.loadtxt(filepath)[::,None]


def mserror(win,**kwargs):
    ts=getSeries()[:,0]
    tsdf=pd.Series(ts)
    bn=analysis.get_best_net(id)
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

aer = mserror(30)
print aer
print aer.size

# scorelist = aer
# scorelist = [0 if math.isnan(x) else x for x in scorelist]



dic = aer.to_dict()
scorelist = dic.values()
scorelist = [0 if math.isnan(x) else x for x in scorelist]
scorelist= [scorelist[x:x+1] for x in xrange(0, len(scorelist), 1)]

file = open(filepath+"_ae_score.csv", "wb")
writer = csv.writer(file)
writer.writerows(scorelist)
file.close()

pct=5
el=np.percentile(scorelist,90+pct)
print ("threshold: "+str(el))
file = open(filepath+"_threshold.csv", "wb")
writer = csv.writer(file)
writer.writerow([el])
file.close()