import numpy as np
from hmmlearn import hmm
import matplotlib.pyplot as plt
import math as mm
import csv as C
import time
from linked_list import LinkedList
from sklearn.externals import joblib
import sys
from HMMModel import HMMModel

n1 = int(sys.argv[1])
n2 = int(sys.argv[2])
n3 = int(sys.argv[3])

np.random.seed(42)
hmm = HMMModel()
model = hmm.trainingHMM('no_big_states.csv', n1)

for i in range(1, 6):
    size = i*10
    scorelist2 = hmm.testingHMM('anomaly_final_states1.csv', size, n3, model)
    #scorelist2 = hmm.testingHMM('ano3_states.csv',size, n3, model)
    labellist1 = hmm.labeling(460,600, scorelist2)
    #labellist1n = hmm.relabeling(1110,)

    hmm.drawROC(scorelist2, labellist1, size)

# for i in range(1, 9):
#     states = i + 1
#     model = hmm.trainingHMM('no_big_states.csv', states)
#     scorelist2 = hmm.testingHMM('anomaly_final_states1.csv', n2, n3, model)
#     #scorelist2 = hmm.testingHMM('ano3_states.csv',size, n3, model)
#     labellist1 = hmm.labeling(460,600, scorelist2)
#     #labellist1n = hmm.relabeling(1110,)
#
#     hmm.drawROC(scorelist2, labellist1, states)

plt.xlim(-1, 101)
plt.ylim(-1, 101)
plt.xlabel('FPR')
plt.ylabel('TPR')
plt.title('ROC Graph')
plt.legend()
plt.show()
