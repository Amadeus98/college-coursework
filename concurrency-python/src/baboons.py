# CS 450 Assignment 3 Part 2
# 
# Emmanuel Marcha

from __future__ import print_function
from threading import Semaphore, Lock, Thread
from random import random
from time import sleep
import argparse

class Lightswitch:
    def __init__(self):
        self.mutex = Lock()
        self.count = 0

    def lock(self, sem):
        with self.mutex:
            self.count += 1
            if self.count == 1:
                sem.acquire()

    def unlock(self, sem):
        with self.mutex:
            self.count -= 1
            if self.count == 0:
                sem.release()

class Baboon: 
    # Possible states 
    CROSSING    = 0
    WAITING     = 1
    
    state_names = ['Crossing', 
                   'Waiting']
    
    # Possible sides
    EAST    = 0 
    WEST    = 1
    
    side_names = ['East',
                  'West']
    
    def __init__(self, bid):
        self.bid = bid
        self.state = Baboon.WAITING
        self.side = Baboon.EAST
        self.num_crosses = 0
        
class Simulator: 
    def __init__(self):
        global rope, rope_max, rope_multiplex, turnstile, east_switch, west_switch
        rope            = Lock()
        turnstile       = Lock()
        east_switch     = Lightswitch()
        west_switch     = Lightswitch()
        rope_multiplex  = Semaphore(rope_max)
        
    def run_sim(self):
        global num_baboons
        self.bt = [Thread(target=self.start_crossing, args(i,)) for i in range(num_baboons)]
        for b in self.bt: b.start()
        
    def start_crossing(self, bid):
        b = Baboon(bid)
        b.cross()
        
if __name__ == '__main__': 
    global num_baboons, tot_crossings, rope_max
    
    parser = argparse.ArgumentParser(description='Baboon-Crossing Simulator')
    parser.add_argument('--baboons', '-b', 
                        type = int, 
                        default = 10, 
                        help = 'Number of baboons', 
                        metavar = 'total_baboons')
    
    parser.add_argument('--crossings', '-c', 
                        type = int, 
                        default = 5, 
                        help = 'Max number of crossings per baboon', 
                        metavar = 'max_crossings')
    
    parser.add_argument('--rope_max', '-r', 
                        type = int, 
                        default = 1, 
                        help = 'Max number of baboons the rope can hold', 
                        metavar = 'rope_max')
    
    args = parser.parse_args()
    
    # Set global variables 
    num_baboons = args.baboons 
    tot_crossings = args.crossings
    rope_max = args.rope_max
    
    # Begin golf simulation 
    sim = Simulator()
    sim.run_sim()