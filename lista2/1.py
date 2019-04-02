import networkx as nx
import matplotlib.pyplot as plt
import random

def success_rate(f, n):
    success = 0
    for i in range(n):
        if f():
            success += 1
    return success/n

def first():
    G = nx.Graph()
    for i in range(20):
        G.add_node(i)
        if i > 0:
            if random.uniform(0, 1) < 0.95:
                G.add_edge(i-1, i)
    return nx.is_connected(G)

def second():
    G = nx.Graph()
    for i in range(20):
        G.add_node(i)
        if i > 0:
            if random.uniform(0, 1) < 0.95:
                G.add_edge(i-1, i)
    if random.uniform(0, 1) < 0.95:
        G.add_edge(0, 19)
    return nx.is_connected(G)

def third():
    G = nx.Graph()
    for i in range(20):
        G.add_node(i)
        if i > 0:
            if random.uniform(0, 1) < 0.95:
                G.add_edge(i-1, i)
    if random.uniform(0, 1) < 0.95:
        G.add_edge(0, 19)
    if random.uniform(0, 1) < 0.8:
        G.add_edge(0, 9)
    if random.uniform(0, 1) < 0.7:
        G.add_edge(4, 14)
    return nx.is_connected(G)

def fourth():
    G = nx.Graph()
    for i in range(20):
        G.add_node(i)
        if i > 0:
            if random.uniform(0, 1) < 0.95:
                G.add_edge(i-1, i)
    if random.uniform(0, 1) < 0.95:
        G.add_edge(0, 19)
    if random.uniform(0, 1) < 0.8:
        G.add_edge(0, 9)
    if random.uniform(0, 1) < 0.7:
        G.add_edge(4, 14)

    for i in range(4):
        r1 = random.randrange(20)
        r2 = random.randrange(20)
        while (r1, r2) in G.edges:
            r1 = random.randrange(20)
            r2 = random.randrange(20)
        if random.uniform(0, 1) < 0.4:
            G.add_edge(r1, r2)
    return nx.is_connected(G)

print("First:", success_rate(first, 10000))
print("Second:", success_rate(second, 10000))
print("Third:", success_rate(third, 10000))
print("Fourth:", success_rate(fourth, 10000))