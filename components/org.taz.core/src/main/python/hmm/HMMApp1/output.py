import numpy as np
import csv
from sklearn.externals import joblib
import sys
from HMMModel import HMMModel

np.random.seed(42)

n2 = int(sys.argv[1])
n3 = int(sys.argv[2])
filepath = str(sys.argv[3])
modelpath = str(sys.argv[4])

hmm = HMMModel()
model = joblib.load("/home/suve/product-taz/components/org.taz.core/src/main/python/hmm/HMMApp1/model1.pkl")
print filepath
scorelist = hmm.testingHMM(filepath, n2, n3, model)
scorelist= [scorelist[x:x+1] for x in xrange(0, len(scorelist), 1)]
file = open(filepath+"_hmm_score.csv", "wb")
print filepath
writer = csv.writer(file)
writer.writerows(scorelist)
file.close()
