import numpy as np
import matplotlib.pyplot as plt
from sklearn.externals import joblib
import sys
from HMMModel import HMMModel

n1 = int(sys.argv[1])
n2 = int(sys.argv[2])
name = str(sys.argv[3])
filename = str(sys.argv[4])
filename1 = str(sys.argv[5])
n3 = int(sys.argv[6])

np.random.seed(42)
hmm = HMMModel()
fileDir = "/home/suve/FYP/HMM/" + name + "/States/" + filename
testfile = "/home/suve/FYP/HMM/" + name + "/States/" + filename1
#model = hmm.trainingHMM(fileDir, n1)
#model = hmm.trainingHMM("/home/suve/FYP/HMM/App1/States/no", n1)
print fileDir
print "Success"
# for i in range(1, 6):
#     size = i*10
#     #scorelist2 = hmm.testingHMM("/home/suve/FYP/HMM/App1/States/anomaly_final", size, n2, model)
#     scorelist2 = hmm.testingHMM(testfile,size, n2, model)
#     labellist = hmm.labeling(130,270, scorelist2)
#     labellist = hmm.relabeling(290,398, labellist)
#     labellist = hmm.relabeling(545,665, labellist)
#     labellist = hmm.relabeling(750,1430, labellist)
#     hmm.drawROC(scorelist2, labellist, size)
#Anomaly Validation
# for i in range(1, 6):
#     size = i*10
#     #scorelist2 = hmm.testingHMM("/home/suve/FYP/HMM/App1/States/anomaly_final", size, n2, model)
#     scorelist2 = hmm.testingHMM(testfile,size, n2, model)
#     labellist = hmm.labeling(299,335, scorelist2)
#     hmm.drawROC(scorelist2, labellist, size)
#
# for i in range(1, 6):
#     size = i*10
#     #scorelist2 = hmm.testingHMM("/home/suve/FYP/HMM/App1/States/anomaly_final", size, n2, model)
#     scorelist2 = hmm.testingHMM(testfile,size, n2, model)
#     labellist = hmm.labeling(448,574, scorelist2)
#     #labellist = hmm.labeling(460,600, scorelist2)
#     #labellist = hmm.labeling(448,574, scorelist)
#     labellist = hmm.relabeling(1118,1388, labellist)
#     labellist = hmm.relabeling(1906,2037, labellist)
#     labellist = hmm.relabeling(2672,2889, labellist)
#     labellist = hmm.relabeling(3386,3501, labellist)
#     labellist = hmm.relabeling(3832,3888, labellist)
#     # labellist = hmm.relabeling(4348,4497, labellist)
#     # labellist = hmm.relabeling(5315,5605, labellist)
#     # labellist = hmm.relabeling(6131,6623, labellist)
#     hmm.drawROC(scorelist2, labellist, size)
#
#
# #labellist1n = hmm.relabeling(1110,)
#
# tp_avg_list = []
# for num in range(0, 6):
#     tp_avg = 0
#     for i in range(1, 7):
#         states = i + 1
#         model = hmm.trainingHMM(fileDir, states)
#         scorelist2 = hmm.testingHMM(testfile,n3, n2, model)
#         #scorelist2 = hmm.testingHMM('ano3_states.csv',size, n3, model)
#         labellist = hmm.labeling(130,270, scorelist2)
#         labellist = hmm.relabeling(290,398, labellist)
#         labellist = hmm.relabeling(545,665, labellist)
#         labellist = hmm.relabeling(750,1430, labellist)
#         #labellist1n = hmm.relabeling(1110,)
#         value = np.percentile(scorelist2, (i-1)*10)
#         roc_x, roc_y = hmm.drawROC1(scorelist2, labellist, states)
#         tp_avg = tp_avg + roc_y[num]
#     tp_avg_list.append(tp_avg/float(6))
#     print tp_avg/float(6)
#
# print tp_avg_list

tp_avg_list = []
for num in range(0, 6):
    tp_avg = 0
    for i in range(1, 7):
        states = i + 1
        model = hmm.trainingHMM(fileDir, states)
        scorelist2 = hmm.testingHMM(testfile,n3, n2, model)
        # labellist1 = hmm.labeling(460,600, scorelist2)
        labellist = hmm.labeling(299,335, scorelist2)
        # value = np.percentile(scorelist2, (i-1)*10)
        # roc_x, roc_y = hmm.drawROC1(scorelist2, labellist, states)
        value1 = hmm.areaROC1(scorelist2, labellist)
        print "Area of ROC" + str(value1)
        # tp_avg = tp_avg + roc_y[num]
#     tp_avg_list.append(tp_avg/float(6))
#     print tp_avg/float(6)
#
model = hmm.trainingHMM(fileDir, states)
scorelist2 = hmm.testingHMM(testfile,n3, n2, model)
threshold = np.percentile(scorelist2)
for num in range(0, 6):
    tp_avg = 0

    for i in range(0, len(scorelist)):
        if (scorelist[i] > T):
            if (labellist[i]==1):
                TP = TP + 1
            if (labellist[i]==0):
                FP = FP + 1
    roc_x.append(FP*100/float(N))
    roc_y.append(TP*100/float(P))
    FP=0
    TP=0

# print tp_avg_list

# for i in range(1, 9):
#     states = i + 1
#     model = hmm.trainingHMM(fileDir, states)
#     scorelist2 = hmm.testingHMM(testfile,n3, n2, model)
#     #scorelist2 = hmm.testingHMM('ano3_states.csv',size, n3, model)
#     labellist1 = hmm.labeling(460,600, scorelist2)
#     #labellist1n = hmm.relabeling(1110,)
#
#     hmm.drawROC(scorelist2, labellist1, states)

# plt.xlim(-1, 101)
# plt.ylim(-1, 101)
# plt.xlabel('FPR')
# plt.ylabel('TPR')
# plt.title('ROC Graph')
# plt.legend()
# plt.show()
