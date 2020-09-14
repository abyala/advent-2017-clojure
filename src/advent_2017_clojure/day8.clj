(ns advent-2017-clojure.day8
  (:require [clojure.string :as str]))

(def functions "Map of string functions from input into actual Clojure functions"
  {">" >, ">=" >=, "<" <, "<=" <=, "==" =, "!=" not=, "inc" +, "dec" -})

(defn parse-as-calculation "Returns a map of :check and :change to calculations to apply to the register"
  [line]
  (let [[change-name change-op change-amount _ check-name check-op check-amount] (str/split line #" ")]
    {:check  {:name check-name, :op (functions check-op), :amount (Integer/parseInt check-amount)}
     :change {:name change-name, :op (functions change-op), :amount (Integer/parseInt change-amount)}}))

(defn apply-calculation-to-register "Applies a calculation {:name, :op, :amount} to the current registers"
  [calculation registers]
  ((:op calculation) (or (registers (:name calculation)) 0) (:amount calculation)))

(defn apply-register-instructions "Runs all instructions to an all-zero register set, returning {:registers :max}"
  [instructions]
  (loop [[x & xs] instructions, registers {}, largest 0]
    (if (nil? x)
      {:registers registers, :max largest}
      (let [{check :check, change :change} (parse-as-calculation x),
            [next-registers next-largest] (if (apply-calculation-to-register check registers)
                                            (let [new-mapping (apply-calculation-to-register change registers)]
                                              [(assoc registers (:name change) new-mapping), (max largest new-mapping)])
                                            [registers largest])]
        (recur xs next-registers next-largest)))))

(defn max-register-at-the-end [instructions]
  (reduce max (map #(second %)
                   (:registers (apply-register-instructions instructions)))))
(defn max-register-ever [instructions]
  (:max (apply-register-instructions instructions)))

; Test cases
(def TEST_TEXT "b inc 5 if a > 1\na inc 1 if b < 5\nc dec -10 if a >= 1\nc inc -20 if c == 10")
(def TEST_LINES (str/split TEST_TEXT #"\n"))
(assert (= {"a" 1, "c" -10} (:registers (apply-register-instructions TEST_LINES))))
(assert (= 1 (max-register-at-the-end (str/split TEST_TEXT #"\n"))))
(assert (= 10 (max-register-ever (str/split TEST_TEXT #"\n"))))

; Actual data
(def INPUT_TEXT "av inc 167 if f > -9\nav inc 640 if uea == 0\nfk dec -960 if tn > -9\ntn dec 438 if fk == 960\nuc dec -907 if av >= 800\npr dec 176 if s < 4\nnjb dec 861 if l <= 3\nnjb dec 964 if mr >= -2\nl dec 360 if p == 0\nmr inc 372 if fk >= 958\nnjb dec 612 if s > -8\ndou inc -672 if dou < 1\nf inc 747 if njb <= -2447\npr dec -247 if d == 0\nme dec 928 if p == 0\ne dec -568 if f != 0\nfk dec 680 if me != -937\ne inc -786 if rj >= -5\nr inc -66 if ku != 10\nk dec 422 if av > 800\nme inc 344 if mr == 372\nfk dec 479 if eva != -10\np inc 908 if mr >= 370\nd inc -29 if ntq == 0\nmr dec -531 if njb >= -2427\neva dec -57 if f <= 2\neva dec 515 if uea != 3\nfk dec 540 if k > -427\nmr dec -144 if me < -580\nk dec -484 if njb >= -2437\ntn dec 534 if s < 8\nfk inc 91 if y < 1\ne dec -34 if e > -792\ny inc -151 if uea != 0\nme inc 259 if eva != -456\nfk inc 226 if tn >= -980\np dec 616 if rj == 0\ndou dec 249 if eva > -450\nk inc 204 if e != -760\nntq dec 474 if njb < -2434\nnjb inc 395 if ntq < -473\nmr dec 223 if uea == 0\npr inc -642 if ntq != -474\nf inc 438 if mr != 303\nku dec 570 if ku <= 5\nuc dec -560 if p <= 298\nmr inc 240 if s == 0\nme inc 908 if p < 297\npr inc -323 if pr >= 65\nme dec -365 if njb != -2050\nrj inc 344 if uc == 1467\nd dec -652 if uc > 1471\nme dec -300 if uc <= 1463\nr inc 130 if r == -72\nk inc -594 if ntq != -483\nl inc 584 if s >= -1\ns dec 129 if njb > -2045\nd inc 698 if njb >= -2042\np inc -284 if njb == -2042\nme inc 375 if d <= 662\nrj dec 132 if e > -748\nmr dec -435 if k < -327\nk dec -680 if p > 1\ny dec 560 if dou == -672\nfk inc 293 if k >= 351\nuea inc 718 if r > -74\nfk inc 802 if ntq > -481\ny dec 834 if ntq > -479\nr dec 659 if l < 221\np inc -537 if rj == 344\ndou dec -307 if fk == 673\nav inc 446 if l != 219\np dec 527 if y >= -1400\nk inc -538 if rj != 354\nuea dec -586 if tn != -972\nku inc 16 if fk >= 668\npr inc -791 if me <= 951\ns inc 610 if uc == 1467\nmr dec -642 if njb <= -2037\nuea dec -866 if ntq < -468\nav inc -627 if e < -744\nntq inc -779 if r != -75\nnjb inc -779 if rj >= 337\npr inc -886 if k >= -185\nmr inc -145 if njb == -2821\nuea inc 508 if mr <= 1461\nuea dec 707 if d <= 669\neva inc 92 if f == 438\ndou dec -52 if me >= 948\nuea inc -267 if fk <= 675\ny inc 314 if l >= 218\ny dec 469 if rj > 339\nl inc -796 if e == -752\ne inc 285 if uea < 613\nfk dec 283 if d == 669\ne inc -329 if e >= -461\nf inc 524 if ntq == -1253\nme dec 751 if uc >= 1465\nfk dec 85 if k >= -183\npr dec -593 if uc <= 1474\nk dec -495 if uc < 1477\np dec 470 if tn != -973\nav inc -579 if ku < -544\nku inc 587 if k != 313\nku inc -876 if eva > -373\nuea inc 432 if ntq == -1253\neva dec 740 if pr != -455\nme inc -718 if s <= 487\nme dec 643 if tn <= -966\ne dec -995 if tn < -968\nku inc -71 if av < 56\nl inc 184 if s > 477\nk dec 633 if r <= -66\ndou dec -335 if y <= -1542\nav inc 974 if eva < -1101\nr dec 140 if ku > -923\nr dec -205 if njb != -2820\neva inc -52 if e >= 526\neva inc -620 if ntq != -1253\np dec 700 if uea >= 1038\nrj dec 589 if s == 481\nmr inc 270 if ntq != -1250\ndou inc -576 if fk == 390\ne inc -684 if y != -1539\nnjb dec 867 if k <= -315\nmr inc -767 if rj >= -254\nf inc -207 if l <= -387\nntq inc -140 if r != -3\nme inc 185 if d > 659\ns inc -689 if uc >= 1460\nku inc 681 if ntq < -1384\nfk dec 394 if ku == -233\nku dec 988 if k <= -320\npr inc -256 if pr == -450\nme inc -57 if r < -10\nme inc 117 if fk <= -12\np inc 312 if dou > -559\ne dec -575 if eva != -1158\nd inc 107 if l >= -392\ntn inc 279 if mr > 964\nd inc -78 if uc >= 1461\nmr dec 273 if pr <= -711\ndou inc -156 if pr > -703\nrj inc 192 if pr >= -714\np dec 881 if d != 691\neva dec -870 if mr >= 963\nav inc 991 if l <= -387\ne dec -665 if me > -982\nuea dec 916 if uc == 1467\nf dec 893 if av >= 2011\ne inc -109 if njb < -3689\ntn inc -489 if eva > -280\nrj inc 143 if njb < -3680\nrj inc -5 if me == -978\ne inc -567 if k > -334\ntn dec -626 if av < 2020\nr inc 989 if e == -58\np dec 709 if fk >= -7\nk dec 459 if uea > 126\ne inc -311 if pr >= -708\nr inc 235 if njb < -3684\ny dec 788 if p < -3496\ns dec -649 if me >= -983\npr dec -319 if ntq < -1387\nuea dec -399 if me != -981\nuc dec -33 if f <= -136\ne inc -802 if uc == 1504\nku dec -808 if d != 689\nk inc -701 if uea != 530\nme inc -695 if fk == -4\nuc dec -84 if y != -2346\nme dec 404 if tn <= -60\nfk inc 316 if e == -361\nf dec 72 if k < -1017\np inc 1000 if uc <= 1590\nnjb inc -497 if dou != -553\nl inc -75 if l < -380\nav dec -826 if r > 1214\nntq dec -12 if ku == -420\npr inc -157 if e > -373\np inc 822 if pr != -541\ntn dec -718 if eva <= -288\ny dec -530 if tn <= 659\ntn inc 33 if p < -1689\ne inc 411 if e > -371\nmr dec 477 if me <= -2074\nrj inc 576 if k > -1034\ndou dec 965 if f <= -205\nav dec -17 if uc == 1584\nuc inc -277 if l != -459\nfk dec 293 if y < -1803\ne dec 596 if rj == 666\ny dec -125 if uea > 519\ne inc -496 if uc == 1307\ntn inc -145 if uea <= 526\np inc -91 if p != -1675\ntn inc -408 if e != -1040\neva dec -66 if ku < -419\nuc dec 98 if njb == -4185\nk inc 683 if k != -1016\nav dec 846 if ntq == -1394\ntn inc -955 if ntq != -1399\np inc 971 if av < 2850\ntn dec 755 if tn > -866\nme inc -708 if mr != 491\nl dec 508 if k == -342\nl dec -378 if k == -349\nuea dec 588 if me == -2078\ntn inc -334 if njb == -4185\npr dec 42 if uc == 1211\npr inc -6 if y != -1684\nl inc 702 if tn < -1943\ny dec 601 if l > -269\nf inc -242 if d != 704\nk dec -307 if fk != -292\nrj dec 284 if ku != -422\ndou inc 456 if p == -1773\nntq dec 410 if tn > -1955\nr inc 673 if d == 698\np inc 304 if f > -457\nrj inc 432 if tn > -1948\nku inc 962 if k == -35\ndou dec -228 if s > 437\nfk inc -132 if rj < 818\nl inc 513 if k < -26\nme inc 827 if uea != -62\ny dec 768 if ntq >= -1800\nf inc -143 if me < -1241\nfk dec 816 if pr == -550\nntq inc 447 if fk < -1242\nav dec -282 if k > -34\ns dec 427 if fk != -1245\np dec 998 if tn <= -1942\nrj dec -786 if av <= 2845\nuc dec -743 if f < -595\nuc inc 206 if eva <= -284\nfk inc -182 if s >= 448\nuea dec -784 if mr >= 489\nl inc -127 if av != 2858\ne dec -29 if njb == -4185\nl dec -412 if tn == -1946\nuea inc 388 if y >= -1685\nav inc 526 if ku < 557\nrj dec -240 if me <= -1254\nuea inc 990 if pr <= -553\nd inc -806 if av > 3376\ny dec 981 if ku == 549\ns dec 282 if ku <= 550\nl inc -241 if ku <= 552\nuc inc -243 if uea <= 1108\nuea dec 648 if dou < -827\ns inc 0 if f < -592\nf dec 776 if p > -2470\nrj inc -700 if y > -2669\nuea inc -988 if y < -2659\nr dec -132 if r == 1897\nuc dec -341 if me > -1253\nku dec 940 if mr <= 500\nd inc -595 if mr >= 491\np dec 785 if s < 166\nf dec -184 if ku > -395\nd inc -208 if s != 168\nfk dec -538 if av >= 3372\ny inc 204 if ntq != -1362\nr dec 868 if r < 1894\nrj inc 182 if av < 3386\ntn dec 183 if eva == -297\nl inc 596 if tn > -1945\nmr inc -207 if k >= -27\npr dec -857 if p == -3252\ns inc -937 if eva > -294\nntq inc -984 if ntq == -1356\nmr dec 713 if me > -1245\ntn dec -584 if eva > -296\nav inc 992 if ku == -391\nav inc 94 if dou != -836\nntq inc -611 if mr < 494\neva inc 313 if e > -1025\nku dec 940 if y == -2465\ne dec 15 if l <= 281\nuc inc -95 if rj < 299\npr inc -574 if mr < 497\npr inc 364 if rj >= 293\nmr dec 953 if s >= -787\nku dec 963 if uc >= 1665\nf inc 197 if y < -2452\nntq inc -400 if p < -3242\ns inc 490 if me > -1253\nd dec 390 if l >= 285\nd dec 965 if eva <= 29\ne dec -621 if eva > 23\np inc -344 if me != -1256\ny inc 903 if r >= 1895\np dec -994 if tn != -1360\ntn dec 80 if f > -998\nk inc -58 if rj == 296\nme dec 823 if dou == -835\ny inc -547 if njb > -4188\nd inc -459 if d == -2266\nd inc 4 if l >= 280\neva dec -975 if av >= 4460\nfk inc 350 if ku > -396\nrj dec 89 if rj == 286\nmr inc 10 if y != -2094\nrj dec -700 if tn >= -1447\np inc 151 if p != -2608\nku inc -983 if fk > -355\neva dec 106 if rj > 989\nme dec 46 if tn <= -1448\ns dec 476 if p == -2451\nnjb inc 671 if fk == -357\nnjb inc 687 if s == -764\nr dec 470 if ntq <= -3351\nfk inc 784 if rj >= 992\ny inc -573 if tn == -1442\nme inc -410 if ntq > -3355\ndou inc 951 if av != 4458\nd dec 555 if dou != 126\nntq inc 543 if fk != 434\ndou inc -175 if av > 4474\nuea inc 990 if ku == -391\ndou inc -10 if fk == 427\npr inc -274 if r != 1434\ndou dec 68 if p >= -2455\nku inc -46 if ntq != -2815\nme dec -76 if rj != 994\nr inc -40 if uea < 470\nnjb inc -446 if dou != 47\nku inc 552 if r > 1383\ntn inc 520 if f == -990\nf inc 975 if mr <= -452\nav dec -566 if k != -89\nme inc -578 if ntq < -2801\nrj inc 796 if rj >= 987\nuea inc -379 if ntq >= -2811\nku inc 618 if d > -3286\ns dec -990 if av == 5033\nnjb dec 612 if rj >= 1784\nav inc 92 if av != 5033\ns inc -904 if mr <= -450\ntn inc -214 if me > -2996\ne dec -887 if ku == 733\nav inc 580 if mr == -452\nuc inc -967 if rj == 1792\nnjb inc 221 if mr != -446\nf inc 670 if r <= 1389\nuc inc -132 if eva >= 892\nfk dec 669 if l >= 282\nuc inc 76 if p > -2457\ns inc 727 if rj == 1792\nuea inc 381 if d > -3274\nf inc 0 if me == -2986\nuea dec -208 if pr != -167\nuea dec -728 if l > 283\neva dec -853 if e < 480\ndou inc -561 if av <= 5620\ns dec -412 if s != 39\neva dec -816 if eva == 894\np dec -443 if me != -2989\nd dec 104 if fk < -240\nrj inc -56 if e != 489\ntn inc -147 if me != -2983\nav dec -308 if ntq != -2811\nfk dec 440 if l > 286\nrj inc 43 if me >= -2994\nd dec -517 if k <= -98\ntn inc -724 if mr <= -449\nav dec 595 if y < -2667\nrj inc -524 if s != 466\nuc inc 815 if l < 295\nf inc 75 if fk >= -690\nmr dec 898 if ku > 738\nd dec 598 if k > -92\ntn dec 844 if fk == -680\npr inc 885 if me == -2986\nuc dec -457 if l < 290\nfk dec 825 if eva > 1716\nuea inc 439 if pr > 704\nk inc -107 if k <= -89\nfk dec -711 if uc <= 1916\neva dec -412 if d == -3380\nnjb inc 303 if mr == -452\nmr dec -374 if y >= -2669\np inc 341 if njb == -3355\nf dec 538 if tn >= -2007\nr dec 672 if fk < 31\nme dec 442 if fk > 20\nd dec -768 if p < -2015\nrj inc 0 if s != 464\nme inc 67 if y <= -2670\nuea dec 889 if f == 192\neva inc 226 if s >= 461\np dec -646 if fk <= 20\nmr inc 153 if njb > -3366\nrj inc 240 if njb != -3365\nf dec -61 if fk < 38\npr dec 750 if pr >= 707\npr inc 656 if s > 458\nuc inc 366 if l > 284\np dec -574 if uc < 2279\nd dec -88 if ku <= 739\nl inc 466 if rj == 1495\nd dec -335 if ntq < -2806\ndou inc 708 if uea > 567\ne inc 702 if av >= 5323\nk dec 962 if s > 467\nd inc 665 if uc < 2284\nfk inc 849 if d < -2284\ny inc 761 if me < -3354\nnjb dec 30 if ntq != -2808\ne dec 67 if f >= 248\nmr inc -690 if mr <= -308\ne dec -40 if k == -200\npr inc -323 if av > 5320\npr dec -432 if y <= -1915\nf inc -832 if tn >= -2006\nnjb dec -555 if pr == 730\nntq dec 525 if rj <= 1499\nf inc -147 if mr < -290\nd inc -322 if njb > -3371\nmr inc 476 if s < 468\nf dec 125 if eva < 2349\nme dec -964 if njb < -3369\nntq inc 514 if dou >= 181\ns inc -491 if uc != 2276\nr dec -966 if fk != 878\ne inc -971 if f < -15\nuea dec -238 if uc < 2271\nd dec 193 if p == -1434\ne inc -98 if tn > -2011\nav inc -362 if d > -2812\nav dec 230 if r >= 709\ne dec 858 if d >= -2816\nav dec -370 if f != -12\nmr dec 820 if njb != -3370\nuc inc -376 if k >= -203\ns dec -3 if p <= -1431\nme dec -223 if uea >= 569\nfk dec 266 if uea > 563\npr inc 18 if njb > -3368\nku inc 143 if fk > 602\ns inc 917 if fk > 620\nf inc -128 if r >= 709\ndou dec -732 if f > -153\nuc dec -457 if njb != -3365\ny dec -318 if f == -147\nme dec 366 if f > -143\nr dec -203 if uea >= 577\nrj dec -430 if uea != 572\ntn inc 623 if mr >= -644\nmr dec 85 if njb > -3367\ndou inc 371 if njb != -3366\nrj dec -329 if p > -1444\ntn dec 659 if r >= 714\nl dec 336 if e != -765\np dec 233 if tn > -2046\nntq inc 432 if s >= 456\nl inc -743 if fk != 607\npr inc 749 if l < 12\nfk dec 754 if pr == 1482\neva inc 125 if tn == -2043\nf inc -693 if tn <= -2053\ne inc -812 if r != 714\ne inc -857 if njb == -3361\nd inc -182 if tn == -2043\nnjb inc 666 if fk <= 615\ns inc -187 if k != -202\nmr inc 933 if k != -204\neva inc 221 if f != -140\nl dec -146 if ntq > -2378\nk dec -504 if f == -147\ny inc 148 if me == -3138\ntn dec -800 if eva < 2700\nrj dec -458 if k >= 299\ny dec 290 if tn != -1237\neva dec 188 if r != 714\np inc -406 if r <= 719\ndou dec -730 if p < -2070\npr dec -422 if rj <= 2721\nfk inc -73 if rj > 2706\nav dec -196 if f < -144\ns inc 670 if uea <= 573\nrj dec -41 if av >= 5298\ndou inc -463 if k < 304\nl dec -248 if av <= 5300\nr dec -897 if av != 5304\nku inc 678 if k >= 303\ndou inc 773 if p == -2073\ntn dec 399 if me < -3129\ns dec 157 if p <= -2066\nuea dec 624 if rj > 2744\nk dec 451 if av < 5304\np dec -475 if uea == -54\nme inc 481 if uc >= 2356\nnjb dec 194 if k > -153\nr inc 517 if p <= -1593\neva inc -869 if fk > 545\nnjb inc 270 if y == -1739\ny dec 468 if pr >= 1909\ne dec 713 if eva != 2694\ns inc 973 if ku != 1550\nuea dec -325 if r > 2118\nmr dec 812 if l != 269\nf inc 146 if y == -2207\nnjb inc -476 if uea <= 278\neva inc -358 if y > -2210\nntq dec -904 if l > 257\nme dec -499 if ku == 1554\nme dec 38 if uc > 2350\nku inc -255 if rj != 2746\neva inc 389 if pr >= 1913\nku dec 76 if e <= -1620\nl dec -254 if ku <= 1231\ntn dec 880 if ku == 1229\np dec 516 if fk <= 541\nd inc -423 if rj >= 2760\np inc 145 if uea != 271\npr inc 755 if fk > 538\nav dec -945 if p > -2115\ny inc 974 if njb == -3100\nr inc -688 if tn > -1633\nuea dec 54 if d >= -2996\ndou inc -876 if fk >= 537\nuea dec 379 if mr == -607\nrj inc 760 if f != 3\nav inc 124 if pr != 2677\nmr dec -565 if eva > 2333\nk dec 382 if ku <= 1225\ns inc -856 if eva <= 2336\nk dec 357 if d != -2980\nfk dec -926 if y > -2208\nf inc -198 if r <= 2133\nl dec 582 if uc <= 2361\nme dec 288 if uc != 2362\nme inc 757 if dou >= 1908\ntn inc -697 if me == -1720\neva inc 776 if rj >= 3509\nav dec 944 if s != 911\nfk inc -310 if fk != 1457\nl inc 91 if d < -2984\nmr dec 296 if ntq == -1483\neva dec 151 if s <= 914\nnjb dec 122 if njb != -3095\ndou inc 252 if l == 16\npr inc -473 if tn > -1652\ntn dec 442 if me != -1720\nfk inc -24 if y < -2202\nk inc -818 if uc <= 2360\nku inc 597 if eva != 2966\nntq inc -970 if k != -1714\ny dec -747 if pr >= 2188\ns dec 876 if eva >= 2954\ntn dec 599 if k != -1713\nav dec 408 if pr != 2201\ne inc 676 if me < -1726\ns inc -515 if tn == -2683\ns dec -626 if s == -484\nl inc 745 if f <= -191\nnjb dec -458 if eva < 2971\nnjb inc 346 if s == 151\ntn dec -303 if ntq == -2453\nav inc 222 if ku >= 1821\nr inc 810 if uc >= 2357\nntq inc -490 if s != 132\nr dec -448 if rj >= 3509\neva dec 39 if me <= -1725\nl dec 510 if f <= -192\nmr dec -382 if f <= -198\nme dec -1 if ku == 1820\neva inc 366 if s == 143\nnjb dec -109 if e > -955\ne inc -386 if tn != -2380\npr dec 633 if rj == 3513\ny inc -556 if s == 142\ns inc 992 if p <= -2114\ntn dec 333 if eva != 2922\ntn dec -569 if k > -1709\neva inc 263 if rj != 3520\nntq dec -671 if r > 3393\nfk inc -686 if e < -942\ny inc 779 if pr > 1560\ns dec 257 if uc < 2360\ne inc 234 if pr != 1560\ne inc -299 if pr <= 1560\nk inc -58 if fk <= 454\ntn inc 534 if uc >= 2354\ne inc 404 if k > -1763\ntn inc -768 if dou < 1921\ntn inc 737 if eva >= 3193\nku dec -734 if tn > -2050\nnjb inc 956 if ntq < -2940\ntn inc 476 if p != -2120\ndou inc 958 if r == 3386\nfk inc -491 if e <= -308\nme dec -272 if l > 256\nntq inc 274 if pr >= 1556\ne dec -341 if p < -2113\ntn inc 983 if av < 5018\nrj dec 658 if tn == -586\nme dec 766 if dou != 2876\nme inc -116 if y < -1230\nl inc 849 if e <= 41\nk inc -241 if fk <= -39\nd dec 939 if me <= -2335\ntn inc 361 if k >= -1998\nk dec -345 if s > 868\ns dec 280 if uea == -162\nl dec -745 if pr > 1558\ndou dec -964 if d >= -3935\ns dec 189 if njb == -1572\nav inc -344 if me <= -2333\ne dec -453 if k < -1664\nd inc 968 if mr < 54\nrj inc 993 if fk < -45\nd inc -686 if f >= -197\ndou inc -485 if s < 411\ntn inc 206 if rj == 3848\nfk inc 241 if av != 4683\np dec 251 if l < 1858\ndou dec -750 if e > 24\np dec 917 if uc > 2354\nr dec 202 if r == 3386\ne dec 896 if uea == -162\nme dec -287 if me > -2331\nku dec -878 if d >= -2954\neva dec -313 if uc > 2352\nku inc -456 if av <= 4669\ne inc -551 if k == -1658\nr inc -328 if y > -1247\nuc dec 936 if rj == 3838\nr dec -204 if pr < 1559\nuc inc -138 if dou >= 4101\nntq inc -575 if dou != 4095\ne dec -413 if dou < 4107\ndou dec -609 if s <= 416\ntn dec -275 if r >= 2851\nfk inc 365 if av < 4680\nntq dec 898 if fk > 551\nmr dec 896 if l > 1842\nfk inc -304 if rj != 3848\nk dec -10 if av > 4672\ntn dec -427 if k != -1641\nfk inc -362 if fk != 566\nrj inc 163 if l == 1851\nfk inc 614 if uc < 2212\nku inc 942 if tn >= 319\ns dec -341 if rj <= 4016\nnjb inc -443 if r < 2857\nd inc 51 if ku != 3496\nrj dec 266 if s <= 753\ns inc 918 if ntq != -4151\nmr dec -363 if s == 1667\nntq dec 52 if uea != -162\nku inc -69 if rj == 3745\nuc inc -196 if r > 2863\nmr dec -200 if uc < 2229\nf inc 206 if rj < 3751\nfk inc 771 if dou < 4721\ne inc 880 if me <= -2333\nrj inc -811 if av >= 4667\ndou inc 447 if s < 1673\ntn dec -512 if l < 1842\nfk inc 535 if l > 1843\nf dec -837 if tn != 330\nav dec 588 if f <= 845\nnjb inc -988 if uea >= -164\nk inc 726 if l <= 1855\nl dec 297 if uea < -152\ntn inc -718 if rj >= 2925\nntq dec -902 if rj <= 2942\nl inc 532 if uea != -162\nrj dec 277 if d > -2968\nrj dec 628 if rj == 2657\nmr inc 709 if d != -2962\nav dec 951 if uc == 2219\nd inc 107 if tn < -401\nme inc 17 if e != -111\ne inc -927 if mr >= 422\nl dec 263 if k != -926\nuc dec 883 if ntq != -3241\np dec 752 if e == -130\ne inc 989 if r != 2853\nme inc -673 if ku < 3433\nf inc -143 if d > -2970\neva dec -905 if eva != 3498\nku dec 696 if njb != -2993\nr inc 424 if mr < 426\nuc inc -790 if e == 868\nme inc 577 if f > 692\nl inc 962 if eva < 3508\neva dec 626 if dou > 5153\npr dec 928 if rj == 2029\np dec 849 if rj >= 2021\nuc dec -297 if me < -2407\nuc dec -439 if p <= -4122\nnjb inc -800 if me != -2418\nuea inc -159 if ku > 2723\nk inc -71 if eva != 2867\neva dec 186 if eva != 2865\ns inc 131 if njb < -3798\np inc -913 if e > 861\nl dec 478 if y != -1237\nnjb dec 398 if d != -2967\nnjb inc 981 if dou <= 5159\nf inc 641 if d <= -2957\ny inc -277 if fk != 1495\nd inc -53 if ntq <= -3238\nr dec 570 if s >= 1791\ns inc 779 if r >= 2708\npr dec -890 if njb == -3220\nme inc 825 if ntq >= -3245\nr dec -738 if tn > -404\nk dec -544 if uc >= 1276\ndou dec -110 if s <= 2580\npr dec -882 if njb >= -3227\nmr dec 302 if k != -441\nnjb dec 500 if ntq <= -3237\nr inc -45 if uea != -320\nku dec 956 if pr < 2404\nfk inc -942 if eva < 2688\ns inc 834 if uea < -320\nrj inc -972 if ku > 2729\nd dec -99 if k == -449\nf dec -106 if ntq <= -3232\nuea dec 805 if uc <= 1291\nme dec 548 if f == 1448\nuc inc -898 if me == -2138\ny inc -403 if ntq <= -3232\ns dec -959 if dou > 5260\nuc dec -456 if l >= 2244\nk dec -448 if mr > 111\nr dec -854 if ku > 2727\npr inc -662 if pr == 2405\ne inc 838 if rj >= 1063\nl inc -807 if e != 869\nf inc -791 if l <= 1453\nntq dec -948 if av == 3134\np inc 567 if uea > -1130\nntq inc -88 if s != 4367\nnjb dec -665 if ntq <= -2379\nuc dec -243 if me == -2138\ne inc 588 if av <= 3130\nf inc 564 if s != 4368\nl dec 17 if e <= 860\nuc dec 358 if ntq == -2380\nk dec 29 if rj == 1057\ne dec 978 if e == 868\nk dec -424 if pr == 1743\nfk dec 502 if dou == 5268\nl dec -578 if tn != -396\nf inc 657 if av == 3134\neva dec 690 if uea > -1131\nrj inc -639 if f < 1870\ndou dec 545 if p <= -4476\ny dec 170 if l >= 1447\nav dec 872 if mr == 126\nk inc -51 if k < 396\nf dec 655 if mr >= 114\nku dec -952 if r <= 4250\ne inc -220 if k < 351\nme inc 254 if p <= -4484\nf dec -242 if d == -2908\nku inc 595 if r == 4257\nrj dec -66 if s != 4360\neva dec 944 if s > 4367\nku dec -240 if njb > -3059\nf inc -693 if ku < 3560\ne inc -328 if eva > 1042\nl dec -773 if dou <= 4714\nav dec -367 if k >= 337\npr inc -639 if av <= 3496\nd inc 804 if e != -663\nfk inc 217 if d > -2117\nl dec -995 if d < -2117\ntn inc 667 if p != -4479\nr inc 620 if r > 4258\ny dec -226 if p > -4483\nuc dec -427 if uc == 719\ndou inc -716 if p != -4475\nmr inc 788 if njb >= -3059\nl dec -433 if l <= 1454\nl inc -139 if tn < 278\nfk inc 224 if d != -2118\nav dec -164 if av < 3502\npr inc 427 if rj < 1132\ny dec 148 if f != 1220\nd inc -514 if tn != 271\ndou dec 971 if k <= 347\nuea inc 140 if rj <= 1122\nfk dec 243 if rj >= 1123\ns inc 666 if e > -666\nntq dec -438 if eva >= 1058\nrj inc -521 if njb >= -3064\nfk inc 286 if e == -658\ne inc -408 if p > -4484\ndou dec 429 if f <= 1230\nfk dec 722 if pr > 2164\nrj dec 277 if k < 351\nd dec 648 if fk >= -185\nl inc 761 if s != 5036\nl inc -417 if uc > 715\ndou inc 842 if k >= 343\nk inc -810 if fk > -188\np dec -962 if e != -1059\np inc -953 if av < 3674\nr inc -217 if r != 4261\ntn dec -726 if rj == 325\ny dec -443 if k <= -469\neva dec -246 if e > -1076\nfk inc 950 if dou != 3459\nrj dec -905 if e >= -1057\ntn inc 663 if f == 1223\nmr dec 991 if eva < 1307\nuc dec 45 if tn == 1660\ndou dec 298 if l == 1323\nr inc 324 if rj > 325\nme dec 26 if pr < 2173\neva dec -672 if f < 1232\ne inc -991 if rj > 316\npr inc 751 if y <= -1832\nrj dec 421 if uea == -1126\nme inc -169 if k < -457\ny dec 335 if r <= 4031\nuea dec 677 if y < -1848\ny dec 56 if me != -2339\nr dec -579 if av <= 3671\nk dec -479 if ku == 3576\nav inc -690 if ku >= 3565\nr dec -574 if av == 2975\ntn inc 443 if av != 2981\neva inc -180 if uea < -1116\nntq dec 726 if me < -2331\nmr inc 879 if l >= 1318\nf inc 838 if r > 5191\nfk inc 582 if k < -461\nrj dec -663 if dou >= 3145\nk dec -725 if k <= -458\nme inc -590 if y != -1885\nav inc 206 if rj <= 557\ns inc 941 if uc < 678\nuc dec 951 if uea >= -1135\ntn inc 770 if njb > -3057\nmr inc -619 if me < -2921\neva inc 682 if av == 2975\nuea dec 682 if pr <= 2927\ns dec 951 if ku != 3570\nrj dec 274 if pr <= 2924\nku inc 526 if uea > -1811\ne inc 153 if me == -2923\nfk dec -398 if r == 5189\nuc dec -768 if dou >= 3153\npr dec -375 if p == -4468\nrj dec -161 if pr != 3291\nme dec 227 if dou == 3151\nav inc -845 if ntq == -3106\np dec -595 if pr == 3296\nku dec 847 if ntq != -3096\nme inc -60 if f > 2059\ns dec -811 if k > 255\ndou dec 588 if uc > -274\nf inc -535 if eva <= 2481\ns inc -759 if tn >= 2871\nrj inc 375 if s <= 4139\nuc inc 659 if av < 2127\nf inc -240 if f == 1526\ns inc 453 if d == -2755\nnjb inc 789 if e > -1913\nfk inc 570 if njb <= -2258\nnjb dec 579 if rj >= 826\ns inc 638 if fk < 1928\nf dec 648 if fk == 1924\nntq dec -217 if ku >= 3244\nntq dec 919 if me > -3214\nav inc -450 if me != -3213\nk inc -306 if ntq == -3808\nr inc -902 if uea <= -1803\nl inc 319 if fk != 1915\nmr dec -785 if av > 1679\nd dec 222 if l == 1636\nf dec 89 if mr >= 960\neva dec 839 if f > 540\nku dec -894 if e < -1894\nd inc 463 if p < -3874\nr dec 0 if fk >= 1923\nntq inc 276 if uc > -274\nav dec -90 if e != -1905\neva inc 742 if eva == 1633\nl inc 417 if l < 1652\nr dec -223 if njb != -2835\ndou dec 931 if tn < 2877\nk dec -470 if ku >= 4137\nme dec 332 if r != 4507\np dec -543 if uea < -1812\nmr inc 339 if tn <= 2873\nd inc 818 if r == 4514\nku dec -584 if l == 2059\nk dec 852 if mr != 1308\ns dec 406 if r < 4524\ns inc 477 if pr <= 3305\npr inc -867 if dou != 1634\npr inc 155 if f < 556\nd inc 444 if y == -1895\nuc inc 641 if s != 4842\ne inc 378 if njb != -2847\nku dec 489 if ntq < -3527\nl inc -501 if me <= -3534\ne inc -660 if k > -440\nme dec 512 if d <= -1492\ny dec 410 if y < -1886\nk inc 666 if tn < 2878\nk inc 744 if ku < 4244\nr inc 105 if eva > 2383\nmr inc 930 if uea <= -1811\np dec -907 if e >= -2195\nfk inc 835 if e <= -2178\ns inc 85 if p == -2966\nuea inc 680 if d < -1497\ntn dec 524 if l >= 1555\ny dec 195 if eva < 2379\nl inc -333 if tn == 2349\nuea dec 478 if av <= 1766\nav dec -174 if pr >= 2579\nnjb dec -255 if fk <= 2761\nrj inc 509 if e < -2184\nr dec 309 if s < 4937\nuea inc 757 if s >= 4924\nuea dec -456 if eva < 2378\nfk inc 919 if fk < 2763\neva dec 808 if mr > 1295\ne dec 475 if uea == -595\nfk dec -277 if s == 4935\nr inc 822 if p != -2962\nk dec 440 if l != 1234\npr inc -222 if r > 5027\neva inc -437 if njb == -2590\nuc inc 297 if mr <= 1308\nr dec -350 if ntq != -3533\neva inc -550 if me >= -4049\ny dec 587 if p < -2957\nrj dec -371 if l > 1228\ntn inc 353 if tn == 2356\ndou dec 888 if uea >= -603\nme inc 816 if pr == 2584\nuc dec 473 if tn < 2351\nav dec -804 if y <= -3085\nd dec -824 if uc > 202\nav inc -860 if s >= 4939\nntq inc 168 if uea > -596\ntn dec 50 if mr > 1297\nntq dec -852 if uea <= -588\nl inc -732 if r >= 5376\nav dec 301 if k >= 531\nr dec -396 if eva > 1120\npr inc 664 if dou <= 747\ny inc -176 if av != 2446\neva dec -431 if uc == 194\ntn dec -735 if p > -2975\nd dec 70 if p != -2963\nfk dec 100 if fk <= 3687\ntn dec 99 if s >= 4923\nku inc -68 if s < 4939\nku dec -81 if l < 485\ns dec -576 if njb <= -2598\ne dec -439 if k >= 536\nl inc -899 if ku > 4167\ntn dec 639 if av < 2454\nnjb dec 121 if mr >= 1299\ndou dec -218 if uea < -586\ntn dec -161 if e == -2220\ns dec -132 if rj >= 1334\nr inc 700 if dou != 962\nl inc -525 if fk >= 3576\ny inc -102 if d == -1560\nku inc -843 if ntq == -2512\neva inc -318 if me != -3248\ne inc 618 if uea > -597\np inc -598 if fk < 3587\nr inc -537 if pr != 3248\nd dec 858 if dou <= 970\nuea dec -521 if y != -3268\npr dec 870 if ku > 3330\np dec 293 if ku == 3323\nntq dec 363 if f < 543\ntn inc -855 if ku >= 3320\nav inc 438 if uc == 194\nrj inc -649 if pr > 3248\ny inc 76 if p > -3867\ne dec 303 if fk > 3569\nuea dec -500 if av > 2875\nrj dec 571 if d > -2426\nrj inc 771 if pr != 3240\nk inc -815 if f < 559\nav inc -430 if s < 5071\nk inc -404 if uc < 200\nl inc 372 if f < 552\nmr dec -133 if f != 553\ns inc 949 if y >= -3196\nfk dec -576 if ntq != -2514\ny dec 373 if ntq == -2512\ne inc -223 if ku >= 3325")
(def INPUT_LINES (str/split INPUT_TEXT #"\n"))
(assert (= 6012 (max-register-at-the-end INPUT_LINES)))
(assert (= 6369 (max-register-ever INPUT_LINES)))

