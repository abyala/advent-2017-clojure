# Advent of code 2017

This is an implementation of the [2017 Advent of Code](https://adventofcode.com/2017) that I'm using as a way of
learning Clojure. As this is my very first project, it's hardly "good code."

Note: I came back to finish this after completing AoC 2016. I had to start from day 21, and I'll have to go back to 
days 18 and 20 to finish those missing stars. Hopefully I've improved!

# Lessons Learned

_I'm only starting this after returning from AoC 2016, since I didn't record my little diary the first time around._

* Day 18
  * This problem was a killer to be flexible, and was the main reason why I gave up on 2017 until I finished 2016.
  * What I love about the solution is the fact that the program externalizes its instruction set into the `action-map`,
  since `part1` and `part2` wanted the `rcv` instruction to do two different things.
  * I also love how the action map leverages `state-changer`, `mover`, and `side-effect` as composable functions that
  the `take-action` function can reuse. I suppose I could also use namespaced-keywords to abstract away the
  implementation logic of `take-action` from the caller.
  * I think this was also my first use of `core.async` in a real problem. The program does not execute both threads in
  parallel, because detecting deadlock would be tricky. But I got to use `<!!`, `>!!`, and `poll!`.

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

* Day 22
  * Nothing very difficult here, so I decided to focus on readability by adding more smaller functions, like
  `status-at-carrier` and even `move`.
  * So many AoC problems are path finding, and I always take forever to decide how to handle directions. When this
  inevitably shows up in future years, I'll have to make a break-out namespace to simplify it. But the `direction-map`
  seemed to cover what I needed - turning and moving.
  * I've been focusing on my input destructuring, including the use of `:as` to both destructure a function argument
  _and_ leave it intact.
  * Going back to decomposition, I'm really happy with the level of granularity in both the `run-burst` and `solve`
  functions. Part of my learning Clojure is to focus on functional programming, and I enjoyed working with `run-burst`
  in the REPL.
  * The fun part of AoC for me is refactoring each part 1 to support part 2 with minimal code duplication, and I feel
  like the definition of the virus algorithm as the single aspect that differentiates `part1` from `part2` is clean.   
  