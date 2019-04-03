import networkx as nx
import random

def success_rate(f, n):
    success = 0
    for i in range(n):
        if f():
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
                    if graph[path[v]][path[v+1]]['load'] > graph[path[v]][path[v+1]]['capacity']:
                        return False
    T = average_latency(graph, average_packet_size)
    return T < T_max

capacity_matrix = [[100]*10]*10
load_matrix = [[7]*10]*10

def experiment():
    graph = nx.petersen_graph()
    for e_start, e_end in graph.edges:
        graph[e_start][e_end]['capacity'] = capacity_matrix[e_start][e_end]
        graph[e_start][e_end]['load'] = load_matrix[e_start][e_end]
    return works(graph, 0.75, 0.015, 1)

print(success_rate(experiment, 10000))