# from pudb import set_trace; set_trace()

import os

import numpy as np
import scipy as sp

import seaborn as sns
import matplotlib
import matplotlib.pyplot as plt

from math import sqrt

registry={}
def register(figcls):
    registry[figcls.__name__]=figcls
    return figcls


class fig(object):
    name=None
    def __init__(self):
        if self.name==None:
            nmi=self.__class__.__name__.find('_')
            self.name=self.__class__.__name__[nmi+1:]

    def data(self):pass
    def plot(self): plt.close();
    def style(self):pass
    def format(self):pass
    def save(self):
        self.plot().figure.savefig(self.path())
    def path(self):
        pth=os.path.join(''
                         ,self.__class__.__name__+'.png'
        )
        return pth




# RECONSTRUCTION ERROR

import matplotlib.ticker as ticker

class sharexaxis(fig):
    yc=['b','g']
    yl=['y1','y2']
    yms=['.','.']
    yls=['-','-']; ylsd=['solid','solid']
    xl=None;xu=None #none: lim as is


    def style(self,po):
        plt.setp(po,linewidth=1.5)
        # po.axes.get_xaxis().set_ticklabels([])
        # po.axes.get_yaxis().set_ticklabels([])
        return po


    def plot(self):
        fig().plot(); self.format()
        fg,ax=plt.subplots(1+len(self.wins),1,sharex=True)

        dataall=self.data();

        ax2= self.style(   ax[0].plot( dataall[0]
                                       ,linestyle=self.yls[0]
                                       ,color=self.yc[0])[0] )
        ax[0].yaxis.set_major_locator(ticker.MaxNLocator(nbins=4))
        ax[0].xaxis.set_major_locator(ticker.MaxNLocator(integer=True
                                                         #,nbins=10
        ))
        ax[-1].get_xaxis().set_label_text('$t$')
        ax[0].get_yaxis().set_label_text(self.yl[0])
        xd=list(ax[0].get_xlim())
        if self.xl!=None: xd[0]=self.xl
        if self.xu!=None: xd[1]=self.xu

        #kde of ts
        yed=ax[0].lines[0].get_ydata()
        kdexs=np.linspace(min(yed)#-.05*(max(yed)-min(yed)) #a lil less than 0
                          ,max(yed)
                          ,300)
        kdef=sp.stats.gaussian_kde(yed)
        kde=kdef(kdexs)
        kde[0]=min(yed) # i'm not lying!
        kde=kde/max(kde)*.1*(xd[1]-xd[0])#make dist 10% of x axis
        kde=kde+xd[0] #shift to start
        ax[0].plot(kde,kdexs,linewidth=1.5,color='green')

        ax[0].set_xlim(xd); ax[1].set_xlim(xd)
        ax[0].set_ylim(
                min(    dataall[0][xd[0]:xd[1]]    )
                ,max(    dataall[0][xd[0]:xd[1]]    )
            )

        for aeri0,aer in enumerate(dataall[1]):
            data=dataall[0],aer
            aeri=aeri0+1

            ret= self.style( ax[aeri].plot(data[1]
                                        ,linestyle=self.yls[1]
                                        ,color=self.yc[1])[0]  )
            ax[aeri].yaxis.set_major_locator(ticker.LinearLocator(numticks=2))
            ax[aeri].get_yaxis().set_label_text(self.yl[1])
            yed=ax[aeri].lines[0].get_ydata()
            yed=yed[~np.isnan(yed)];
            eax=ret.axes#.twinx(); eax.axes.get_yaxis().set_ticklabels([])
            eax.set_xlim(xd)
            kdexs=np.linspace(min(yed)#-.05*(max(yed)-min(yed)) #a lil less than 0
                              ,max(yed)
                              ,300)
            kdef=sp.stats.gaussian_kde(yed)
            kde=kdef(kdexs)
            kde[0]=min(yed) # i'm not lying!
            kde=kde/max(kde)*.1*(xd[1]-xd[0])#make dist 10% of x axis
            kde=kde+xd[0] #shift to start
            eax.plot(kde,kdexs,linewidth=1.5,color='darkred')

            # show the max err point #
            ymxd=ax[aeri].lines[0].get_ydata()
            ymxd[np.isnan(ymxd)]=min(yed)


            # log scale if num diff too big
            if False:#wrongmax(ymxd[xd[0]:xd[1]])/min(ymxd[xd[0]:xd[1]])>10:
                ax[aeri].set_yscale('log')
                ax[aeri].yaxis.set_major_locator(ticker.MaxNLocator(nbins=3
                                                               ,prune='both'))

            yt= ax[aeri].get_yticks()
            yt[-1]=max(ymxd)
            ax[aeri].set_yticks(yt)
            yt= ax[aeri].get_yticks().tolist()
            yt[-1]='max';
            ax[aeri].set_yticklabels(yt)
            ax[aeri].set_ylim(
                min(    ymxd[xd[0]:xd[1]]    )
                ,max(    ymxd[:]    )
            )

            #shade  pct
            pct=5
            el=np.percentile(yed,90+pct)
            eax.axhspan(el,max(ymxd),facecolor='r',alpha=.1)
            #put pct tick
            if len(yt)>1 and el!=max(ymxd):
                yt= ax[aeri].get_yticks()
                yt[-2]=el
                ax[aeri].set_yticks(yt)
                yt= ax[aeri].get_yticks().tolist()
                yt[-2]=str(pct)+'\%';
                yt[-1]='max'; #aah i have to set this again :/
                ax[aeri].set_yticklabels(yt)

            #window around anomaly.
            if self.al!=None:
                eax.axvspan(self.al-self.wins[aeri0]*.5
                            ,self.al+self.wins[aeri0]*.5
                            ,facecolor='yellow'
                            ,edgecolor='black',linewidth=2
                            ,alpha=.1
                            )
                frac=1.0/len(self.wins)
                ax[0].axvspan(self.al-self.wins[aeri0]*.5
                            ,self.al+self.wins[aeri0]*.5
                              ,ymin=(aeri-1)*frac
                              ,ymax=(aeri)*frac
                            ,fill=True,facecolor='yellow'
                            ,edgecolor='black',linewidth=2
                            ,alpha=.2
                            )


        #---end of err plt iter

        #adj adj
        fg.subplots_adjust(hspace=0)
        fg.tight_layout(pad=0) # could put neg for more condense

        return ret



