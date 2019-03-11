1.least substring K distinct characters substrings of size K
    public int getAns(String s, int k) {
        // Write your code here
        int count = 1;
        int l = 1;
        for(int i = 1; i < s.length(); i ++) {
            if (s.charAt(i) != s.charAt(i - 1) || l >= k) {
                l = 1;
                count ++;
            } else {
                l ++;
            }
        }
        return count;
    }

2. Number of restaurants
    public List<List<Integer>> nearestRestaurant(List<List<Integer>> restaurant, int n) {
        // Write your code here
        List<List<Integer>> result = new ArrayList<>();
        if (restaurant == null || restaurant.size() < n || n == 0) return result;
        
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>(n , (List<Integer> a, List<Integer> b) -> (caldis(b) - caldis(a)));
        
        for (int i = 0; i < restaurant.size(); i++) {
            if (pq.size() >= n) {
                if (caldis(pq.peek()) > caldis(restaurant.get(i))) {
                    pq.poll();
                    pq.add(restaurant.get(i));
                }
            } else {
                pq.add(restaurant.get(i));
            }
        } 
        int farstest = caldis(pq.peek());
        for (int i = 0; i < restaurant.size() && result.size() < n; i++) {
            if (caldis(restaurant.get(i)) <= farstest) {
                result.add(restaurant.get(i));
            }
        }
        return result;
        
    }
    
    private int caldis(List<Integer> coord) {
        return coord.get(0) * coord.get(0) + coord.get(1) * coord.get(1);
    }

3. K-Substring with K different characters
    public int KSubstring(String stringIn, int K) {
        if(stringIn == null || stringIn.length() < K) return 0;
        int res = 0;
        int freq[] = new int[256];
        Set<String> set = new HashSet<>();//不能有重复结果，所以得用个set存一下

        for(int i = 0; i < stringIn.length() - K + 1; i++) {
            int count = 0;
            Arrays.fill(freq,0);
            for(int j = i; j < i + K; j++) {  //j < k
                if(freq[stringIn.charAt(j)] == 0) {
                    count++;
                }
                freq[stringIn.charAt(j)]++;
                if(count == K) {
                    String s = stringIn.substring(i, j + 1);
                    if(!set.contains(s)) {
                        res++;
                        set.add(s);
                    }
                }
            }
        }
        return res;
    }

4. K Closest Points
    public Point[] kClosest(Point[] points, Point origin, int k) {
        // write your code here
        Point[] res = new Point[k];
        PriorityQueue<Point> pq = new PriorityQueue<>(k, new Comparator<Point>(){
            public int compare(Point a, Point b) {
                return compareDistance(a, b, origin);
            }
        });
        
        for (int i = 0; i < points.length; i ++) {
            if (pq.size() >= k) {
                if (compareDistance(pq.peek(), points[i], origin) < 0) {
                    pq.poll();
                    pq.add(points[i]);
                }
            } else {
                pq.add(points[i]);
            }
        }
        
        for (int i = k - 1; i >= 0; i --) {
            res[i] = pq.poll();
        }
        return res;
    }
    
    public int getDis(Point a, Point b) {
        int xd = a.x - b.x;
        int yd = a.y - b.y;
        return xd * xd + yd * yd;
    }
    
    public int compareDistance(Point a, Point b, Point origin) {
        int disA = getDis(a, origin);
        int disB = getDis(b, origin);
        if (disA != disB) {
            return disB - disA;
        } else {
            if (a.x != b.x) {
                return b.x - a.x;
            } else {
                return b.y - a.y;
            }
        }
    }

5. max pair Id和Usage K作为limit   飞行器配送 来回
    public int[][] getAns(int[] a, int[] b, int x) {
        // Write your code here.
        Arrays.sort(a);
        Arrays.sort(b);
        List<int[]> result = new ArrayList<>();
        int curMax = Integer.MIN_VALUE;
        int index1 = 0, index2 = b.length - 1;
        while (index1 < a.length && index2 >= 0) {
            if (index1 != 0 && a[index1] == a[index1 - 1]) {
                index1++;
                continue;
            }
            if (index2 != b.length - 1 && b[index2] == b[index2 + 1]) {
                index2--;
                continue;
            }
            int sum = a[index1] + b[index2];
            if (sum > x) {
                index2--;
            } else {
                if (sum > curMax) {
                    result = new ArrayList<int[]>();
                    result.add(new int[]{a[index1], b[index2]});
                    curMax = sum;
                } else if (sum == curMax) {
                    result.add(new int[]{a[index1], b[index2]});
                    //index2--;
                }
                index1++;
            }
        }
        return result.toArray(new int[result.size()][]);
    }

