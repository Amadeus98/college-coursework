# CS 450 Assignment 3 Part 1
# 
# Emmanuel Marcha

from __future__ import print_function
from threading import Thread, Semaphore
from random import random
from time import sleep
import argparse  

class Golfer(Thread):
    def __init__(self, gid):
        self.gid = gid
        self.balls = 0
        self.round = 0
        print('Golfer {} has appeared on the field'.format(self.gid))
    
    # Fill the golfer's bucket with 'bucket_size' balls from 'stash_size'
    def fill_bucket(self):
        global stash_size, stash_mutex, bucket_size
        with stash_mutex: 
            print('Golfer {} calling for bucket...'.format(self.gid))
            if stash_size < bucket_size: self.call_cart()
            stash_size -= bucket_size
            self.balls = bucket_size
            print('Golfer {} acquired {} balls; Stash size = {}'.format(self.gid, bucket_size, stash_size))
        sleep(random())
    
    # Hit each ball, incrementing count of 'field_balls' while 
    def swing(self):
        global field_mutex, field_balls, num_rounds 
        for r in range(num_rounds):
            self.round += 1
            print('Golfer {} has started playing round {}...'.format(self.gid, (r+1)))
            if self.balls == 0: self.fill_bucket()
            for i in range(self.balls): 
                with field_mutex:
                    self.balls -= 1
                    field_balls += 1
                    print('Golfer {} hit ball {}'.format(self.gid, i))
                sleep(random())
        print('Golfer {} finished playing {} rounds'.format(self.gid, self.round))
            
    # Collect all 'field_balls' and add to 'stash_size', replenishing the stash 
    def call_cart(self):
        global stash_size, field_balls, field_mutex
        with field_mutex:
            print('#######################################################')
            print('No more golfballs. Cart entering field...')
            stash_size += field_balls
            print('Cart gathered {} balls. Stash size = {}'.format(field_balls, stash_size))
            field_balls = 0
            print('#######################################################')

class Simulator: 
    def __init__(self):
        global stash_mutex, field_mutex, field_balls
        stash_mutex = Semaphore(1) 
        field_mutex = Semaphore(1)
        field_balls = 0
        print('{} golfers ready to play...'.format(num_golfers))
    
    # Create threads for simulation 
    def run_sim(self):
        global num_golfers
        self.gt = [Thread(target=self.play_golf, args=(i,)) for i in range(num_golfers)]
        for g in self.gt: g.start()
        
    # Target function for Thread; creates instance of Golfer and begins playing 
    def play_golf(self, gid):
        g = Golfer(gid)
        g.swing()

if __name__ == '__main__': 
    global stash_size, bucket_size, num_golfers, field_balls, num_rounds
    
    parser = argparse.ArgumentParser(description='Driving Range Simulator')
    parser.add_argument('--stash', '-s', 
                        type = int, 
                        default = 20, 
                        help = 'Size of golfball stash', 
                        metavar = 'stash_size')
    
    parser.add_argument('--bucket', '-b', 
                        type = int, 
                        default = 5, 
                        help = 'Number of golfballs in bucket', 
                        metavar = 'bucket_size')
    
    parser.add_argument('--golfers', '-g', 
                        type = int, 
                        default = 4, 
                        help = 'Number of golfers on the field', 
                        metavar = 'num_golfers')
    
    parser.add_argument('--rounds', '-r', 
                        type = int, 
                        default = 2, 
                        help = 'Number of rounds golfers play', 
                        metavar = 'num_rounds')
                        
    args = parser.parse_args()
    
    # Set global variables 
    stash_size = args.stash
    bucket_size = args.bucket
    num_golfers = args.golfers
    num_rounds = args.rounds
    
    # Begin golf simulation
    sim = Simulator()
    sim.run_sim()