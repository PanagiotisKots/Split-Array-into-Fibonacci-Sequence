import java.util.ArrayList;
import java.util.List;

class Main {
    public List<Integer> splitIntoFibonacci(String num) {
        List<Integer> list = new ArrayList<>();
        System.out.println("Starting DFS");
        dfs(num.toCharArray(), list, 0);
        return list;
    }

    public boolean dfs(char[] digit, List<Integer> list, int index) {
        System.out.println("DFS called with index: " + index + " and current list: " + list);

        // Base case: Check if we have reached the end of the string and have at least 3 numbers in the list
        if (index == digit.length && list.size() >= 3) {
            System.out.println("Found valid Fibonacci sequence: " + list);
            return true;
        }

        // Try different splits of the string and attempt to form numbers
        for (int i = index; i < digit.length; i++) {
            // Skip numbers with leading zero unless the number is "0"
            if (digit[index] == '0' && i > index) {
                System.out.println("Skipping due to leading zero from index " + index + " to " + i);
                break;
            }

            long num = subDigit(digit, index, i + 1);
            System.out.println("Trying substring from index " + index + " to " + (i + 1) + " -> num: " + num);

            // If the number is too large, break out of the loop
            if (num > Integer.MAX_VALUE) {
                System.out.println("Number too large, breaking out: " + num);
                break;
            }

            int size = list.size();

            // If the list has at least two numbers, check if the current number is valid
            if (size >= 2 && num > list.get(size - 1) + list.get(size - 2)) {
                System.out.println("Number is greater than the sum of the last two numbers, breaking: " + num);
                break;
            }

            // If the list has less than 2 numbers or the number fits the Fibonacci condition, add it to the list
            if (size <= 1 || num == list.get(size - 1) + list.get(size - 2)) {
                System.out.println("Adding " + num + " to the list");
                list.add((int) num);

                // Recursively call DFS
                if (dfs(digit, list, i + 1))
                    return true;

                // Backtrack if the current sequence does not lead to a solution
                System.out.println("Backtracking from list: " + list);
                list.remove(list.size() - 1);
            }
        }

        System.out.println("Returning false from index " + index + " with list: " + list);
        return false;
    }

    private long subDigit(char[] digit, int start, int end) {
        long res = 0;
        for (int i = start; i < end; i++) {
            res = res * 10 + digit[i] - '0';
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String num = "1101111";  // Example input
        List<Integer> result = solution.splitIntoFibonacci(num);
        System.out.println("Resulting Fibonacci sequence: " + result);
    }
}