6. BST Node Distance
class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    
}
public class Solution {
    /**
     * @param numbers: the given list
     * @param node1: the given node1
     * @param node2: the given node2
     * @return: the distance between two nodes
     */
    public int bstDistance(int[] numbers, int node1, int node2) {
        // Write your code here
        if (numbers == null || numbers.length < 2) {
            return -1;
        }
        
        boolean has1 = false;
        boolean has2 = false;
        
        TreeNode root = new TreeNode(numbers[0]);
        if (numbers[0] == node1) has1 = true;
        if (numbers[0] == node2) has2 = true;
        
        for (int i = 1; i < numbers.length; i ++) {
            if (numbers[i] == node1) has1 = true;
            if (numbers[i] == node2) has2 = true;
            constructTree(root, numbers[i]);
        }
        
        if(!has1 || !has2) return -1;
        
        //find lca
        TreeNode LCA = findLCA(root, node1, node2);
        
        // get distance
        return getDistance(LCA, node1) + getDistance(LCA, node2);
    }
    
    public int getDistance(TreeNode root, int node){
        if(root.val == node) return 0;
        if(root.val < node) {
            return getDistance(root.right, node) + 1;
        } else {
            return getDistance(root.left, node) + 1;
        }
    }
    
    public TreeNode findLCA(TreeNode root, int node1, int node2){
        if(node1 < root.val && node2 < root.val)
            return findLCA(root.left, node1, node2);
        if(node1 > root.val && node2 > root.val)
            return findLCA(root.right, node1, node2);
        else 
            return root;
    }
    
    public void constructTree(TreeNode root, int val) {
        if (val < root.val) {
            if (root.left == null) {
                root.left = new TreeNode(val);
            } else {
                constructTree(root.left, val);
            }
        } else {
            if (root.right == null) {
                root.right = new TreeNode(val);
            } else {
                constructTree(root.right, val);
            }           
        }
    }
}


 放电影
7. Aerial Movie In order to prevent passengers from being too bored during the flight, LQ Airlines decided to play two movies during the flight.
    public int[] aerial_Movie(int t, int[] dur) {
        // Write your code here
        Arrays.sort(dur);
        t -= 30;
        int film1 = 0;
        int film2 = 0;
        int minDiff = Integer.MAX_VALUE;
        for (int lo = 0, hi = dur.length - 1; lo < hi; ) {
            int duration = dur[lo] + dur[hi];
            int diff = t - duration; 
            if (duration < t) {
                if (diff < minDiff) {
                    film1 = dur[lo];
                    film2 = dur[hi];      
                    minDiff = diff;
                }
                while (lo < hi && dur[lo] == dur[lo + 1]) lo++; // skip duplicates
                lo++;
            } else if (duration > t) {
                while (lo < hi && dur[hi] == dur[hi - 1]) hi--; // skip duplicates
                hi--;
            } else {
                film1 = dur[lo];
                film2 = dur[hi];
                break;
            }
        }
        return new int[] {film1, film2};
    }

// 8. Golf event  0 represents the obstacle can't be reached. 
// 1 represents the ground can be walked through.
class Point {
    int x, y, val;
    public Point(int a, int b, int v) {
        x = a; y = b; val = v;
    }
}


public class Solution {
    /**
     * @param forest: a list of integers
     * @return: return a integer
     */
    
    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
     
    public int cutOffTree(List<List<Integer>> forest) {
        // write your code here
        int steps = 0;
        if (forest == null || forest.size() == 0 || forest.get(0).size() == 0) {
            return steps;
        }
        //list for finding all trees and then sort, get the smallest one
        List<Point> trees = new ArrayList<>();
        //construct grid
        int[][] grid = new int[forest.size()][forest.get(0).size()];
        for (int i = 0; i < forest.size(); i++) {
            for (int j = 0; j < forest.get(i).size(); j++) {
                grid[i][j] = forest.get(i).get(j);
                if (grid[i][j] > 1) {
                    trees.add(new Point(i, j, grid[i][j]));
                }
            }
        }
        //sort
        Comparator<Point> comparator = new Comparator<Point>() {
            public int compare(Point a, Point b) {
                return a.val - b.val;
            }
        };
        Collections.sort(trees, comparator);
        
        //start from (0, 0), then cut all trees, count the steps
        int i = 0, j = 0;
        for (Point p : trees) {
            int step = bfs(grid, i, j, p.x, p.y);
            if (step == -1) {
             //System.out.println(p.x + " " + p.y + " " + p.val);
                return -1;
            }
            steps += step;
            grid[p.x][p.y] = 1;
            i = p.x; j = p.y;
        }
        
        return steps;
    }
    
