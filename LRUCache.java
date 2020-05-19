package com.leetcode.design;

import java.util.HashMap;

public class LRUCache {

  class Node {
    int key;
    int value;
    Node prev;
    Node next;

    Node(int a, int b) {
      key = a;
      value = b;
    }

    public String toString() {
      return key + " ";
    }
  }

  int cacheCapacity, count = 0;
  Node head;
  Node tail;
  HashMap<Integer, Node> cache;

  public LRUCache(int capacity) {
    cacheCapacity = capacity;
    cache = new HashMap<>();
    head = new Node(0, 0);
    tail = new Node(0, 0);
    head.next = tail;
    tail.prev = head;
    head.prev = null;
    tail.next = null;
    count = 0;
  }

  private void addToHead(Node node) {
    node.next = head.next;
    node.next.prev = node;
    node.prev = head;
    head.next = node;
  }

  private void deleteNode(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }

  public int get(int key) {
    if (cache.containsKey(key)) {
      Node node = cache.get(key);
      deleteNode(node);
      addToHead(node);
      return node.value;
    }
    return -1;
  }


  public void put(int key, int value) {
    if (cache.containsKey(key)) {
      Node node = cache.get(key);
      node.value = value;
      deleteNode(node);
      addToHead(node);
    } else {
      Node node = new Node(key, value);
      cache.put(key, node);
      if (count < cacheCapacity) {
        count++;
        addToHead(node);
      } else {
        cache.remove(tail.prev.key);
        deleteNode(tail.prev);
        addToHead(node);
      }
    }
  }

  public static void main(String[] args) {

    LRUCache cache = new LRUCache(2 /* capacity */ );

    cache.put(1, 1);
    cache.put(2, 2);
    System.out.println(cache.get(1)); // returns 1
    cache.put(3, 3); // evicts key 2
    System.out.println(cache.get(2)); // returns -1 (not found)
    cache.put(4, 4); // evicts key 1
    System.out.println(cache.get(1)); // returns -1 (not found)
    System.out.println(cache.get(3)); // returns 3
    System.out.println(cache.get(4)); // returns 4

  }

}