# low pri todo: match with seaborn colors
class recon(sharexaxis):
    yl=['$x$','$\epsilon$']
    yc=['black','darkblue']

import analysis
import dat

class erfig(recon):
    name=None #if none, gets the name from class name
    al=None # (qualitative) anomaly location

    def data(self):
        er=[analysis.errs(self.name,awin) for awin in self.wins ]
        tsd=dat.get_series(self.name)
        return tsd,er

    def format(self):
        n=len(self.wins)+1
        latexify(6,ratio=.2*n) #w,r=h*w



# @register
# class er_ecg(erfig):
#     xl=1280;xu=1840
#     wins=[0,50,150,200]
#     al=1565

# @register
# class er_power(erfig):
#     xl=1800;xu=3000
#     wins=[0,200,300]
#     al=2466

# @register
# class er_gctime(erfig):
#    xl=1000;xu=1900
#    wins=[0,10,50,100,1000]
#    al=1350

@register
class er_gcstate(erfig):
   xl=1;xu=1100
   wins=[0,10,50,100]
   al=550


# BAYESIAN OPT ANALYSIS

import analysis

def bop(data
       ,hue='nl'
       ,y='o'
       ,x='n'
       ,est=np.mean):
    d=data.sort_values(by=hue)
    d=data.sort_values(by=x)
    oxc=set(d.columns)-set([y,hue]);  # other 'x' cols
    # print oxc
    hues=np.sort(np.unique(d[hue]));

    po=sns.pointplot(x=x,y=y,hue=hue
                      ,data=d
                     ,markers=('o', '>', 'p', 'v', '^', '8', 's', '<', '*', 'h', 'H', 'D', 'd')
                      ,join=False
                      ,dodge=True
                     ,hue_order=hues
                     ,estimator=est
    );

    # axes adjustments
    if np.any(d[y])>=0 and po.axes.get_ylim()[0]<0:
        po.axes.set_ylim(bottom=0) #the log axis helps to not have neg ticks
        pass
    # po.axes.set_ylim(top=10)
    po.yaxis.set_major_locator(ticker.MaxNLocator(5))
    # if max(d[y])/min(d[y])>10: po.axes.set_yscale('log')

    #labels
    yl= po.axes.get_ylabel()
    yl=yl.split('(')[0] # mean, median, mode ..etc
    yld={'mean':lambda x:'$\overline{%s}$'%x}
    plt.ylabel(yld[yl]('L')+'$_v$') # ..of validation set
    xld={'n': r'$n$', 'nl': '$l$'}
    plt.xlabel(xld[x])
    po.legend(title=xld[hue],loc='best')

    #putting a line in myself b/c seaborn doesn't do it right!!

    grps=d.groupby(by=[hue]+list(oxc),sort=True)
    grps=grps.aggregate(est)#.reset_index();

    xlocs=[]
    for al in po.lines:
        xlocs.append( al.get_data()[0][0] )
    xlocsd={}
    for ai,ak in enumerate(np.unique(d[x])):
        xlocsd[ak]=xlocs[ai]
    xlocs=xlocsd; del xlocsd;

    for ah in hues:
        xs=[]; ys=[]
        for ax in np.unique(d[d[hue]==ah][x]):
            xs.append(xlocs[ax])
            ix=grps.index.names.index(x)
            ih=grps.index.names.index(hue)
            i=list(range(2))
            i[ix]=ax
            i[ih]=ah
            ys.append(grps[y][tuple(i)])
        plt.plot( xs,ys,zorder=1 )

    plt.tight_layout(pad=0.05)
    return po


