# Test file

from threading import Thread
from time import sleep 
from timeit import Timer
import random

def toTime():
    ts = [Thread(target=thread_body, args=[0.1]) for i in range(10)]
    for t in ts: t.start()
    for t in ts: t.join()
    
def thread_body(n):
    sleep(n)
    
if __name__ == '__main__': 
    timer = Timer(toTime)
    print("Time: {:0.3f}s".format(timer.timeit(100)/100))
    print(timer.repeat(5,1))