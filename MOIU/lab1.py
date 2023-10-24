from scipy.optimize import linprog

def print_linprog_results(res):
  """Prints the results of a linprog() call in a readable format."""

  print("Optimization status:", res.status)
  print("Objective function value:", res.fun)
  print("Optimal solution:", res.x)

# Solve the first problem
c = [-30, -1]
A_ub = [[90, 5]]
b_ub = [10000]
A_eq = [[3, -1]]
b_eq = [0]

res = linprog(c, A_ub, b_ub, A_eq, b_eq)
print("Problem 1:")
print_linprog_results(res)

# Solve the second problem
c = [7, 3, 6, 4, 8, 2, 1, 5, 9]
A_ub = [[1, 1, 1, 0, 0, 0, 0, 0, 0],
         [0, 0, 0, 1, 1, 1, 0, 0, 0],
         [0, 0, 0, 0, 0, 0, 1, 1, 1]]
b_ub = [74, 40, 36]
A_eq = [[1, 0, 0, 1, 0, 0, 1, 0, 0],
         [0, 1, 0, 0, 1, 0, 0, 1, 0],
         [0, 0, 1, 0, 0, 1, 0, 0, 1]]
b_eq = [20, 45, 30]

res = linprog(c, A_ub, b_ub, A_eq, b_eq)
print("Problem 2:")
print_linprog_results(res)