(ns advent-2017-clojure.day5
  (:require [clojure.string :as str]))

(defn escape-from-maze "Walks through the maze, changing the previous value based on the supplied function"
  [maze replace-fun]
  (loop [pos 0, m maze, steps 0]
    (if (>= pos (count m))
      steps
      (recur (+ pos (m pos))
             (assoc m pos (replace-fun (m pos)))
             (inc steps)))))

(defn simple-maze-walk [maze] (escape-from-maze maze inc))
(defn complex-maze-walk [maze] (escape-from-maze maze #(if (>= % 3) (dec %) (inc %))))

; Test cases
(def TEST_MAZE [0 3 0 1 -3])
(assert (= 5 (simple-maze-walk TEST_MAZE)))
(assert (= 10 (complex-maze-walk TEST_MAZE)))

; Actual data
(def INPUT_DATA "2\n1\n-1\n-2\n0\n-1\n1\n-1\n-7\n-6\n1\n-4\n-1\n-12\n-7\n-3\n-12\n-5\n-6\n-13\n-7\n-17\n-13\n-11\n-3\n-7\n-3\n-2\n-6\n-27\n-20\n-15\n-23\n-23\n-33\n0\n-10\n-35\n-29\n-6\n-10\n-5\n-20\n-38\n-30\n-38\n-12\n-23\n1\n-4\n-48\n-45\n-1\n-30\n-38\n-27\n-23\n-53\n-36\n0\n-3\n-45\n-32\n-39\n-32\n-46\n-23\n-40\n-10\n-54\n-38\n-37\n-44\n1\n-56\n-11\n-74\n-41\n-73\n-34\n-31\n-42\n-49\n-75\n-8\n-48\n-49\n-82\n-21\n-58\n-40\n-75\n-66\n-31\n-34\n-35\n-52\n-23\n-56\n-58\n-60\n-18\n-34\n-50\n-27\n-1\n-3\n-6\n-70\n-93\n-36\n-15\n-1\n-51\n0\n-110\n-7\n-7\n-56\n-14\n-66\n-93\n-56\n-100\n-19\n-54\n-79\n-81\n-19\n-112\n-13\n-24\n-40\n-90\n-8\n-10\n-14\n-27\n-62\n-45\n-137\n-53\n-53\n-89\n-48\n-86\n-139\n-91\n-146\n-109\n-52\n-6\n-32\n-6\n-113\n-78\n-12\n-4\n-113\n-42\n-145\n-23\n-64\n-97\n-98\n-77\n-155\n-133\n-65\n-64\n-59\n-164\n-155\n-27\n-65\n-57\n-133\n-140\n-95\n-104\n-46\n-16\n-139\n-55\n-15\n-26\n-63\n-141\n-93\n-146\n-51\n-104\n-84\n-82\n-87\n-149\n-19\n-77\n-154\n-118\n-96\n-117\n-96\n-140\n-47\n-188\n-158\n-141\n-192\n-63\n-58\n-191\n-63\n-52\n-135\n-142\n-109\n-42\n-134\n-4\n-11\n-135\n-13\n-24\n-39\n-4\n-183\n-158\n-25\n-136\n-35\n-49\n-54\n-78\n-18\n-92\n-19\n-142\n-40\n-237\n-119\n-147\n-198\n-132\n-73\n-238\n-106\n-82\n-51\n-72\n-9\n-44\n-151\n-164\n-35\n-74\n-252\n-219\n-40\n-154\n-229\n-169\n-130\n-238\n-64\n-171\n-174\n-161\n-67\n-205\n-160\n-112\n-191\n1\n-60\n-147\n0\n-43\n-67\n-190\n-256\n-66\n-189\n-76\n-86\n-91\n-243\n-10\n-142\n-163\n-52\n-112\n-162\n-169\n-269\n-98\n-188\n-282\n-212\n-286\n-28\n-33\n-6\n-114\n-89\n-237\n-90\n-95\n-202\n-266\n-72\n-215\n-50\n-52\n-78\n-286\n-32\n-235\n-7\n-56\n-194\n-6\n-32\n-73\n-48\n-77\n-69\n-43\n-279\n-236\n-79\n-286\n-105\n-295\n-61\n-320\n-130\n-99\n-90\n-238\n-294\n-120\n-9\n-302\n-327\n-165\n-267\n-228\n-250\n-153\n-28\n-126\n-187\n-138\n-163\n-140\n-26\n-217\n-197\n-180\n-338\n-39\n-71\n-6\n-56\n-151\n-272\n-276\n-246\n-189\n-183\n-38\n-249\n0\n-185\n-8\n-193\n-213\n-296\n-3\n-340\n-76\n-97\n-87\n-1\n-172\n-235\n-38\n-274\n-169\n-70\n-162\n-320\n-78\n-222\n-69\n-222\n-219\n-213\n-313\n-179\n-182\n-253\n-135\n-206\n-54\n-167\n-101\n-397\n-367\n-54\n-143\n-147\n-156\n-293\n-144\n-47\n-254\n-169\n-307\n-223\n-339\n-398\n-414\n-23\n-107\n-235\n-302\n-321\n-111\n-167\n-345\n-55\n-64\n-315\n-266\n-191\n-265\n-248\n-426\n-47\n-409\n-212\n-212\n-401\n-87\n-389\n-146\n-97\n-65\n-286\n-447\n-168\n-26\n-371\n-153\n-297\n-285\n-164\n-215\n-336\n-14\n-416\n-278\n-233\n-234\n-392\n-113\n-80\n-237\n-342\n-85\n0\n-145\n-75\n-101\n-88\n-292\n-285\n-344\n-254\n-47\n-310\n-227\n-60\n-320\n-102\n-364\n-131\n-338\n-17\n-239\n-124\n-266\n-380\n-421\n-217\n-311\n-287\n-233\n-223\n-242\n-16\n-326\n-407\n-482\n-470\n-247\n-365\n-75\n-278\n-44\n-404\n-195\n-348\n-81\n-309\n-181\n-176\n-97\n-274\n-204\n-485\n-458\n-364\n-22\n-89\n-448\n-235\n-53\n-50\n-510\n-89\n-114\n-158\n-199\n-189\n-204\n-528\n-278\n-274\n-149\n-208\n-485\n-313\n-325\n-246\n-173\n-478\n-164\n-153\n-76\n-407\n-447\n-109\n-334\n-199\n-50\n-361\n-449\n-338\n-409\n-66\n-282\n-510\n-288\n-380\n-562\n-543\n-534\n-500\n-288\n-526\n-439\n-142\n-284\n-421\n-30\n-243\n-185\n-433\n-326\n-102\n-540\n-391\n-197\n-580\n-305\n-436\n-559\n2\n-30\n-204\n-97\n-204\n-207\n-79\n-329\n-157\n-284\n-581\n-182\n-458\n-232\n-111\n-352\n-601\n0\n-245\n-292\n-167\n-549\n-456\n-277\n-63\n-104\n-493\n-585\n-369\n-121\n-122\n-180\n-466\n-509\n-405\n-53\n-555\n-454\n-549\n-486\n-80\n-463\n-385\n-538\n-274\n-75\n-90\n-500\n-434\n-167\n-142\n-587\n-92\n-182\n-95\n-205\n-49\n-574\n-352\n-638\n-204\n-25\n-375\n-456\n-400\n-572\n-37\n-151\n-81\n2\n-19\n-579\n-106\n-344\n-339\n-188\n-517\n-12\n-403\n-623\n-619\n-429\n-53\n-227\n-11\n-548\n-426\n-115\n-481\n-425\n-9\n-43\n-209\n-145\n-168\n-241\n-331\n-521\n-77\n-642\n-397\n-37\n-98\n-333\n-281\n-162\n-361\n-119\n-696\n-440\n-663\n-347\n-295\n-692\n-32\n-331\n-623\n-275\n-646\n-517\n-16\n-193\n-537\n-403\n-75\n-607\n-74\n-393\n-333\n-665\n-448\n-419\n-119\n-213\n-635\n-668\n-178\n-46\n-175\n-537\n-160\n-467\n-271\n-594\n-240\n-262\n-666\n-205\n-48\n-319\n-738\n-240\n-697\n-685\n-711\n-98\n-134\n-28\n-731\n-317\n-319\n-288\n-236\n-425\n-401\n-625\n-638\n-496\n-23\n-751\n-643\n-382\n-717\n-269\n-275\n-764\n-672\n-758\n-605\n-530\n-244\n-526\n-357\n-175\n-667\n-282\n-551\n-642\n-83\n-116\n-751\n-381\n-447\n-266\n-297\n-88\n-575\n-246\n-189\n-662\n-450\n-91\n-471\n-209\n-609\n-151\n-630\n-345\n-625\n-743\n-377\n-789\n-56\n-370\n-250\n-661\n-792\n-560\n-585\n-231\n-673\n-725\n-194\n-317\n-455\n-234\n-282\n-516\n-784\n-2\n-652\n-427\n-31\n-755\n-527\n-725\n-47\n-606\n-210\n-172\n-773\n-819\n-636\n-348\n-376\n-700\n-727\n-156\n-574\n-414\n-34\n-439\n-413\n-604\n-648\n-381\n-529\n-82\n-736\n-816\n-595\n-352\n-417\n-836\n-691\n-660\n-464\n-314\n-748\n-698\n-49\n-97\n-721\n-294\n-441\n-446\n-415\n-187\n-212\n-506\n-550\n-131\n-231\n-637\n-334\n-853\n-383\n-407\n-219\n-518\n-743\n-83\n-773\n-162\n-570\n-611\n-574\n-355\n-56\n-775\n-663\n-131\n-357\n-560\n-335\n-390\n-667\n-516\n-897\n-752\n-786\n-246\n-893\n-693\n-692\n-647\n-422\n-361\n-148\n-231\n-775\n-62\n-404\n-783\n-387\n-559\n-703\n-403\n-776\n-588\n-633\n-831\n-779\n-23\n-216\n-381\n-287\n-517\n-402\n-814\n-756\n-646\n-535\n-270\n-282\n-157\n-367\n-356\n-925\n-333\n-375\n-469\n-931\n-347\n-455\n-942\n-815\n-311\n-690\n-65\n-691\n-64\n-361\n-409\n-886\n-488\n-303\n-806\n-73\n-653\n-356\n-71\n-523\n-370\n-685\n-526\n-528\n-519\n-179\n-762\n-652\n-388\n-568\n-296\n-601\n-822\n-656\n-258\n-304\n-670\n-731\n-352\n-82\n0\n-116\n-294\n-652\n-702\n-933\n-12\n-348\n-15\n-662\n-311\n-695\n-357\n-872\n-847\n-791\n-129\n-574\n-281\n-42\n-626\n-36\n-60\n-864\n-871\n-246\n-943\n-500\n-253\n-684\n-545\n-1011\n-330\n-666\n-468\n-780\n-596\n-872\n-812\n-924\n-836\n-379\n-528\n-464\n-99\n-675\n-317\n-58\n-641\n-590\n-227\n-296\n-303\n-798\n-39\n-824\n-300\n-469\n-251\n-182\n-40\n-115\n-997\n-572\n-743\n-13\n-557\n-542\n-832\n-884\n-385\n-224\n-932\n-757\n-405\n-690\n-745\n-1008\n-657\n-846\n-565\n-508\n-792\n-245\n-298\n-793\n-278")
(def INPUT_MAZE (vec (map #(Integer/parseInt %) (str/split-lines INPUT_DATA))))

(assert (= 358309 (simple-maze-walk INPUT_MAZE)))
(assert (= 28178177 (complex-maze-walk INPUT_MAZE)))
