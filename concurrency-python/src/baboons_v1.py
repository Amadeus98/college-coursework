# CS 450 Assignment 3 Part 2
# 
# Emmanuel Marcha

from __future__ import print_function
from threading import Semaphore, Lock, Thread
from time import sleep
from timeit import Timer
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

def act_as_baboon(bid, init_side):
    global avg_time
    import random
    rng = random.Random()
    rng.seed(bid)
    random = rng.random()
    
    def time_cross():
        side = init_side
        count = 0
        
        while count < num_cross:
            with turnstile:
                switches[side].lock(rope)
            with multiplex:
                sleep(random)  # simulate crossing
            switches[side].unlock(rope)
            side = 1 - side
            count += 1
    
    timer = Timer(time_cross)
    time = timer.timeit(1)
    with mutex: 
        avg_time += time
        print('Baboon {} finished in {:0.3f}s'.format(bid, time))
    
    
def time_sim():
    global avg_time
    avg_time = 0
    bt = [Thread(target=act_as_baboon, args=[i, 1]) for i in range(num_baboons)]
    for b in bt: b.start()
    for b in bt: b.join()
    print('----------------------------')
    avg_time = avg_time/num_baboons
    

NUM_SIMS    = 3
NUM_BABOONS = 10
NUM_CROSS   = 10
ROPE_MAX    = 5

if __name__ == '__main__':
    global avg_time
    
    parser = argparse.ArgumentParser(description='Baboon-Crossing Simulator')
    parser.add_argument('--baboons', '-b', 
                        type = int, 
                        default = NUM_BABOONS, 
                        help = 'Number of baboons', 
                        metavar = 'total_baboons')
    
    parser.add_argument('--crossings', '-c', 
                        type = int, 
                        default = NUM_CROSS, 
                        help = 'Max number of crossings per baboon', 
                        metavar = 'max_crossings')
    
    parser.add_argument('--rope_max', '-r', 
                        type = int, 
                        default = ROPE_MAX, 
                        help = 'Max number of baboons the rope can hold', 
                        metavar = 'rope_max')
    
    parser.add_argument('--sims', '-s', 
                        type = int, 
                        default = NUM_SIMS,  
                        help = 'Total number of simulations', 
                        metavar = 'total_simulations')
    
    args = parser.parse_args()
    
    num_sims    = args.sims
    num_baboons = args.baboons
    num_cross   = args.crossings
    rope_max    = args.rope_max
    
    rope       = Lock()
    turnstile  = Lock()
    switches   = [Lightswitch(), Lightswitch()]
    multiplex  = Semaphore(rope_max)
    mutex      = Semaphore(1)
    
    print('Timing {} simulations with {} baboons, {} crossings:'.format(num_sims, 
                                                                        num_baboons,
                                                                        num_cross))
    
    print('----------------------------')
    total_timer = Timer(time_sim)
    total_timer = total_timer.timeit(num_sims)
    print('Total time elapsed:  {:0.3f}s'.format(total_timer))
    print('Avg. time per run:   {:0.3f}s'.format(avg_time))