class bo(fig):
    bop_kwargs={
        'hue':'nl'
       ,'y':'o'
       ,'x':'n'
       ,'est':np.mean
        }

    #def style already seaborn style

    def plot(self):
        fig().plot(); #jus' closes a previous plot
        self.format();
        return bop(self.data())

    def format(self):
        latexify(fig_width=5,ratio=(sqrt(5)-1.0)/2.0)#'golden')

    def data(self):
        return analysis.bo_diag(self.name.replace('bo_',''))



# @register
# class bo_power(bo):pass

# @register
# class bo_ecg(bo): pass

# @register
# class bo_gctime(bo): pass



# VALIDATION and TRAINING ERROR

class trn(fig):
    def plot(self):
        fig().plot(); #jus' closes a previous plot
        self.format();
        d=self.data()
        plt.plot(d['trn'],label='training')
        po=plt.plot(d['vld'],label='validation')[0]
        po.axes.set_yscale('log')
        po.axes.get_xaxis().set_label_text('epoch')
        po.axes.get_yaxis().set_label_text('$L$')
        plt.legend(loc='upper right')
        plt.autoscale(tight=True)
        plt.tight_layout(pad=0.05)
        return po

    def format(self):
        latexify(fig_width=3,ratio=(sqrt(5)-1.0)/2.0)#'golden')

    def data(self):
        nm=self.name.replace('trn_','')
        br=analysis.get_best_params(nm)['run_id']
        return analysis.get_epocherr(nm,br)

# @register
# class trn_gctime(trn):pass

# @register
# class trn_ecg(trn):pass

#----
def latexify(fig_width=None
             , fig_height=None
             ,ratio='golden'
             , columns=1):
    """Set up matplotlib's RC params for LaTeX plotting.
    Call this before plotting a figure.

    Parameters
    ----------
    fig_width : float, optional, inches
    fig_height : float,  optional, inches
    columns : {1, 2}
    """

    # code adapted from http://www.scipy.org/Cookbook/Matplotlib/LaTeX_Examples

    # Width and max height in inches for IEEE journals taken from
    # computer.org/cms/Computer.org/Journal%20templates/transactions_art_guide.pdf

    assert(columns in [1,2])

    if fig_width is None:
        fig_width = 3.39 if columns==1 else 6.9 # width in inches

    if fig_height is None:
        if ratio=='golden':
            ar = (sqrt(5)-1.0)/2.0    # Aesthetic ratio
        else:
            ar=ratio
            fig_height = fig_width*ar # height in inches

    MAX_HEIGHT_INCHES = 8.0
    if fig_height > MAX_HEIGHT_INCHES:
        print("WARNING: fig_height too large:" + str(fig_height) +
              "so will reduce to" + str(MAX_HEIGHT_INCHES) + "inches.")
        fig_height = MAX_HEIGHT_INCHES

#low priority todo: gmu preamble?
    params = {'backend': 'ps',
              'text.latex.preamble': [
                  r'\input{%s/custom}' % os.path.join(os.getcwd(),'').replace('\\','/') #%
              ],
              'axes.labelsize': 10, # fontsize for x and y labels (was 10)
              'axes.titlesize': 10,
              'font.size': 10, # was 10
              'legend.fontsize': 10, # was 10
              'xtick.labelsize': 10,
              'ytick.labelsize': 10,
              'text.usetex': True,
              'figure.figsize': [fig_width,fig_height],
              'font.family': 'serif'
    }

    matplotlib.rcParams.update(params)


def format_axes(ax):

    for spine in ['top', 'right']:
        ax.spines[spine].set_visible(False)

    for spine in ['left', 'bottom']:
        ax.spines[spine].set_color(SPINE_COLOR)
        ax.spines[spine].set_linewidth(0.5)

    ax.xaxis.set_ticks_position('bottom')
    ax.yaxis.set_ticks_position('left')

    for axis in [ax.xaxis, ax.yaxis]:
        axis.set_tick_params(direction='out', color=SPINE_COLOR)

    return ax



if __name__=='__main__':
    import sys
    fignm=sys.argv[1]

    if fignm=='all': fignm=registry.keys()
    else: fignm=[fignm]

    for afn in fignm: plt.close(); eval(afn+'().save()');
