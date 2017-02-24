import numpy as np
import matplotlib.pyplot as plt
from sklearn.externals import joblib
import sys
from HMMModel import HMMModel

n1 = int(sys.argv[1])

np.random.seed(42)
hmm = HMMModel()
model = hmm.trainingHMM('no_big_states.csv', n1)

# for i in range(1, 6):
#     size = i*10
#     scorelist2 = hmm.testingHMM('anomaly_final_states1.csv', size, n3, model)
#     #scorelist2 = hmm.testingHMM('ano3_states.csv',size, n3, model)
#     labellist = hmm.labeling(448,574, scorelist2)
#     # labellist = hmm.labeling(448,574, scorelist)
#     labellist = hmm.relabeling(1118,1388, labellist)
#     labellist = hmm.relabeling(1906,2037, labellist)
#     labellist = hmm.relabeling(2672,2889, labellist)
#     labellist = hmm.relabeling(3386,3501, labellist)
#     labellist = hmm.relabeling(3832,3888, labellist)
#     labellist = hmm.relabeling(4348,4497, labellist)
#     labellist = hmm.relabeling(5315,5605, labellist)
#     labellist = hmm.relabeling(6131,6623, labellist)
#
# #labellist1n = hmm.relabeling(1110,)
#
#     hmm.drawROC(scorelist2, labellist, size)
#
# # for i in range(1, 9):
# #     states = i + 1
# #     model = hmm.trainingHMM('no_big_states.csv', states)
# #     scorelist2 = hmm.testingHMM('anomaly_final_states1.csv', n2, n3, model)
# #     #scorelist2 = hmm.testingHMM('ano3_states.csv',size, n3, model)
# #     labellist1 = hmm.labeling(460,600, scorelist2)
# #     #labellist1n = hmm.relabeling(1110,)
# #
# #     hmm.drawROC(scorelist2, labellist1, states)
#
# plt.xlim(-1, 101)
# plt.ylim(-1, 101)
# plt.xlabel('FPR')
# plt.ylabel('TPR')
# plt.title('ROC Graph')
# plt.legend()
# plt.show()
