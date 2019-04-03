import networkx as nx
import random
import numpy as np
import matplotlib.pyplot as plt

def success_rate(f, n, *args):
    success = 0
    for i in range(n):
        if f(*args):
            success += 1
    return success/n

def average_latency(graph):
    G = 0
    for e_start, e_end in graph.edges:
        G += graph[e_start][e_end]['a']

    load_sum = 0
    for e_start, e_end in graph.edges:
        load_sum += graph[e_start][e_end]['a']/((graph[e_start][e_end]['c']) - graph[e_start][e_end]['a'])

    return 1/G * load_sum

def works(graph, N, p, T_max):
    #copies list of edges
    edges = list(graph.edges)

    for e_start, e_end in edges:
        #if the edge doesn't work, we remove it
        if random.uniform(0, 1) > p:
            graph.remove_edge(e_start, e_end)

            if not nx.is_connected(graph):
                return False
            #we recreate the load_matrix
            a = [[0 for x in range(len(N))] for y in range(len(N))]
            for i in range(len(N)):
                for j in range(len(N[i])):
                    path = nx.shortest_path(graph, source=i, target=j)
                    for v in range(len(path)):
                        if v != len(path) - 1:
                            a[path[v]][path[v+1]] += N[i][j]
                            if a[path[v]][path[v+1]] >= graph[path[v]][path[v+1]]['c']:
                                return False
            #and reapply it
            for e_start, e_end in graph.edges:
                graph[e_start][e_end]['a'] = a[e_start][e_end]
    T = average_latency(graph)
    return T < T_max

def experiment(*args):
    #size is the number of nodes
    SIZE = 10

    #peterser is optimal here
    graph = nx.petersen_graph()

    #arbitraty large number by observation
    c = [[150]*SIZE]*SIZE

    #N[i][j] = 10 <=> 10 packets are sent between i and j
    N = []
    for i in range(len(c)):
        sub = []
        for j in range(len(c)):
            if i != j:
                sub.append(random.randint(5, 10))
            else:
                sub.append(0)
        N.append(sub)
    
    #a[i][j] = 120 <=> edge between i and j transports 120 packets every second
    a = [[0 for x in range(len(N))] for y in range(len(N))]
    for i in range(len(N)):
        for j in range(len(N[i])):
            path = nx.shortest_path(graph, source=i, target=j)
            for v in range(len(path)):
                if v != len(path) - 1:
                    a[path[v]][path[v+1]] += N[i][j]

    #we set capacity of every edge and its load
    for e_start, e_end in graph.edges:
       graph[e_start][e_end]['c'] = c[e_start][e_end]
       graph[e_start][e_end]['a'] = a[e_start][e_end]

    #we check if the network will work
    return works(graph, N, *args)

for i in np.linspace(0.3, 0.95, 30):
    for j in np.linspace(0.025, 0.01, 30):
        print('{:f},{:f},{:f}'.format(i, j, success_rate(experiment, 1000, i, j)))

#http://almende.github.io/chap-links-library/js/graph3d/playground/