    public int bfs(int[][]grid, int si, int sj, int ei, int ej) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{si, sj});
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        visited[si][sj] = true;
        int step = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.remove();
                if (cur[0] == ei && cur[1] == ej) return step;
            
                for (int k = 0; k < 4; k++) {
                    int nx = cur[0] + dx[k];
                    int ny = cur[1] + dy[k];
                    if (nx == ei && ny == ej) return step + 1;
                //inside bound && not visited && istree
                    if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && visited[nx][ny] == false && grid[nx][ny] >= 1) {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                    }   
                }
            }
            step++;
        }
        return -1;
    }
}

9. Can Reach The Endpoint
Given a map size of m*n, 1 means space, 0 means obstacle, 9 means the endpoint.
You start at (0,0) and return whether you can reach the endpoint.
public class Solution {

    class Coordinate {
        int x, y;
        Coordinate (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public boolean reachEndpoint(int[][] map) {
        if (map == null || map.length == 0) {
            return false;
        }
        
        int[] directionX = {1, 0, -1, 0};
        int[] directionY = {0, 1, 0, -1};
        
        Queue<Coordinate> queue = new LinkedList<>();
        queue.offer(new Coordinate(0,0));

        while (!queue.isEmpty()) {
            Coordinate coor = queue.poll();
            if (map[coor.x][coor.y] == 9) {
                return true;
            }
            
            for (int i = 0; i < 4; i++) {
                int x = coor.x + directionX[i];
                int y = coor.y + directionY[i];
                Coordinate c = new Coordinate(x, y); 
                if (isValid(map, x, y)) {
                    queue.offer(c);
                    // 0means we have tranversed it
                    if (map[x][y] == 1)
                        map[x][y] = 0;
                }
            }
        }
        
        return false;
    }
    
    private boolean isValid(int[][] map, int x, int y) {
        int m = map.length;
        int n = map[0].length;
        return x >= 0 && x < m && y >= 0 && y < n && map[x][y] != 0;
    }
}

10. Log file
public class Solution {
    /**
     * @param logs: the logs
     * @return: the log after sorting
     */
    public String[] logSort(String[] logs) {
        // Write your code here
        List<String> component2 = new ArrayList<>();
        List<String[]> component1 = new ArrayList<>();
        
        for (int i = 0; i < logs.length; i++) {
            String[] temp = parseString(logs[i]);
            if (temp.length == 1) {
                component2.add(temp[0]);
            } else {
                component1.add(temp);
            }
        }
        Collections.sort(component1, new Comparator<String[]>() {
            public int compare(String[] list1, String[] list2) {
                if (list1[1].equals(list2[1])) {
                    return list1[0].compareTo(list2[0]);
                } else {
                    return list1[1].compareTo(list2[1]);
                }
            }
        });
        String[] result = new String[logs.length];
        for (int i = 0; i < component1.size(); i++) {
            result[i] = component1.get(i)[2];
        }
        for (int i = 0; i < component2.size(); i++) {
            result[component1.size() + i] = component2.get(i);
        }
        return result;
    }
    
    public String[] parseString(String str) {
        int id = str.indexOf(' ');
        for (int i = id + 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ') {
                if (c <= '9' && c >= '0') {
                    return new String[]{str};
                } else {
                    String idStr = str.substring(0, id);
                    String content = str.substring(id + 1, str.length());
                    return new String[]{idStr, content, str};
                }
            }
        }
        return new String[]{"ggggg"};
    }
}

11. Most frequent word
public class Solution {
    /**
     * @param s: a string
     * @param excludewords: a dict
     * @return: the most frequent word
     */
    public String frequentWord(String s, Set<String> excludewords) {
        // Write your code here
        Map<String, Integer> map = new HashMap<>();
        
        String[] strList = s.split("\\W+");
        
        String rst = "";
        int max = 0;
        
        for (String str : strList) {
            // str = str.replace(",", "");
            // str = str.replace(".", "");
            
            if (excludewords.contains(str)) {
                continue;
            }
            if (map.containsKey(str)) {
                map.put(str, map.get(str) + 1);
            } else {
                map.put(str, 1);                
            }
            
            if (map.get(str) == max && str.compareTo(rst) < 0) {
                rst = str;
            }
            
            if (map.get(str) > max) {
                max = map.get(str);
                rst = str;
            }
        }
        
        return rst;
    }
}


23280666998441