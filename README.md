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
  * To get around the `StackOverflowError` that plagued me in part 2, I had to call `doall` around the `map` function,
  since it did a lot of calculations, and that overloaded the lazy map.  Given the use case, there really was no need
  for lazy evaluation, and evaluating it didn't have any negative impacts. 

* Day 20
  * I did part 1 before moving to 2016, and I think I always regretted reformatting the inputs.
  * That said, I just made an educated guess that 10,000 tests would be enough for the particles to have hit,
  so it's not perfect but it got me the star.

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

* Day 23
  * Sorry, Eric, but my goal isn't to figure out the brain-teaser part of these challenges. I read online what the
  magic secret was to part #2, and then I implemented it in Clojure. I've learned by now that there are always 1 or 2
  problems that depend on _not_ coding and instead interpretting the data set, and I'm not game.
  * That said, part 1 was fun in that I got a ton of reuse from Day 18.  If I see this again in another day's problem,
  I might extend this further; perhaps each value in the action-map can support an optional filter, such that
  `jnz` and `jgz` could both use a common function, but with an optional filter to decide if the branch should be taken.
  That could also work with some of the earlier channel work.
  
* Day 24
  * The hardest part of this was figuring out the mix of `concat`, `cons`, and `merge` in `find-paths`.
  If a path is defined as a vector of points, and a point is a 2-element vector, then the possible paths from a source
  is a list of a vector of vectors.
  * What also made this hard was making sure I got the intermediate bridges. So if I have a bridge `0/1--10/1` and I
  add `9/10` to the end, the instructions said to keep both `0/1--10/1` and `0/1--10/1--9/10` in the list of possible
  bridges. It's a simple `cons`, but again it was hard to get the grouping sizes right.  And then when all was said and
  done, it was entirely unnecessary, since an intermediate bridge will neither be stronger than nor longer than an
  extension. Oh well -- it was a learning experience! 