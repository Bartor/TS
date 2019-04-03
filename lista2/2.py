import networkx as nx
import random
import numpy as np

def success_rate(f, n, *args):
    success = 0
    for i in range(n):
        if f(*args):
            success += 1
    return success/n

def average_latency(graph, average_packet_size):
    G = 0
    for e_start, e_end in graph.edges:
        G += graph[e_start][e_end]['load']

    load_sum = 0
    for e_start, e_end in graph.edges:
        load_sum += graph[e_start][e_end]['load']/((graph[e_start][e_end]['capacity']/average_packet_size) - graph[e_start][e_end]['load'])

    return 1/G * load_sum

def works(graph, p, T_max, average_packet_size):
    edges = list(graph.edges)
    for e_start, e_end in edges:
        if random.uniform(0, 1) > p:
            load = graph[e_start][e_end]['load']
            graph.remove_edge(e_start, e_end)
            if (not nx.is_connected(graph)):
                return False
            path = nx.shortest_path(graph, source=e_start, target=e_end)
            for v in range(len(path)):
                if v != len(path) - 1:
                    graph[path[v]][path[v+1]]['load'] += load
                    if graph[path[v]][path[v+1]]['load'] >= graph[path[v]][path[v+1]]['capacity']:
                        return False
    T = average_latency(graph, average_packet_size)
    return T < T_max

def experiment(*args):
    capacity_matrix = [[50]*10]*10
    load_matrix = [[random.randint(3, 7)]*10]*10
    graph = nx.petersen_graph()
    for e_start, e_end in graph.edges:
        graph[e_start][e_end]['capacity'] = capacity_matrix[e_start][e_end]
        graph[e_start][e_end]['load'] = load_matrix[e_start][e_end]
    return works(graph, *args, 1)

for i in np.linspace(0.3, 0.95, 30):
    for j in np.linspace(0.030, 0.021, 30):
        print('p={:f} t={:f} :: {:f}'.format(i, j, success_rate(experiment, 100, i, j)))