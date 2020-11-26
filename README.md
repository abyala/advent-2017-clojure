# Advent of code 2017

This is an implementation of the [2017 Advent of Code](https://adventofcode.com/2017) that I'm using as a way of
learning Clojure. As this is my very first project, it's hardly "good code."

Note: I came back to finish this after completing AoC 2016. I had to start from day 21, and I'll have to go back to 
days 18 and 20 to finish those missing stars. Hopefully I've improved!

# Lessons Learned

_I'm only starting this after returning from AoC 2016, since I didn't record my little diary the first time around._

* Day 21
  * I had to do this a few times, because I couldn't decide if I wanted to represent the grid as one big string, or as
  sequences of strings. I eventually opted for the latter.
  * The complexity here was mostly around understanding grids, rows, row-groups, etc.
  * Look ma - no loops! I replaced my `loop -> recur` with `iterate`, which appears to be more functional and idiomatic.
  * I look them up every time, but `zipmap` and `interleave` both helped work with the collections.
  * I did discover that I can make a dynamic regex pattern, in this case combining `re-seq` with `re-pattern` in the
  `sub-grids` method. I needed this to break apart strings into groups of 2 or 3 characters.
  * I thought about a generic way to rotate a grid such that it would scale beyond 2 or 3, but I couldn't find one that
  wasn't going to be too complex. So instead, I played around with multi-methods (`defmulti` and `defmethod`) instead.
  A simple `case` sufficed for the original implementation, but this was a fun experiment that looks decent enough.
  