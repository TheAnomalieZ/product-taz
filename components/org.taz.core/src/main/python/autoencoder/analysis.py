import pandas as pd
import numpy as np
import os

import config as configmod
from config import config
# get data from db

from spearmint.utils.database.mongodb import MongoDB
mdb=MongoDB(config['rnndb']) #samd db as rnns

def get_runs(xpnm):
    # print mdb
    jobs=mdb.load(xpnm,'jobs',{'status':'complete'})
    try: jobs[0]
    except KeyError: jobs=[jobs]
    finally: params=jobs[0]['params'].keys()

    data=[]
    for ajb in jobs:
        arow=[]
        for ap in params:
            dd=ajb['params'][ap]['values'][0]
            dt=ajb['params'][ap]['type'][0]
            arow.append(np.array(dd,dtype=dt))
        arow.append(ajb['values']['main'])
        arow.append((ajb['id']))
        data.append(tuple(arow))

    columns=params[:]
    columns.append('o')
    columns.append('run_id')
    runs=pd.DataFrame(data=data,columns=columns)
    # print runs
    return runs


def get_best_params(xpnm):
    #todo what if muliple nets with same 'params'?
    runs=get_runs(xpnm)
    bp=dict(runs.ix[runs['o'].idxmin()])
    bp.pop('o')
    best_params={}
    for ap in (bp):
        try: #chk for number
            bp[ap]/1.0
            best_params[ap]=float(bp[ap])
        except:
            best_params[ap]=bp[ap]
    return best_params

import rnndb
import omain
def get_best_net(xpnm):
    #assert(len(list(tbl.find(**best_params)))==1)
    params=get_best_params(xpnm)
    params['iter']=omain.itermap(params['iter'])
    return rnndb.get_net(xpnm,params)


#ts=slidingwin size= step=
import sklearn.metrics as metrics
def get_errs(wints,net):
    p=net.predict(wints)
    errs=[]
    for i in xrange(wints.shape[0]):
        errs.append(metrics.mean_squared_error(wints[i,:,0],p[i])**1 )
    return errs


from pandas import rolling_apply
import dat
def errs(ts_id,win,**kwargs):
    ts=dat.get_series(ts_id)[:,0]
    tsdf=pd.Series(ts)
    bn=get_best_net(ts_id)
    mse=lambda win:np.mean(win - bn.predict(np.array(win,dtype='float32')[:,None,None]))
    if win==0: #no window. just return all errors at once
        pr= (bn.predict(ts[:,None,None])[:,0,0]-ts)**2;
        return pr
    return \
        rolling_apply(tsdf
                      ,win
                      ,mse
                      ,center=True
        )


#bodiag rng nl and n
def bo_diag(ts_id):
    d=get_runs(ts_id)
    for ar in d['run_id']:
        if 'patience elapsed' in get_log(ts_id,ar):
            ri=d[d['run_id']==ar].index;
            if  d.loc[ri,'iter'].any()==1: pass
            else:
                if 'patience elapsed' in str(get_log(ts_id,ar)):
                    d.loc[ri,'iter']=1
                else: pass


    # print d
    # print "\nbefore"
    d=d[d['iter']==1] #just get the ones that i'm sure patience elapsed
    d=d[d.columns.drop(['run_id','iter'])] #no need
    # still has objects instead of elems of a dtype
    d['n']=np.array(d['n'],dtype=np.int)
    d['nl']=np.array(d['nl'],dtype=np.int)
    d=d.sort_values(by=['nl','n'])
    print d
    return d


def get_log(ts_id,run_id):
    run_id=int(run_id)
    thisdir=os.path.split(os.path.abspath(configmod.__file__))[0]
    run_id=str(run_id)
    fn= '0'*(8-len(run_id))+run_id+'.out'
    fn= (os.path.join(thisdir,'experiments',ts_id,'output',fn))
    return open(fn).readlines()



def get_epocherr(ts_id,run_id):
    v=[]; t=[]
    for al in get_log(ts_id,run_id):
        if 'loss=' in al:
            if 'validation' in al:
                erri=al.find('err=')
                lssi=al.find('loss=')
                v.append(float(al[lssi+len('loss='):erri-1]))
            elif 'loss=' in al: #training loss
                erri=al.find('err=')
                lssi=al.find('loss=')
                t.append(float(al[lssi+len('loss='):erri-1]))
    v=v[1:] #chop off first validation
    # assert len(v)==len(t)
    return {'vld':v,'trn':t}
