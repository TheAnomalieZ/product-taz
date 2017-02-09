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


aer = mserror(name,30)
dic = aer.to_dict()
scorelist = dic.values()
scorelist = [0 if math.isnan(x) else x for x in scorelist]
roc_y = scorelist
roc_x = [x for x in range(len(scorelist))]
plt.scatter(roc_x, roc_y)
plt.plot(roc_x, roc_y)
# plt.xlim(150,400)
plt.legend()
plt.xlabel('time')
plt.ylabel('error')

plt.show()
