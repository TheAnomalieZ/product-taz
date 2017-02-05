import analysis
import dat
import csv
ts=dat.get_series("gctime")
print len(ts)
tl=int(.7*len(ts))
trn=(ts[:tl])
vld=(ts[tl:])

file = open("./components/org.taz.core/src/main/python/filename.csv", "wb")
writer = csv.writer(file)
writer.writerows(ts)
file.close()