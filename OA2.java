1.  Count number of substrings with exactly k distinct characters  O(n^2)
    // Function to count number of substrings 
    // with exactly k unique characters 
    int countkDist(String str, int k) 
    { 
        // Initialize result 
        int res = 0; 
  
        int n = str.length(); 
  
        // To store count of characters from 'a' to 'z' 
        int cnt[] = new int[26]; 
  
        // Consider all substrings beginning with 
        // str[i] 
        for (int i = 0; i < n; i++) 
        { 
            int dist_count = 0; 
  
            // Initializing count array with 0 
            Arrays.fill(cnt, 0); 
  
            // Consider all substrings between str[i..j] 
            for (int j=i; j<n; j++) 
            { 
                // If this is a new character for this 
                // substring, increment dist_count. 
                if (cnt[str.charAt(j) - 'a'] == 0) 
                    dist_count++; 
  
                // Increment count of current character 
                cnt[str.charAt(j) - 'a']++; 
  
                // If distinct character count becomes k, 
                // then increment result. 
                if (dist_count == k) 
                    res++; 
            } 
        } 
  
        return res; 
    } 

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) {
            return 0;
        }
        int start = 0;
        int res = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put(s.charAt(0), 1);
        for (int end = 1; end < s.length(); end++) {
            char ch = s.charAt(end);
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch)+1);
            } else {
                if (map.size() == k) {
                    res = res++;
                    //full map, need to remove the first character in ths substring
                    for (int index = start; index < end; index++) {
                        map.put(s.charAt(index), map.get(s.charAt(index))-1);
                        start++;
                        if (map.get(s.charAt(index)) == 0) {
                            //have removed all occurance of a char
                            map.remove(s.charAt(index));
                            break;
                        } else {

                        }
                    }
                }
                map.put(ch, 1);
            }
        }
     
        return res;
    }
////////////////////////////////////////

  2. Maximum minimum Path
public static int maximumMinimumPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];                                // dp[i,j]路径中最小值 (不过下边有所不同)
        dp[0][0] = matrix[0][0];// [0,0] 肯定在考虑点中
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.min(dp[i - 1][0], matrix[i][0]); 
        }
        for (int i = 1; i < m; i++) {
            dp[0][i] = Math.min(dp[0][i - 1], matrix[0][i]);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.min(Math.max(dp[i - 1][j], dp[i][j - 1]), matrix[i][j]);
                // 每次选点的时候,因为路径只可能是从上或者从左, 所以选其中较大的, 再去合当前值比较.即可
  
            }
        }
        return dp[n - 1][m - 1];
    }

////////////////////////////////////////

    3. Subtree with Maximum Average 

    class ResultType {
        TreeNode node;
        int sum;
        int size;
        public ResultType(TreeNode node, int sum, int size) {
            this.node = node;
            this.sum = sum;
            this.size = size;
        }
    }
    
    private ResultType result = null;
    
    public TreeNode findSubtree2(TreeNode root) {
        // Write your code here
        if (root == null) {
            return null;
        }
        
        ResultType rootResult = helper(root);
        return result.node;
    }
    
    public ResultType helper(TreeNode root) {
        if (root == null) {
            return new ResultType(null, 0, 0);
        }
        
        ResultType leftResult = helper(root.left);
        ResultType rightResult = helper(root.right);
        
        ResultType currResult = new ResultType(
                    root, 
                    leftResult.sum + rightResult.sum + root.val, 
                    leftResult.size + rightResult.size + 1);
    
        if (result == null 
            || currResult.sum * result.size > result.sum * currResult.size) {
            result = currResult;
        }
        
        return currResult;
    }