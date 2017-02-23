import numpy as np
import csv
from sklearn.externals import joblib
import sys
from HMMModel import HMMModel

np.random.seed(42)

filepath = str(sys.argv[1])
modelpath = str(sys.argv[2])
appName = str(sys.argv[3])

hmm = HMMModel()
modelp = "/home/suve/product-taz/files/hmm/train/app1"
print modelpath
model = hmm.trainingHMM(filepath, 7)
joblib.dump(model, modelpath + "/model" + appName + ".pkl")
# joblib.dump(model, modelpath)
