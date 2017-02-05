"""has functions to handle segmenting a long timeseries for consumption
in a neural network"""


from random import randrange


def irandrange(*rng):
    #should be a primitive
    """gives a random number in rng until numbers are exhausted"""
    try: rng=int(rng)
    except: pass
    try: len(rng)
    except TypeError: rng=(rng,)
    picked=set()
    while len(picked) < len(xrange(*rng)):
        pick=randrange(*rng)
        if pick not in picked: 
            picked.add(pick)
            yield pick



def iwin(T, batch_size=32
         , min_winsize= 10, slide_jump=1, winsize_jump=1
         ,max_winsize='T'
         ,winloc_shuffle=True, winsize_shuffle=True):
    """this is just the (separated out) logic of the 
    sliding window minibatch
    returns: (windowsize, indexes of sliding window)
    it returns batch sizes <= batch_size so it's not strict
    """
    assert max_winsize>=min_winsize
    if max_winsize=='T': max_winsize = T
    else: T=int(T)

    winsize_rng = ( min_winsize, max_winsize+1, winsize_jump )
    if winsize_shuffle == True: iws = irandrange(*winsize_rng)
    else: iws=xrange(*winsize_rng)
    if winloc_shuffle == True: iwlf = irandrange
    else: iwlf=xrange
    for winsize in iws:
        iwl = iwlf(*(0,T,slide_jump))
        winlocs=[]
        for winloc in iwl:
            if (T-winsize)<winloc: continue
            winlocs.append(winloc)
            if len(winlocs)==batch_size:
                yield winsize , winlocs
                winlocs=[]
         #the remainig from the location looping
        if len(winlocs)!=0: yield winsize,winlocs



def iwin_fixed(*args,**kwargs):
    """a version that returns batches of the size batch_size"""
    
    batch_size=kwargs['batch_size']
    iw=iwin(*args,**kwargs)
    for awinsize,winlocs in iw:
        if len(winlocs)!=batch_size : continue
        else: yield awinsize,winlocs

        
        
winbatch_igen=iwin_fixed
def winbatch_gen(seq, batch_igen=winbatch_igen
                 , batchproc_callback=lambda x:x 
                 ,**kwargs):
    """creates batches for consumption by RNN
    first axis of numpy sequence should be time"""
    
    import numpy as np
    if seq.ndim!=2: 
        raise ValueError('seq needs to be 2D. if 1D try seq=seq[:,None]')
    bi=batch_igen(len(seq),**kwargs)
    for awinsize , iwinlocs in bi:
        abatch=[]
        for awinloc in iwinlocs:
            abatch.append((seq[awinloc:awinloc+awinsize]))
        yield batchproc_callback(np.array(abatch,dtype=abatch[0].dtype))

        

from itertools import cycle
class winbatch(object):
    """a callable version of winbatch_gen for theanonets"""

    def __init__(self,*args,**kwargs):
        
        self.length=(kwargs.setdefault('length',None))
        kwargs.pop('length')
        self.mybatch_gen=cycle(winbatch_gen(*args,**kwargs)) #itertools to the rescue!
        seq=args[0]
        self._len_gen=winbatch_igen(len(seq),**kwargs)

        if self.length!=None:
            self.length=int(self.length)
        else:
            self.length=len(self)

        return


    def __call__(self):
        
        return  [self.mybatch_gen.next()]

    def __len__(self):
        
        if self.length!=None: return self.length
        count=0
        for i in self._len_gen: count+=1
        self.length=count
        return count
        


from itertools import islice
def window(seq, size=2,step=1):
    """"
    Returns a sliding window (of width n) over data from the iterable
       s -> (s0,s1,...s[n-1]), (s1,s2,...,sn), ...                   
    """
    n=size
    it = iter(seq)
    result = tuple(islice(it, n))
    if len(result) == n:
        yield result    
    for elem in it:
        result = result[1:] + (elem,)
        yield result

# https://scipher.wordpress.com/2010/12/02/simple-sliding-window-iterator-in-python/
def slidingwindow(sequence,size=2,step=1):
    """Returns a generator that will iterate through
    the defined chunks of input sequence.  Input sequence
    must be iterable."""
    winSize=size
    # Verify the inputs
    try: it = iter(sequence)
    except TypeError:
        raise Exception("**ERROR** sequence must be iterable.")
    if not ((type(winSize) == type(0)) and (type(step) == type(0))):
        raise Exception("**ERROR** type(winSize) and type(step) must be int.")
    if step > winSize:
        raise Exception("**ERROR** step must not be larger than winSize.")
    if winSize > len(sequence):
        raise Exception("**ERROR** winSize must not be larger than sequence length.")
 
    # Pre-compute number of chunks to emit
    numOfChunks = ((len(sequence)-winSize)/step)+1
 
    # Do the work
    for i in range(0,numOfChunks*step,step):
        yield sequence[i:i+winSize]
