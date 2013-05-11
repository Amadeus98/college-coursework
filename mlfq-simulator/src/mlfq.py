# MLFQ scheduler implementation

from __future__ import print_function
from collections import deque

class MLFQScheduler:
    def __init__(self, quanta):
        self.quanta = quanta
        self.queues = []
        self.queue_lengths = []
        for i in range(0, len(quanta)):
            self.queues.append(deque())
            self.queue_lengths.append([])
        self.switches = dict()
        self.jid_queue = dict()
        pass

    def job_created(self, jid):
        if jid not in self.jid_queue: 
            self.queues[0].append(jid)
            self.jid_queue[jid] = 0    
            self.switches[jid] = 0 
        pass

    def job_ready(self, jid):
        last_queue = self.jid_queue[jid]
        self.queue_lengths[last_queue].append(len(self.queues[last_queue]))
        self.queues[last_queue].append(jid)
        pass

    def job_quantum_expired(self, jid): 
        cur_queue = self.jid_queue[jid]
        if cur_queue < (len(self.quanta)-1):
            next_queue = cur_queue+1
            self.queue_lengths[next_queue].append(len(self.queues[next_queue]))
            self.queues[next_queue].append(jid)
            self.jid_queue[jid] = next_queue
            self.switches[jid] += 1
        else: 
            self.queue_lengths[cur_queue].append(len(self.queues[cur_queue]))
            self.queues[cur_queue].append(jid)
        pass

    def job_preempted(self, jid):
        cur_queue = self.jid_queue[jid]
        self.queue_lengths[cur_queue].append(len(self.queues[cur_queue]))
        self.queues[cur_queue].appendleft(jid)
        pass

    def job_terminated(self, jid):
        pass

    def job_blocked(self, jid):
        cur_queue = self.jid_queue[jid]
        if cur_queue > 0: 
            self.jid_queue[jid] = cur_queue-1
            self.switches[jid] += 1
        pass

    def needs_resched(self):
        top_range = (len(self.quanta)-1)/2
        for i in range(0, top_range): 
            if self.queues[i]: 
                return True
        return False

    def next_job_and_quantum(self):
        for i, q in enumerate(self.quanta): 
            if self.queues[i]: 
                return (self.queues[i].popleft(), q)
        return (None, 0)

    def print_report(self):
        print()
        print('  JID | # Switches')
        print('------------------')
        for jid in sorted(self.switches.keys()):
            print('{:5d} | {:10d}'.format(jid, self.switches[jid]))
        print()
        print('Queue | Avg length')
        print('------------------')
        for i in range(0, len(self.quanta)):
            if len(self.queue_lengths[i]) > 0: 
                avg = sum(self.queue_lengths[i], 0.0) / len(self.queue_lengths[i]) 
            else: 
                avg = 0.0
            print('{:5d} | {:10.2f}'.format(i, avg)) 
        print()
        pass

